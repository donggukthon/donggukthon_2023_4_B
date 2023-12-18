package donggukthon.volunmate.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthSignupDto (
        @JsonProperty("name")
        String name,

        @JsonProperty("kakao_id")
        String kakaoId
) {
}
