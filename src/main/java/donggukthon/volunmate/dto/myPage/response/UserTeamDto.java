package donggukthon.volunmate.dto.myPage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserTeamDto(
        @JsonProperty("team_id")
        Long teamId,

        @JsonProperty("title")
        String title,

        @JsonProperty("writer")
        String writer,

        @JsonProperty("content")
        String content
) implements Serializable {
    public static UserTeamDto of(
            Long teamId, String title, String writer, String content
    ) {
        return UserTeamDto.builder()
                .teamId(teamId)
                .title(title)
                .writer(writer)
                .content(content)
                .build();
    }
}
