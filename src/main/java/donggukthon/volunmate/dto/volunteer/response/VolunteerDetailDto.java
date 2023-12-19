package donggukthon.volunmate.dto.volunteer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record VolunteerDetailDto (
        @JsonProperty("title") String title,
        @JsonProperty("content") String content,
        @JsonProperty("tag") List<String> tag,
        @JsonProperty("start_date") String startDate,
        @JsonProperty("end_date") String endDate,
        @JsonProperty("due_date") String dueDate,
        @JsonProperty("volun_count") Integer volunCount,
        @JsonProperty("cur_count") Integer curCount,
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude,
        @JsonProperty("name") String name,
        @JsonProperty("degree") Float degree,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("image_url") String imageUrl
) implements Serializable {
    public static VolunteerDetailDto of(
            String title, String content, List<String> tag, String startDate, String endDate,
            String dueDate, Double latitude, Double longitude, String name, Float degree, String createdAt,
            Integer volunCount, Integer curCount, String imageUrl
    ){
        return VolunteerDetailDto.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .startDate(startDate)
                .dueDate(dueDate)
                .endDate(endDate)
                .volunCount(volunCount)
                .curCount(curCount)
                .latitude(latitude)
                .longitude(longitude)
                .name(name)
                .degree(degree)
                .createdAt(createdAt)
                .imageUrl(imageUrl)
                .build();
    }
}
