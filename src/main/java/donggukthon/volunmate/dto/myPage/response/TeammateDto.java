package donggukthon.volunmate.dto.myPage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record TeammateDto(
        @JsonProperty("team_id")
        Long teamId,

        @JsonProperty("title")
        String title,

        @JsonProperty("date")
        String date,

        @JsonProperty("lattitude")
        Float lattitude,

        @JsonProperty("longitude")
        Float longitude,

        @JsonProperty("image_url")
        String ImageUrl,

        @JsonProperty("volun_count")
        Integer volunCount,

        @JsonProperty("cur_count")
        Integer curCount,

        @JsonProperty("created_at")
        String createdAt
) implements Serializable {
    public static TeammateDto of(
            Long teamId, String title, String date, Float lattitude,
            Float longitude, String ImageUrl, Integer volunCount,
            Integer curCount, String createdAt
    ) {
        return TeammateDto.builder()
                .teamId(teamId)
                .title(title)
                .date(date)
                .lattitude(lattitude)
                .longitude(longitude)
                .ImageUrl(ImageUrl)
                .volunCount(volunCount)
                .curCount(curCount)
                .createdAt(createdAt)
                .build();
    }
}
