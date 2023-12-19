package donggukthon.volunmate.controller;

import donggukthon.volunmate.annotation.SocialId;
import donggukthon.volunmate.dto.*;
import donggukthon.volunmate.dto.exception.ResponseDto;
import donggukthon.volunmate.service.HelpService;
import donggukthon.volunmate.service.VolunteerService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/help")
public class HelpController {

    private final HelpService helpService;

    @PostMapping("")
    public ResponseDto<Boolean> createHelp(@SocialId String socialId, @RequestBody CreateHelpDto createHelpDto,
                                                @Nullable @RequestPart("file") File imageFile) {

        return ResponseDto.created(helpService.createHelp(socialId,createHelpDto,imageFile));
    }

    @GetMapping("")
    public ResponseDto<List<ResponseHelpDto>> getHelp(@SocialId String socialId){
        return ResponseDto.ok(helpService.getHelpList(socialId));
    }

    @GetMapping("/em")
    public ResponseDto<List<ResponseHelpDto>> getEmergencyHelp(@SocialId String socialId){
        return ResponseDto.ok(helpService.getHelpList(socialId));
    }

    @GetMapping("/{help_id}")
    public ResponseDto<ResponseHelpDetailDto> getHelpDetail(@PathVariable Long help_id){

        return ResponseDto.ok(helpService.getHelpDetail(help_id));
    }

    @PatchMapping("/{help_id}")
    public ResponseDto<Boolean> helpUpdate(@SocialId String socialId, @RequestBody CreateHelpDto createHelpDto,
                                                @Nullable @RequestPart("file") File imageFile,@PathVariable Long team_id) {

        return ResponseDto.ok(helpService.helpUpdate(socialId,createHelpDto,imageFile,team_id));
    }

    @DeleteMapping("/{help_id}")
    public ResponseDto<Boolean> helpDelete(@SocialId String socialId, @PathVariable Long help_id){
        return ResponseDto.ok(helpService.helpDelete(socialId,help_id));
    }


}
