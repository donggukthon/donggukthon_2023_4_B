package donggukthon.volunmate.controller;


import donggukthon.volunmate.annotation.SocialId;
import donggukthon.volunmate.dto.auth.AuthSignupDto;
import donggukthon.volunmate.dto.exception.ResponseDto;
import donggukthon.volunmate.dto.jwt.JwtTokenDto;
import donggukthon.volunmate.service.OAuth2Service;
import donggukthon.volunmate.type.ELoginProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final OAuth2Service oAuth2Service;

    @GetMapping("/google")
    public ResponseDto<Map<String, String>> getGoogleRedirectUrl() {
        Map<String, String> map = new HashMap<>();
        map.put("url", oAuth2Service.getRedirectUrl(ELoginProvider.GOOGLE));
        return ResponseDto.ok(map);
    }

    @GetMapping("/google/callback")
    public void getGoogleAccessToken(String code, HttpServletResponse response) throws IOException {
        String accessToken = oAuth2Service.getAccessToken(code, ELoginProvider.GOOGLE);
        oAuth2Service.login(response, accessToken, ELoginProvider.GOOGLE);
    }

    @PostMapping("/enroll")
    public ResponseDto<Boolean> enroll(@SocialId String socialId, @RequestBody AuthSignupDto authSignupDto) {
        return ResponseDto.ok(oAuth2Service.enroll(socialId, authSignupDto));
    }

    @GetMapping("/logout")
    public ResponseDto<Boolean> logout(@SocialId String socialId) {
        return ResponseDto.ok(oAuth2Service.logout(socialId));
    }

    @GetMapping("/refresh")
    public ResponseDto<?> updateAccessToken(HttpServletRequest request) {
        return ResponseDto.ok(oAuth2Service.reissueAccessToken(request));
    }
}
