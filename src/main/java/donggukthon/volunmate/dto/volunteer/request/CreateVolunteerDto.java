package donggukthon.volunmate.dto.volunteer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
) {

}

