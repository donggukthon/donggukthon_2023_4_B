package donggukthon.volunmate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ResponseVolunteerDetailDto(
        @JsonProperty("title") String title,
        @JsonProperty("content") String content,
        @JsonProperty("tag") List<String> tag,
        @JsonProperty("start_date") LocalDateTime startDate,
        @JsonProperty("end_date") LocalDateTime endDate,
        @JsonProperty("due_date") LocalDateTime dueDate,
        @JsonProperty("person") Integer person,
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude,
        @JsonProperty("name") String name,
        @JsonProperty("degree") Float degree,
        @JsonProperty("created_at") LocalDateTime created_at
) implements Serializable {
    public static ResponseVolunteerDetailDto of(
            String title,String content,List<String> tag, LocalDateTime startDate,
            LocalDateTime endDate,LocalDateTime dueDate, int person, Double latitude,Double longitude
            ,String name, Float degree, LocalDateTime created_at
    ){
        return ResponseVolunteerDetailDto.builder().
                title(title)
                .content(content)
                .tag(tag)
                .startDate(startDate)
                .endDate(endDate)
                .dueDate(dueDate)
                .endDate(endDate)
                .person(person)
                .latitude(latitude)
                .longitude(longitude)
                .name(name)
                .degree(degree)
                .created_at(created_at)
                .build();
    }
}
