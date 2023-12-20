package donggukthon.volunmate.controller;

import donggukthon.volunmate.annotation.SocialId;
import donggukthon.volunmate.dto.exception.ResponseDto;
import donggukthon.volunmate.dto.myPage.request.UserLocationDto;
import donggukthon.volunmate.dto.myPage.response.*;
import donggukthon.volunmate.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
@Tag(name = "MyPage", description = "마이페이지 관련 API입니다.")
public class MyPageController {
    private final MyPageService myPageService;

    @Operation(summary = "유저 정보 조회", description = "유저 정보를 조회합니다.")
    @GetMapping
    public ResponseDto<UserInfoDto> getUserInfo(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getUserInfo(socialId));
    }

    @Operation(summary = "유저 정보 수정", description = "유저 정보를 수정합니다. 현재 위치를 기반으로 합니다.")
    @PatchMapping
    public ResponseDto<?> updateUserLocation(@SocialId String socialId,
                                             @RequestBody UserLocationDto userLocationDto) {
        return ResponseDto.ok(myPageService.updateUserLocation(socialId, userLocationDto));
    }

    @Operation(summary = "유저 팀 리스트 조회", description = "유저 팀 리스트를 조회합니다.")
    @GetMapping("/team")
    public ResponseDto<List<UserTeamListDto>> getUserTeamList(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getUserTeamList(socialId));
    }

    @Operation(summary = "유저 팀 가입 승인 / 거절", description = "유저 팀 가입을 승인하거나 거절합니다.")
    @PatchMapping("/team/{teamId}")
    public ResponseDto<?> patchUserTeam(@SocialId String socialId,
                                           @PathVariable Long teamId,
                                           @RequestParam boolean isAccept) {
        return ResponseDto.ok(myPageService.patchUserTeam(socialId, teamId, isAccept));
    }

    @Operation(summary = "유저 팀 상세 조회", description = "유저가 작성한 봉사활동에 참여한 팀월들을 확인합니다.")
    @GetMapping("/team/details")
    public ResponseDto<TeamDetailListDto> getTeamDetails(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getTeamDetails(socialId));
    }

    @Operation(summary = "유저 팀 상세 조회", description = "내가 작성한 봉사활동글을 조회합니다.")
    @GetMapping("/mate")
    public ResponseDto<TeammateListDto> getTeammateList(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getTeammateList(socialId));
    }

    @Operation(summary = "유저 도와주세요 상세 조회", description = "내가 등록한 도와주세요글을 조회합니다.")
    @GetMapping("/help")
    public ResponseDto<HelpListDto> getHelpList(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getHelpList(socialId));
    }

    @Operation(summary = "유저 좋아요글 상세 조회", description = "내가 좋아요한 봉사활동글을 조회합니다.")
    @GetMapping("/heart")
    public ResponseDto<TeammateListDto> getTeammateHeartedList(@SocialId String socialId) {
        return ResponseDto.ok(myPageService.getTeammateHeartedList(socialId));
    }
}
