package donggukthon.volunmate.service;

import donggukthon.volunmate.domain.*;
import donggukthon.volunmate.dto.help.*;
import donggukthon.volunmate.exception.CustomException;
import donggukthon.volunmate.exception.ErrorCode;
import donggukthon.volunmate.repository.HelpRepository;
import donggukthon.volunmate.repository.UserRepository;
import donggukthon.volunmate.utils.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HelpService {
    private final HelpRepository helpRepository;
    private final UserRepository userRepository;
    private final S3Util s3Util;

    public Boolean createHelp(String socialId, CreateHelpDto createHelpDto, MultipartFile imageFile){

        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));


        String imageUrl = s3Util.upload(imageFile);
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


    public ResponseHelpDetailDto getHelpDetail(Long helpId){
        Help help = helpRepository.findById(helpId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HELP));

        return ResponseHelpDetailDto.of(
                help.getEmergency(),help.getImageUrl(),help.getTitle(), help.getContent(), help.getLatitude(),
                help.getLongitude(),help.getUser().getUserName(),help.getUser().getDegree(),
                help.getUser().getKakaoId(),help.getCreatedAt().toString());
    }

    public HelpListDto getHelpList(String socialId){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        List<Help> helpList = helpRepository.getHelpList(user.getLatitude(), user.getLongitude());

        List<HelpDto> helpDtos = helpList.stream()
                .map(help -> HelpDto.of(
                        help.getId(),
                        help.getEmergency(),
                        help.getImageUrl(),
                        help.getTitle(),
                        help.getLatitude(),
                        help.getLongitude(),
                        help.getCreatedAt().toString()
                )).toList();
        return HelpListDto.of(helpDtos);
    }

    public EmergencyHelpListDto getEmergencyHelpList(String socialId){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        List<Help> helpList = helpRepository.getEmergencyHelpList(user.getLatitude(), user.getLongitude());
        List<EmergencyHelpDto> responseHelpDtoList = helpList.stream()
                .filter(Help::getEmergency)
                .map(help -> EmergencyHelpDto.of(
                        help.getId(),
                        help.getImageUrl(),
                        help.getTitle(),
                        help.getLatitude(),
                        help.getLongitude(),
                        help.getCreatedAt().toString()
                )).toList();
        return EmergencyHelpListDto.of(responseHelpDtoList);
    }
    public Boolean helpDelete(String socialId, Long helpId){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        Help help = helpRepository.findById(helpId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        if(user != help.getUser()){
            throw new CustomException(ErrorCode.BAD_REQUEST_ERROR);
        }

        helpRepository.delete(help);

        return Boolean.TRUE;
    }

    public Boolean helpUpdate(String socialId, CreateHelpDto createHelpDto,
                                   MultipartFile imageFile,Long helpId){
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        Help help = helpRepository.findById(helpId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        if(user != help.getUser()){
            throw new CustomException(ErrorCode.BAD_REQUEST_ERROR);
        }
        String imageUrl = s3Util.updateImage(imageFile, help.getImageUrl());

        help.update(createHelpDto.title(),createHelpDto.content(),imageUrl,
                createHelpDto.latitude(), createHelpDto.longitude(), createHelpDto.isEmergency());

        return Boolean.TRUE;
    }
}
