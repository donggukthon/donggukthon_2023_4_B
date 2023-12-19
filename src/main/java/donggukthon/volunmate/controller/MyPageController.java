package donggukthon.volunmate.controller;

import donggukthon.volunmate.annotation.SocialId;
import donggukthon.volunmate.dto.exception.ResponseDto;
import donggukthon.volunmate.dto.myPage.request.UserLocationDto;
import donggukthon.volunmate.dto.myPage.response.*;
import donggukthon.volunmate.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    @GetMapping
    public ResponseDto<UserInfoDto> getUserInfo(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getUserInfo(socialId));
    }

    @PatchMapping
    public ResponseDto<?> updateUserLocation(@SocialId String socialId,
                                             @RequestBody UserLocationDto userLocationDto) {
        return ResponseDto.ok(myPageService.updateUserLocation(socialId, userLocationDto));
    }

    @GetMapping("/team")
    public ResponseDto<UserTeamListDto> getUserTeamList(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getUserTeamList(socialId));
    }

    @PatchMapping("/team/{teamId}")
    public ResponseDto<?> patchUserTeam(@SocialId String socialId,
                                           @PathVariable Long teamId,
                                           @RequestParam boolean isAccept) {
        return ResponseDto.ok(myPageService.patchUserTeam(socialId, teamId, isAccept));
    }

    @GetMapping("/team/details")
    public ResponseDto<TeamDetailListDto> getTeamDetails(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getTeamDetails(socialId));
    }

    @GetMapping("/mate")
    public ResponseDto<TeammateListDto> getTeammateList(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getTeammateList(socialId));
    }

    @GetMapping("/help")
    public ResponseDto<HelpListDto> getHelpList(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getHelpList(socialId));
    }

    @GetMapping("/heart")
    public ResponseDto<TeammateListDto> getTeammateHeartedList(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getTeammateHeartedList(socialId));
    }
}
