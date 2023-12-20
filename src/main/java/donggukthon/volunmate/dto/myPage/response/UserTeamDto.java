package donggukthon.volunmate.dto.myPage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserTeamDto(
        @JsonProperty("team_id")
        Long teamId,

        @JsonProperty("writer")
        String writer,

        @JsonProperty("degree")
        Float degree,

        @JsonProperty("content")
        String content
) implements Serializable {
    public static UserTeamDto of(
            Long teamId, String writer, Float degree, String content
    ) {
        return UserTeamDto.builder()
                .teamId(teamId)
                .writer(writer)
                .degree(degree)
                .content(content)
                .build();
    }
}
