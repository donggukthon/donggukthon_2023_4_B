package donggukthon.volunmate.controller;

import donggukthon.volunmate.annotation.SocialId;
import donggukthon.volunmate.dto.exception.ResponseDto;
import donggukthon.volunmate.dto.myPage.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class MyPageController {
    @GetMapping
    public ResponseDto<UserInfoDto> getUserInfo(@SocialId String socialId) {
        return ResponseDto.ok(UserInfoDto.of(
                "김동규", 4.5f, 3, 2, 1
        ));
    }

    @GetMapping("/team")
    public ResponseDto<UserTeamListDto> getUserTeamList(@SocialId String socialId) {
        return ResponseDto.ok(UserTeamListDto.of(List.of(
                UserTeamDto.of(1L, "팀원 모집합니다", "김동규", "팀원 모집합니다"),
                UserTeamDto.of(2L, "팀원 모집합니다", "김동규", "팀원 모집합니다"),
                UserTeamDto.of(3L, "팀원 모집합니다", "김동규", "팀원 모집합니다"),
                UserTeamDto.of(4L, "팀원 모집합니다", "김동규", "팀원 모집합니다"),
                UserTeamDto.of(5L, "팀원 모집합니다", "김동규", "팀원 모집합니다"))
        ));
    }

    @PatchMapping("/team/{teamId}")
    public ResponseDto<?> patchUserTeam(@SocialId String socialId,
                                           @PathVariable Long teamId,
                                           @RequestParam boolean isAccept) {
        return ResponseDto.ok(true);
    }

    @GetMapping("/team/details")
    public ResponseDto<TeamDetailListDto> getTeamDetails(@SocialId String socialId) {
        return ResponseDto.ok(
            TeamDetailListDto.of(List.of(
                    TeamDetailDto.of(1L, "팀원 모집합니다", List.of(
                            ParticipantsDto.of(
                                    "김동규", "010-1234-5678"
                            ),
                            ParticipantsDto.of(
                                    "김동규", "010-1234-5678"
                            ),
                            ParticipantsDto.of(
                                    "김동규", "010-1234-5678"
                            )
                    ))
            ))
        );
    }

    @GetMapping("/mate")
    public ResponseDto<TeammateListDto> getTeammateList(@SocialId String socialId) {
        return ResponseDto.ok(TeammateListDto.of(List.of(
                TeammateDto.of(1L, "팀원 구합니다", "2021-10-10 ~ 2021-10-12", 11.1f, 22.2f, "image_url", 10, 6, "2021-10-10"),
                TeammateDto.of(2L, "팀원 구합니다", "2021-10-10 ~ 2021-10-12", 11.1f, 22.2f, "image_url", 10, 6, "2021-10-10"),
                TeammateDto.of(3L, "팀원 구합니다", "2021-10-10 ~ 2021-10-12", 11.1f, 22.2f, "image_url", 10, 6, "2021-10-10")
        )));
    }

    @GetMapping("/help")
    public ResponseDto<HelpListDto> getHelpList(@SocialId String socialId) {
        return ResponseDto.ok(HelpListDto.of(List.of(
                HelpDto.of(1L, "도와주세요", 11.1f, 22.2f, "image_url", true, "2021-10-10"),
                HelpDto.of(2L, "도와주세요", 11.1f, 22.2f, "image_url", true, "2021-10-10"),
                HelpDto.of(3L, "도와주세요", 11.1f, 22.2f, "image_url", true, "2021-10-10")
        )));
    }

    @GetMapping("/heart")
    public ResponseDto<TeammateListDto> getTeammateHeartedList(@SocialId String socialId) {
        return ResponseDto.ok(TeammateListDto.of(List.of(
                TeammateDto.of(1L, "팀원 구합니다", "2021-10-10 ~ 2021-10-12", 11.1f, 22.2f, "image_url", 10, 6, "2021-10-10"),
                TeammateDto.of(2L, "팀원 구합니다", "2021-10-10 ~ 2021-10-12", 11.1f, 22.2f, "image_url", 10, 6, "2021-10-10"),
                TeammateDto.of(3L, "팀원 구합니다", "2021-10-10 ~ 2021-10-12", 11.1f, 22.2f, "image_url", 10, 6, "2021-10-10")
        )));
    }
}
