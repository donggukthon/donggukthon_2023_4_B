package donggukthon.volunmate.security.filter;

import donggukthon.volunmate.constant.Constants;
import donggukthon.volunmate.security.CustomUserDetails;
import donggukthon.volunmate.security.jwt.JwtProvider;
import donggukthon.volunmate.security.service.CustomUserDetailsService;
import donggukthon.volunmate.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = JwtProvider.resolveToken(request);
        Claims claims = jwtUtil.validateToken(token);
        String socialId = claims.get(Constants.SOCIAL_ID_CLAIM_NAME).toString();

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(socialId);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Constants.NO_AUTH_WHITE_LABEL.contains(request.getRequestURI());
    }
}
