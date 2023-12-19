package donggukthon.volunmate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CreateVolunteerDto(
        @JsonProperty("title") String title,
        @JsonProperty("content") String content,
        @JsonProperty("tag") List<String> tag,
        @JsonProperty("start_date") LocalDateTime startDate,
        @JsonProperty("end_date") LocalDateTime endDate,
        @JsonProperty("due_date") LocalDateTime dueDate,
        @JsonProperty("person") Integer person,
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude
) implements Serializable {
    public static CreateVolunteerDto of(
            String title,String content,List<String> tag, LocalDateTime startDate,
            LocalDateTime endDate,LocalDateTime dueDate, int person, Double latitude,Double longitude
    ){
        return CreateVolunteerDto.builder().
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
                .build();
    }
}

