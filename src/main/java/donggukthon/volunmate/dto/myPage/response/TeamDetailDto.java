package donggukthon.volunmate.dto.myPage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record TeamDetailDto (
        @JsonProperty("team_id")
        Long teamId,

        @JsonProperty("title")
        String title,

        @JsonProperty("participants")
        List<ParticipantsDto> participants
) implements Serializable {
    public static TeamDetailDto of(
            Long teamId, String title, List<ParticipantsDto> participants
    ) {
        return TeamDetailDto.builder()
                .teamId(teamId)
                .title(title)
                .participants(participants)
                .build();
    }
}
