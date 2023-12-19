package donggukthon.volunmate.dto.myPage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record ParticipantsDto (
        @JsonProperty("name")
        String name,

        @JsonProperty("phone_number")
        String phoneNumber
) implements Serializable {
    public static ParticipantsDto of(String name, String phoneNumber) {
        return ParticipantsDto.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }
}
