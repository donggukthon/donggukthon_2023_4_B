package donggukthon.volunmate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record RequestVolunteerSignDto(
        @JsonProperty String name,
        @JsonProperty String phoneNumber,
        @JsonProperty String content
) implements Serializable {
    public static RequestVolunteerSignDto of(String name, String phoneNumber, String content) {
        return new RequestVolunteerSignDto(name, phoneNumber, content);
    }
}
