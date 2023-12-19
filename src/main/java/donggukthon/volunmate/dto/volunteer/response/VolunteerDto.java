package donggukthon.volunmate.dto.volunteer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record VolunteerDto (
        @JsonProperty("team_id") Long teamId,
        @JsonProperty("title") String title,
        @JsonProperty("start_date") String startDate,
        @JsonProperty("end_date") String endDate,
        @JsonProperty("due_date") String dueDate,
        @JsonProperty("tag") List<String> tag,
        @JsonProperty("volun_count") Integer volunCount,
        @JsonProperty("cur_count") Integer curCount,
        @JsonProperty("image_url") String imageUrl,
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude,
        @JsonProperty("created_at") String created_at,
        @JsonProperty("is_hearted") Boolean isHearted
) implements Serializable {
    public static VolunteerDto of(
            Long teamId, String title,List<String> tag, String startDate,
            String endDate, String dueDate, Integer volunCount, Integer curCount,
            Double latitude, Double longitude, String created_at, String imageUrl,
            Boolean isHearted
    ){
        return VolunteerDto.builder()
                .teamId(teamId)
                .title(title)
                .tag(tag)
                .startDate(startDate)
                .endDate(endDate)
                .dueDate(dueDate)
                .endDate(endDate)
                .volunCount(volunCount)
                .curCount(curCount)
                .latitude(latitude)
                .imageUrl(imageUrl)
                .longitude(longitude)
                .created_at(created_at)
                .isHearted(isHearted)
                .build();
    }
}
