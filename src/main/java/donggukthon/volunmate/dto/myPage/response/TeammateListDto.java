package donggukthon.volunmate.dto.myPage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record TeammateListDto(
        @JsonProperty("res")
        List<TeammateDto> res
) implements Serializable {
    public static TeammateListDto of(List<TeammateDto> res) {
        return TeammateListDto.builder()
                .res(res)
                .build();
    }
}
