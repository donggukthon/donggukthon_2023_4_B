package donggukthon.volunmate.security.jwt;

import donggukthon.volunmate.constant.Constants;
import donggukthon.volunmate.domain.User;
import donggukthon.volunmate.dto.jwt.JwtTokenDto;
import donggukthon.volunmate.repository.UserRepository;
import donggukthon.volunmate.type.EUserType;
import donggukthon.volunmate.utils.JwtUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider implements InitializingBean {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte [] keyBytes = Decoders.BASE64.decode(jwtUtil.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String socialId) {
        Claims claims = Jwts.claims();

        claims.put(Constants.SOCIAL_ID_CLAIM_NAME, socialId);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (
                        jwtUtil.getAccessTokenExpiration()
                )))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtTokenDto createTokens(String socialId) {
        return new JwtTokenDto(
                createToken(socialId),
                createToken(socialId)
        );
    }

    public String validateRefreshToken(HttpServletRequest request) {
        String refreshToken = resolveToken(request);
        Claims claims = validateToken(refreshToken);

        User user = userRepository.findBySocialIdAndRefreshToken(claims.get(Constants.SOCIAL_ID_CLAIM_NAME, String.class), refreshToken)
                .orElseThrow(() -> new JwtException("USER_NOT_FOUND"));
        return createToken(user.getSocialId());
    }

    public String getSocialId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(Constants.SOCIAL_ID_CLAIM_NAME, String.class);
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(Constants.AUTHORIZATION);
        String newToken = null;

        if (token != null && token.startsWith(Constants.BEARER_PREFIX)) {
            newToken = token.substring(7);
        }
        return newToken;
    }
}
