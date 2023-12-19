package donggukthon.volunmate.dto.myPage.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserLocationDto (
        @JsonProperty("name") String name,
        @JsonProperty("kakao_id") String kakaoId,
        @JsonProperty("my_latitude") Double myLatitude,

        @JsonProperty("my_longitude") Double myLongitude
) {
}
