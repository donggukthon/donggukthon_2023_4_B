package donggukthon.volunmate.controller;

import donggukthon.volunmate.annotation.SocialId;
import donggukthon.volunmate.dto.volunteer.request.CreateVolunteerDto;
import donggukthon.volunmate.dto.volunteer.request.VolunteerSignDto;
import donggukthon.volunmate.dto.volunteer.response.VolunteerDetailDto;
import donggukthon.volunmate.dto.exception.ResponseDto;
import donggukthon.volunmate.dto.volunteer.response.VolunteerListDto;
import donggukthon.volunmate.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
@Tag(name = "Volunteer", description = "봉사활동 관련 API")
public class VolunteerController {

    private final VolunteerService volunteerService;

    @Operation(summary = "봉사활동 생성", description = "봉사활동 정보와 이미지를 통해 봉사활동을 생성힙니다.")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<Boolean> createVolunteer(@SocialId String socialId,
                                                @RequestPart("image") MultipartFile image,
                                                @RequestPart("data") CreateVolunteerDto createVolunteerDto) {
        return ResponseDto.ok(volunteerService.createVolunteer(socialId, createVolunteerDto, image));
    }

    @Operation(summary = "봉사활동 리스트 조회", description = "봉사활동 리스트를 조회합니다.")
    @GetMapping
    public ResponseDto<VolunteerListDto> volunteerHeart(@SocialId String socialId) {
        return ResponseDto.ok(volunteerService.getVolunteerList(socialId));
    }

    @Operation(summary = "봉사활동 상세 조회", description = "봉사활동 상세 정보를 조회합니다.")
    @GetMapping("/{teamId}")
    public ResponseDto<VolunteerDetailDto> volunteerHeart(@PathVariable Long teamId) {

        return ResponseDto.ok(volunteerService.getVolunteerDetail(teamId));
    }

    @Operation(summary = "봉사활동 신청", description = "봉사활동에 신청합니다. 자신이 생성한 봉사는 참가할 수 없습니다.")
    @PostMapping("/{teamId}")
    public ResponseDto<Boolean> volunteerSignUp(@SocialId String socialId, @PathVariable Long teamId,
                                                @RequestBody VolunteerSignDto volunteerSignDto) {
        return ResponseDto.ok(volunteerService.volunteerSignUp(socialId, teamId, volunteerSignDto));
    }

    @Operation(summary = "봉사활동 좋아요하기", description = "내가 관심이 가는 봉사활동에 좋아요를 누릅니다.")
    @PostMapping("/{teamId}/heart")
    public ResponseDto<Boolean> volunteerHeart(@SocialId String socialId, @PathVariable Long teamId) {
        return ResponseDto.ok(volunteerService.volunteerHeart(socialId, teamId));
    }

    @Operation(summary = "봉사활동 글을 삭제", description = "내가 생성한 봉사활동 글을 삭제합니다.")
    @DeleteMapping("/{teamId}")
    public ResponseDto<Boolean> volunteerDelete(@SocialId String socialId, @PathVariable Long teamId) {
        return ResponseDto.ok(volunteerService.volunteerDelete(socialId, teamId));
    }

    @Operation(summary = "봉사활동 글을 수정", description = "내가 생성한 봉사활동 글을 수정합니다.")
    @PatchMapping(value = "/{teamId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<Boolean> volunteerUpdate(@SocialId String socialId,
                                                @RequestPart("data") CreateVolunteerDto createVolunteerDto,
                                                @Nullable @RequestPart("image") MultipartFile imageFile,
                                                @PathVariable Long teamId) {
        return ResponseDto.ok(volunteerService.volunteerUpdate(socialId, createVolunteerDto, imageFile, teamId));
    }
}
