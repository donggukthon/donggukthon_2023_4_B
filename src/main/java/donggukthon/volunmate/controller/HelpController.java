package donggukthon.volunmate.controller;

import donggukthon.volunmate.annotation.SocialId;
import donggukthon.volunmate.dto.exception.ResponseDto;
import donggukthon.volunmate.dto.help.*;
import donggukthon.volunmate.service.HelpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/help")
@Tag(name = "Help", description = "도와주세요 요청 관련 API입니다.")
public class HelpController {
    private final HelpService helpService;

    @Operation(summary = "도움 요청 생성", description = "도움 요청을 생성합니다.")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<Boolean> createHelp(@SocialId String socialId,
                                           @RequestPart("data") CreateHelpDto createHelpDto,
                                           @Nullable @RequestPart("file") MultipartFile imageFile) {
        return ResponseDto.created(helpService.createHelp(socialId, createHelpDto, imageFile));
    }

    @Operation(summary = "도움 요청 리스트 조회", description = "도움 요청 리스트를 조회합니다.")
    @GetMapping
    public ResponseDto<HelpListDto> getHelp(@SocialId String socialId){
        return ResponseDto.ok(helpService.getHelpList(socialId));
    }

    @Operation(summary = "긴급 도움 요청 리스트 조회", description = "긴급 도움 요청 리스트를 조회합니다.")
    @GetMapping("/em")
    public ResponseDto<EmergencyHelpListDto> getEmergencyHelp(@SocialId String socialId){
        return ResponseDto.ok(helpService.getEmergencyHelpList(socialId));
    }

    @Operation(summary = "도움 요청 상세 조회", description = "도움 요청 상세 정보를 조회합니다.")
    @GetMapping("/{helpId}")
    public ResponseDto<ResponseHelpDetailDto> getHelpDetail(@PathVariable Long helpId){
        return ResponseDto.ok(helpService.getHelpDetail(helpId));
    }

    @Operation(summary = "도움 요청 수정", description = "도움 요청을 수정합니다.")
    @PatchMapping("/{helpId}")
    public ResponseDto<Boolean> helpUpdate(@SocialId String socialId,
                                           @RequestPart("data") CreateHelpDto createHelpDto,
                                           @Nullable @RequestPart("file") MultipartFile imageFile,
                                           @PathVariable Long helpId) {
        return ResponseDto.ok(helpService.helpUpdate(socialId, createHelpDto, imageFile, helpId));
    }

    @Operation(summary = "도움 요청글 상태 변경", description = "도움 요청글 상태를 변경합니다.")
    @PatchMapping("/{helpId}/status")
    public ResponseDto<Boolean> helpStatusUpdate(@SocialId String socialId,
                                                 @RequestParam("status") boolean status,
                                                 @PathVariable Long helpId) {
        return ResponseDto.ok(helpService.helpStatusUpdate(socialId, status, helpId));
    }

    @Operation(summary = "도움 요청 삭제", description = "도움 요청을 삭제합니다.")
    @DeleteMapping("/{helpId}")
    public ResponseDto<Boolean> helpDelete(@SocialId String socialId,
                                           @PathVariable Long helpId){
        return ResponseDto.ok(helpService.helpDelete(socialId, helpId));
    }
}
