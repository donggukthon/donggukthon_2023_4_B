package donggukthon.volunmate.dto.myPage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record TeamDetailListDto (
        @JsonProperty("res")
        List<TeamDetailDto> res
) implements Serializable {
    public static TeamDetailListDto of(List<TeamDetailDto> res) {
        return TeamDetailListDto.builder()
                .res(res)
                .build();
    }
}
