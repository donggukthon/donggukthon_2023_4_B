package donggukthon.volunmate.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthSignupDto (
        @JsonProperty("name")
        String name,

        @JsonProperty("kakao_id")
        String kakaoId,

        @JsonProperty("my_latitude")
        Double myLatitude,

        @JsonProperty("my_longitude")
        Double myLongitude

) {
}
