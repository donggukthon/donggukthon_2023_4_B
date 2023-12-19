package donggukthon.volunmate.dto.myPage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record HelpListDto(
        @JsonProperty("res")
        List<HelpDto> res
) implements Serializable {
    public static HelpListDto of(List<HelpDto> res) {
        return HelpListDto.builder()
                .res(res)
                .build();
    }
}
