package donggukthon.volunmate.dto.help;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record EmergencyHelpListDto(
        @JsonProperty("res")
        List<EmergencyHelpDto> res
) implements Serializable {
    public static EmergencyHelpListDto of(List<EmergencyHelpDto> res) {
        return EmergencyHelpListDto.builder()
                .res(res)
                .build();
    }
}
