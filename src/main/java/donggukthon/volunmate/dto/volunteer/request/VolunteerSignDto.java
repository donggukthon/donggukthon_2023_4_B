package donggukthon.volunmate.dto.volunteer.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record VolunteerSignDto(
        @JsonProperty("name") String name,
        @JsonProperty("phone_number") String phoneNumber,
        @JsonProperty("content") String content
) {
}
