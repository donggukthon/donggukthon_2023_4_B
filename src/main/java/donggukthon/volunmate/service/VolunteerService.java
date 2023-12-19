package donggukthon.volunmate.service;

import donggukthon.volunmate.domain.*;
import donggukthon.volunmate.dto.CreateVolunteerDto;
import donggukthon.volunmate.dto.RequestVolunteerSignDto;
import donggukthon.volunmate.dto.ResponseVolunteerDetailDto;
import donggukthon.volunmate.dto.ResponseVolunteerDto;
import donggukthon.volunmate.exception.CustomException;
import donggukthon.volunmate.exception.ErrorCode;
import donggukthon.volunmate.repository.*;
import donggukthon.volunmate.type.EStatusType;
import donggukthon.volunmate.utils.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    private final VolunmateRepository volunmateRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    private final HeartRepository heartRepository;

    private final S3Util s3Util;

    public Boolean createVolunteer(String socialId, CreateVolunteerDto createVolunteerDto, File imageFile){

        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        List<Tag> tagList = new ArrayList<>();

        String imageUrl = null; // image logic 추가해야함.
        Volunteer volunteer = Volunteer.builder()
                .title(createVolunteerDto.title())
                .imageUrl(imageUrl)
                .tags(tagList)
                .content(createVolunteerDto.content())
                .volunCount(createVolunteerDto.person())
                .lattitude(createVolunteerDto.latitude())
                .longitude(createVolunteerDto.longitude())
                .startDate(createVolunteerDto.startDate())
                .endDate(createVolunteerDto.endDate())
                .dueDate(createVolunteerDto.dueDate())
                .user(user)
                .build();



        for (String tag:createVolunteerDto.tag()
             ) {
            tagList.add(Tag.builder()
                            .content(tag)
                            .volunteer(volunteer)
                    .build());
        }
        volunteerRepository.saveAndFlush(volunteer);
        tagRepository.saveAllAndFlush(tagList);

        return Boolean.TRUE;
    }

    public Boolean volunteerSignUp(String socialId, Long team_id, RequestVolunteerSignDto requestVolunteerSignDto){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        Volunteer volunteer = volunteerRepository.findById(team_id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        if(user == volunteer.getUser()){
            throw new CustomException(ErrorCode.BAD_REQUEST_ERROR);
        }

        volunmateRepository.save(
                Volunmate.builder()
                        .content(requestVolunteerSignDto.content())
                        .phoneNumber(requestVolunteerSignDto.phoneNumber())
                        .status(EStatusType.READY)
                        .volunteer(volunteer)
                        .username(requestVolunteerSignDto.name())
                        .user(user)
                        .build()
        );

        return Boolean.TRUE;
    }

    public Boolean volunteerHeart(String socialId, Long team_id){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        Volunteer volunteer = volunteerRepository.findById(team_id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        heartRepository.save(
                Heart.builder()
                        .volunteer(volunteer)
                        .user(user)
                        .build()
        );

        return Boolean.TRUE;
    }

    public ResponseVolunteerDetailDto getVolunteerDetail(Long team_id){
        Volunteer volunteer = volunteerRepository.findById(team_id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        List<String> tagStrings = new ArrayList<>();

        for (Tag t: volunteer.getTags()
             ) {
            tagStrings.add(t.getContent());
        }

        return ResponseVolunteerDetailDto.of(volunteer.getTitle(),
                volunteer.getContent(),
                tagStrings,
                volunteer.getStartDate(),
                volunteer.getEndDate(),
                volunteer.getDueDate(),
                volunteer.getVolunCounet(),
                volunteer.getLattitude(),
                volunteer.getLongitude(),
                volunteer.getUser().getUserName(),
                volunteer.getUser().getDegree(),
                volunteer.getCreatedAt());
    }

    public List<ResponseVolunteerDto> getVolunteerList(String socialId){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        List<Volunteer> volunteerList = volunteerRepository.getVolunteers(user.getLattitude(), user.getLongitude(), LocalDateTime.now());

        List<ResponseVolunteerDto> responseVolunteerDtoList = volunteerList.stream()
                .map(volunteer ->
                        new ResponseVolunteerDto(
                        volunteer.getId(),
                        volunteer.getTitle(),
                        volunteer.getStartDate(),
                        volunteer.getEndDate(),
                        volunteer.getDueDate(),
                                volunteer.getTags().stream()
                                        .map(Tag::getContent)
                                        .collect(Collectors.toList()),
                        volunteer.getVolunCounet(),
                        volunteer.getCurCount(),
                        volunteer.getLattitude(),
                        volunteer.getLongitude(),
                        volunteer.getCreatedAt(),
                        volunteer.getHearts().stream().map(Heart::getUser).anyMatch(u->u.equals(user))
                ))
                .collect(Collectors.toList());


        return responseVolunteerDtoList;
    }
    public Boolean volunteerDelete(String socialId, Long team_id){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        Volunteer volunteer = volunteerRepository.findById(team_id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        if(user != volunteer.getUser()){
            throw new CustomException(ErrorCode.BAD_REQUEST_ERROR);
        }

        volunteerRepository.delete(volunteer);

        return Boolean.TRUE;
    }

    public Boolean volunteerUpdate(String socialId, CreateVolunteerDto createVolunteerDto,
                                   File imageFile,Long team_id){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        Volunteer volunteer = volunteerRepository.findById(team_id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        if(user != volunteer.getUser()){
            throw new CustomException(ErrorCode.BAD_REQUEST_ERROR);
        }
        String imageUrl = null; //image logic 추가필요.

        volunteer.update(createVolunteerDto.title(),createVolunteerDto.content(),imageUrl,
                createVolunteerDto.latitude(), createVolunteerDto.longitude(),
                createVolunteerDto.person(),createVolunteerDto.startDate(),createVolunteerDto.endDate(),
                createVolunteerDto.dueDate());

        return Boolean.TRUE;
    }

}
