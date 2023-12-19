package donggukthon.volunmate.dto.volunteer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record VolunteerListDto(
        @JsonProperty("res")
        List<VolunteerDto> res
) implements Serializable {
    public static VolunteerListDto of(List<VolunteerDto> res) {
        return VolunteerListDto.builder()
                .res(res)
                .build();
    }
}
