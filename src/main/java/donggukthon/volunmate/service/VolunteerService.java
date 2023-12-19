package donggukthon.volunmate.service;

import donggukthon.volunmate.domain.*;
import donggukthon.volunmate.dto.volunteer.request.CreateVolunteerDto;
import donggukthon.volunmate.dto.volunteer.request.VolunteerSignDto;
import donggukthon.volunmate.dto.volunteer.response.VolunteerDetailDto;
import donggukthon.volunmate.dto.volunteer.response.VolunteerDto;
import donggukthon.volunmate.dto.volunteer.response.VolunteerListDto;
import donggukthon.volunmate.exception.CustomException;
import donggukthon.volunmate.exception.ErrorCode;
import donggukthon.volunmate.repository.*;
import donggukthon.volunmate.type.EStatusType;
import donggukthon.volunmate.utils.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final VolunmateRepository volunmateRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final HeartRepository heartRepository;
    private final S3Util s3Util;

    public Boolean createVolunteer(String socialId, CreateVolunteerDto createVolunteerDto, MultipartFile imageFile){

        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        List<Tag> tagList = new ArrayList<>();

        String imageUrl = s3Util.upload(imageFile);

        Volunteer volunteer = Volunteer.builder()
                .title(createVolunteerDto.title())
                .imageUrl(imageUrl)
                .tags(tagList)
                .content(createVolunteerDto.content())
                .volunCount(createVolunteerDto.person())
                .latitude(createVolunteerDto.latitude())
                .longitude(createVolunteerDto.longitude())
                .startDate(createVolunteerDto.startDate())
                .endDate(createVolunteerDto.endDate())
                .dueDate(createVolunteerDto.dueDate())
                .user(user)
                .build();

        for (String tag:createVolunteerDto.tag()) {
            tagList.add(Tag.builder()
                            .content(tag)
                            .volunteer(volunteer)
                    .build());
        }
        user.updateDegree(user.getDegree() + 0.2f);
        volunteerRepository.saveAndFlush(volunteer);
        tagRepository.saveAllAndFlush(tagList);

        return Boolean.TRUE;
    }

    public Boolean volunteerSignUp(String socialId, Long teamId, VolunteerSignDto requestVolunteerSignDto){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Volunteer volunteer = volunteerRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.VOLUNTEER_NOT_FOUND));

        if(user == volunteer.getUser()){
            throw new CustomException(ErrorCode.CANNOT_SIGNUP_MY_VOLUNTEER);
        }
        user.updateDegree(user.getDegree() + 0.1f);

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

    public Boolean volunteerHeart(String socialId, Long teamId){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Volunteer volunteer = volunteerRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.VOLUNTEER_NOT_FOUND));

        heartRepository.save(
                Heart.builder()
                        .volunteer(volunteer)
                        .user(user)
                        .build()
        );

        return Boolean.TRUE;
    }

    public VolunteerDetailDto getVolunteerDetail(Long teamId){
        Volunteer volunteer = volunteerRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.VOLUNTEER_NOT_FOUND));

        List<String> tagStrings = new ArrayList<>();

        for (Tag t: volunteer.getTags()) {
            tagStrings.add(t.getContent());
        }

        return VolunteerDetailDto.of(volunteer.getTitle(),
                volunteer.getContent(), tagStrings,
                volunteer.getStartDate().toString(), volunteer.getEndDate().toString(),
                volunteer.getDueDate().toString(), volunteer.getLatitude(), volunteer.getLongitude(),
                volunteer.getUser().getUserName(), volunteer.getUser().getDegree(),
                volunteer.getCreatedAt().toString(),
                volunteer.getVolunCount(), volunteer.getCurCount(),
                volunteer.getImageUrl()
                );
    }

    public VolunteerListDto getVolunteerList(String socialId){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Volunteer> volunteerList = volunteerRepository.getVolunteers(user.getLatitude(), user.getLongitude(),
                                                                            LocalDateTime.now());
        List<VolunteerDto> volunteerDtos = volunteerList.stream()
                .map(volunteer -> VolunteerDto.of(
                        volunteer.getId(),
                        volunteer.getTitle(),
                        volunteer.getTags().stream()
                                .map(Tag::getContent)
                                .collect(Collectors.toList()),
                        volunteer.getStartDate().toString(),
                        volunteer.getEndDate().toString(),
                        volunteer.getDueDate().toString(),
                        volunteer.getVolunCount(),
                        volunteer.getCurCount(),
                        volunteer.getLatitude(),
                        volunteer.getLongitude(),
                        volunteer.getCreatedAt().toString(),
                        volunteer.getImageUrl(),
                        volunteer.getHearts().stream()
                                .anyMatch(heart -> heart.getUser() == user)
                )).toList();

        return VolunteerListDto.of(volunteerDtos);
    }
    public Boolean volunteerDelete(String socialId, Long team_id){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Volunteer volunteer = volunteerRepository.findById(team_id)
                .orElseThrow(() -> new CustomException(ErrorCode.VOLUNTEER_NOT_FOUND));

        if(user != volunteer.getUser()){
            throw new CustomException(ErrorCode.BAD_REQUEST_ERROR);
        }

        volunteerRepository.delete(volunteer);

        return Boolean.TRUE;
    }

    public Boolean volunteerUpdate(String socialId, CreateVolunteerDto createVolunteerDto,
                                   MultipartFile imageFile, Long team_id){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Volunteer volunteer = volunteerRepository.findById(team_id)
                .orElseThrow(() -> new CustomException(ErrorCode.VOLUNTEER_NOT_FOUND));

        if(user != volunteer.getUser()){
            throw new CustomException(ErrorCode.BAD_REQUEST_ERROR);
        }

        String imageUrl = s3Util.updateImage(imageFile, volunteer.getImageUrl());

        volunteer.update(createVolunteerDto.title(),createVolunteerDto.content(),imageUrl,
                createVolunteerDto.latitude(), createVolunteerDto.longitude(),
                createVolunteerDto.person(),createVolunteerDto.startDate(),createVolunteerDto.endDate(),
                createVolunteerDto.dueDate());

        return Boolean.TRUE;
    }

}
