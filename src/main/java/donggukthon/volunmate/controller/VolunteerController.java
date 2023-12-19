package donggukthon.volunmate.controller;

import donggukthon.volunmate.annotation.SocialId;
import donggukthon.volunmate.dto.CreateVolunteerDto;
import donggukthon.volunmate.dto.RequestVolunteerSignDto;
import donggukthon.volunmate.dto.ResponseVolunteerDetailDto;
import donggukthon.volunmate.dto.ResponseVolunteerDto;
import donggukthon.volunmate.dto.exception.ResponseDto;
import donggukthon.volunmate.service.VolunteerService;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class VolunteerController {

    private final VolunteerService volunteerService;

    @PostMapping("")
    public ResponseDto<Boolean> createVolunteer(@SocialId String socialId, @RequestBody CreateVolunteerDto createVolunteerDto,
                                                @Nullable @RequestPart("file") File imageFile) {

        return ResponseDto.ok(volunteerService.createVolunteer(socialId,createVolunteerDto,imageFile));
    }

    @PostMapping("/{team_id}")
    public ResponseDto<Boolean> volunteerSignUp(@SocialId String socialId, @PathVariable Long team_id,
                                                @RequestBody RequestVolunteerSignDto requestVolunteerSignDto) {

        return ResponseDto.ok(volunteerService.volunteerSignUp(socialId,team_id,requestVolunteerSignDto));
    }

    @PostMapping("/{team_id}/heart")
    public ResponseDto<Boolean> volunteerHeart(@SocialId String socialId, @PathVariable Long team_id) {

        return ResponseDto.ok(volunteerService.volunteerHeart(socialId,team_id));
    }
    @GetMapping("")
    public ResponseDto<List<ResponseVolunteerDto>> volunteerHeart(@SocialId String socialId) {

        return ResponseDto.ok(volunteerService.getVolunteerList(socialId));
    }

    @GetMapping("/{team_id}")
    public ResponseDto<ResponseVolunteerDetailDto> volunteerHeart(@PathVariable Long team_id) {

        return ResponseDto.ok(volunteerService.getVolunteerDetail(team_id));
    }

    @DeleteMapping("/{team_id}")
    public ResponseDto<Boolean> volunteerDelete(@SocialId String socialId, @PathVariable Long team_id) {

        return ResponseDto.ok(volunteerService.volunteerDelete(socialId,team_id));
    }

    @PatchMapping("/{team_id}")
    public ResponseDto<Boolean> volunteerUpdate(@SocialId String socialId, @RequestBody CreateVolunteerDto createVolunteerDto,
                                                @Nullable @RequestPart("file") File imageFile,@PathVariable Long team_id) {

        return ResponseDto.ok(volunteerService.volunteerUpdate(socialId,createVolunteerDto,imageFile,team_id));
    }



}
