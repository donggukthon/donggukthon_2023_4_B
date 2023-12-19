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

        @JsonProperty("start_date")
        String startDate,

        @JsonProperty("end_date")
        String endDate,

        @JsonProperty("latitude")
        Double latitude,

        @JsonProperty("longitude")
        Double longitude,

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
            Long teamId, String title, String startDate, String endDate,
            Double latitude, Double longitude, String ImageUrl, Integer volunCount,
            Integer curCount, String createdAt
    ) {
        return TeammateDto.builder()
                .teamId(teamId)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .latitude(latitude)
                .longitude(longitude)
                .ImageUrl(ImageUrl)
                .volunCount(volunCount)
                .curCount(curCount)
                .createdAt(createdAt)
                .build();
    }
}
