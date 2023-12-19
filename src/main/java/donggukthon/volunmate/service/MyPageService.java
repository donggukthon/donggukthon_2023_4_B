package donggukthon.volunmate.service;

import donggukthon.volunmate.domain.*;
import donggukthon.volunmate.dto.myPage.request.UserLocationDto;
import donggukthon.volunmate.dto.myPage.response.*;
import donggukthon.volunmate.exception.CustomException;
import donggukthon.volunmate.exception.ErrorCode;
import donggukthon.volunmate.repository.*;
import donggukthon.volunmate.type.EStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;
    private final VolunmateRepository volunmateRepository;
    private final HelpRepository helpRepository;
    private final HeartRepository heartRepository;
    public UserInfoDto getUserInfo(String socialId) {
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        return UserInfoDto.of(user.getUserName(), user.getDegree(), volunmateRepository.countByUser(user),
                helpRepository.countByUser(user), heartRepository.countByUser(user),
                user.getLatitude(), user.getLongitude());
    }

    public boolean updateUserLocation(String socialId, UserLocationDto userLocationDto) {
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));

        return user.updateUserLocation(userLocationDto.myLatitude(), userLocationDto.myLongitude());
    }

    public UserTeamListDto getUserTeamList(String socialId) {
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        List<Volunteer> volunteers = volunteerRepository.findByUser(user);
        List<UserTeamDto> userTeamDto = volunmateRepository.findByVolunteersIn(volunteers).stream()
                .filter(volunmate -> volunmate.getStatus() == EStatusType.READY)
                .map(volunmate -> UserTeamDto.of(
                        volunmate.getId(), volunmate.getVolunteer().getTitle(), volunmate.getUser().getUserName(), volunmate.getContent()
                )).toList();
        return UserTeamListDto.of(userTeamDto);
    }

    public boolean patchUserTeam(String socialId, Long teamId, boolean isAccept) {
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        Volunmate volunmate = volunmateRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.VOLUNMATE_NOT_FOUND));

        if (!volunmate.getVolunteer().getUser().equals(user)) {
            throw new CustomException(ErrorCode.USER_NOT_MATCH);
        }

        if (volunmate.getStatus() != EStatusType.READY) {
            throw new CustomException(ErrorCode.VOLUNMATE_STATUS_NOT_READY);
        }

        if (Objects.equals(volunmate.getVolunteer().getCurCount(), volunmate.getVolunteer().getVolunCounet())) {
            throw new CustomException(ErrorCode.FULL_VOLUNTEER_ERROR);
        }

        return volunmate.updateStatus(isAccept ? EStatusType.ACCEPT : EStatusType.REJECT);
    }

    public TeamDetailListDto getTeamDetails(String socialId) {
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        List<TeamDetailDto> teamDetailDtos = volunteerRepository.findByUser(user).stream()
                .map(volunteer -> {
                    List<Volunmate> associatedVolunmates = volunmateRepository.findByVolunteer(volunteer);
                    List<ParticipantsDto> participantsDtos = associatedVolunmates.stream()
                            .filter(volunmate -> volunmate.getStatus() == EStatusType.ACCEPT)
                            .map(volunmate -> ParticipantsDto.of(volunmate.getUsername(), volunmate.getPhoneNumber()))
                            .toList();

                    return TeamDetailDto.of(
                            volunteer.getId(),
                            volunteer.getTitle(),
                            participantsDtos
                    );
                })
                .toList();
        return TeamDetailListDto.of(teamDetailDtos);
    }

    public TeammateListDto getTeammateList(String socialId) {
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        List<Volunteer> volunteers = volunteerRepository.findByUser(user);
        List<TeammateDto> teammateDtos = volunteers.stream()
                .map(volunteer -> TeammateDto.of(
                        volunteer.getId(), volunteer.getTitle(), volunteer.getStartDate().toString(),
                        volunteer.getEndDate().toString(), volunteer.getLatitude(), volunteer.getLongitude(),
                        volunteer.getImageUrl(), volunteer.getVolunCounet(), volunteer.getCurCount(),
                        volunteer.getCreatedAt().toString()
                )).toList();

        return TeammateListDto.of(teammateDtos);
    }

    public HelpListDto getHelpList(String socialId) {
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        List<Help> helps = helpRepository.findByUser(user);
        List<HelpDto> helpDtos = helps.stream()
                .map(help -> HelpDto.of(
                        help.getId(), help.getTitle(), help.getLatitude(), help.getLongitude(),
                        help.getImageUrl(), help.getEmergency(), help.getCreatedAt().toString()
                )).toList();
        return HelpListDto.of(helpDtos);
    }

    public TeammateListDto getTeammateHeartedList(String socialId) {
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        List<Heart> hearts = heartRepository.findByUser(user);
        List<TeammateDto> teammateDtos = hearts.stream()
                .map(heart -> TeammateDto.of(
                        heart.getVolunteer().getId(), heart.getVolunteer().getTitle(),
                        heart.getVolunteer().getStartDate().toString(), heart.getVolunteer().getEndDate().toString(),
                        heart.getVolunteer().getLatitude(), heart.getVolunteer().getLongitude(),
                        heart.getVolunteer().getImageUrl(), heart.getVolunteer().getVolunCounet(),
                        heart.getVolunteer().getCurCount(), heart.getVolunteer().getCreatedAt().toString()
                )).toList();

        return TeammateListDto.of(teammateDtos);
    }
}
