package donggukthon.volunmate.service;

import donggukthon.volunmate.domain.*;
import donggukthon.volunmate.dto.*;
import donggukthon.volunmate.exception.CustomException;
import donggukthon.volunmate.exception.ErrorCode;
import donggukthon.volunmate.repository.HelpRepository;
import donggukthon.volunmate.repository.UserRepository;
import donggukthon.volunmate.utils.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HelpService {
    private final HelpRepository helpRepository;
    private final UserRepository userRepository;

    private final S3Util s3Util;

    public Boolean createHelp(String socialId, CreateHelpDto createHelpDto, File imageFile){

        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));


        String imageUrl = null; // image logic 추가해야함.
        helpRepository.save(
                Help.builder()
                        .emergency(createHelpDto.isEmergency())
                        .title(createHelpDto.title())
                        .imageUrl(imageUrl)
                        .content(createHelpDto.content())
                        .latitude(createHelpDto.latitude())
                        .longitude(createHelpDto.longitude())
                        .user(user)
                        .build()
        );

        return Boolean.TRUE;
    }


    public ResponseHelpDetailDto getHelpDetail(Long help_id){
        Help help = helpRepository.findById(help_id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        return ResponseHelpDetailDto.of(
                help.getEmergency(),help.getImageUrl(),help.getTitle(), help.getContent(), help.getLatitude(),
                help.getLongitude(),help.getUser().getUserName(),help.getUser().getDegree(),
                help.getUser().getKakaoId(),help.getCreatedAt());
    }

    public List<ResponseHelpDto> getHelpList(String socialId){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        List<Help> helpList = helpRepository.getHelpList(user.getLatitude(), user.getLongitude());

        List<ResponseHelpDto> responseHelpDtoList = helpList.stream()
                .map(help ->
                        new ResponseHelpDto(
                                help.getId(),
                                help.getEmergency(),
                                help.getImageUrl(),
                                help.getTitle(),
                                help.getLatitude(),
                                help.getLongitude(),
                                help.getCreatedAt()
                        ))
                .collect(Collectors.toList());


        return responseHelpDtoList;
    }

    public List<ResponseHelpDto> getEmergencyHelpList(String socialId){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        List<Help> helpList = helpRepository.getEmergencyHelpList(user.getLatitude(), user.getLongitude());

        List<ResponseHelpDto> responseHelpDtoList = helpList.stream()
                .map(help ->
                        new ResponseHelpDto(
                                help.getId(),
                                help.getEmergency(),
                                help.getImageUrl(),
                                help.getTitle(),
                                help.getLatitude(),
                                help.getLongitude(),
                                help.getCreatedAt()
                        ))
                .collect(Collectors.toList());


        return responseHelpDtoList;
    }
    public Boolean helpDelete(String socialId, Long help_id){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        Help help = helpRepository.findById(help_id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        if(user != help.getUser()){
            throw new CustomException(ErrorCode.BAD_REQUEST_ERROR);
        }

        helpRepository.delete(help);

        return Boolean.TRUE;
    }

    public Boolean helpUpdate(String socialId, CreateHelpDto createHelpDto,
                                   File imageFile,Long team_id){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        Help help = helpRepository.findById(team_id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        if(user != help.getUser()){
            throw new CustomException(ErrorCode.BAD_REQUEST_ERROR);
        }
        String imageUrl = null; //image logic 추가필요.

        help.update(createHelpDto.title(),createHelpDto.content(),imageUrl,
                createHelpDto.latitude(), createHelpDto.longitude(), createHelpDto.isEmergency());

        return Boolean.TRUE;
    }
}
