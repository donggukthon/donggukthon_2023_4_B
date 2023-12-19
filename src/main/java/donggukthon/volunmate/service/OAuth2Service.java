package donggukthon.volunmate.service;

import donggukthon.volunmate.constant.Constants;
import donggukthon.volunmate.domain.User;
import donggukthon.volunmate.dto.auth.AuthSignupDto;
import donggukthon.volunmate.dto.jwt.JwtTokenDto;
import donggukthon.volunmate.exception.CustomException;
import donggukthon.volunmate.exception.ErrorCode;
import donggukthon.volunmate.repository.UserRepository;
import donggukthon.volunmate.security.jwt.JwtProvider;
import donggukthon.volunmate.type.ELoginProvider;
import donggukthon.volunmate.type.EUserType;
import donggukthon.volunmate.utils.CookieUtil;
import donggukthon.volunmate.utils.GoogleOAuth2Util;
import donggukthon.volunmate.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2Service {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final JwtProvider jwtProvider;
    private final GoogleOAuth2Util oAuth2Util;
    @Value("${security.oauth2.main_url}")
    private String MAIN_URL;
    @Value("${security.oauth2.signup_url}")
    private String SIGNUP_URL;

    public String getRedirectUrl(ELoginProvider provider) {
        if (provider == ELoginProvider.GOOGLE) {
            return oAuth2Util.getGoogleRedirectUrl();
        }
        return null;
    }

    public String getAccessToken(String authorizationCode, ELoginProvider provider) {
        String accessToken = null;
        if (provider == ELoginProvider.GOOGLE) {
            accessToken = oAuth2Util.getGoogleAccessToken(authorizationCode);
        }
        return accessToken;
    }

    public void login(HttpServletResponse response, String accessToken, ELoginProvider provider) throws IOException {
        String tempId = null;
        if (provider == ELoginProvider.GOOGLE) {
            tempId = oAuth2Util.getGoogleUserInfo(accessToken);
        }

        if (tempId == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_ERROR);
        }

        final String socialId = tempId;
        final String password = UUID.randomUUID().toString();

        User user = userRepository.findBySocialIdAndProvider(socialId, provider)
                .orElseGet(() -> userRepository.save(User.builder()
                        .socialId(socialId)
                        .password(password)
                        .userType(EUserType.GUEST)
                        .provider(provider)
                        .build()));

        JwtTokenDto jwtToken = jwtProvider.createTokens(user.getSocialId());
        user.updateRefreshToken(jwtToken.refreshToken());

        sendAccessTokenAndRefreshToken(user, response, jwtToken.accessToken(), jwtToken.refreshToken());
    }

    public void sendAccessTokenAndRefreshToken(User user, HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);

        CookieUtil.addCookie(response, Constants.AUTHORIZATION, accessToken);
        CookieUtil.addSecureCookie(response, Constants.REAUTHORIZATION, refreshToken, jwtUtil.getRefreshTokenExpiration());

        if (user.getUserType() == EUserType.GUEST) {
            response.sendRedirect(SIGNUP_URL);
        } else {
            response.sendRedirect(MAIN_URL);
        }
    }

    public Boolean enroll(String socialId, AuthSignupDto authSignupDto) {
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        user.userSignUp(authSignupDto.name(), authSignupDto.kakaoId(), authSignupDto.myLatitude(), authSignupDto.myLongitude());
        return true;
    }

    public Boolean logout(String socialId) {
        User user = userRepository.findBySocialIdAndIsLogin(socialId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        user.logout();
        return true;
    }

    public Map<String, String> reissueAccessToken(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put(Constants.AUTHORIZATION, jwtProvider.validateRefreshToken(request));
        return map;
    }
}
