package donggukthon.volunmate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ResponseVolunteerDto(
        @JsonProperty("team_id") Long teamId,
        @JsonProperty("title") String title,
        @JsonProperty("start_date") LocalDateTime startDate,
        @JsonProperty("end_date") LocalDateTime endDate,
        @JsonProperty("due_date") LocalDateTime dueDate,
        @JsonProperty("tag") List<String> tag,
        @JsonProperty("volun_count") Integer volunCount,
        @JsonProperty("cur_count") Integer curCount,
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude,
        @JsonProperty("created_at") LocalDateTime created_at,
        @JsonProperty("is_hearted") Boolean isHearted
) implements Serializable {
    public static ResponseVolunteerDto of(
            Long teamId,String title,List<String> tag, LocalDateTime startDate,
            LocalDateTime endDate,LocalDateTime dueDate,Integer volunCount,Integer curCount,
            Double latitude,Double longitude, LocalDateTime created_at
    ){
        return ResponseVolunteerDto.builder()
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
                .longitude(longitude)
                .created_at(created_at)
                .build();
    }
}
