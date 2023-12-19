package donggukthon.volunmate.dto.myPage.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserLocationDto(
        @JsonProperty("my_latitude")
        Double myLatitude,

        @JsonProperty("my_longitude")
        Double myLongitude
) {
}
