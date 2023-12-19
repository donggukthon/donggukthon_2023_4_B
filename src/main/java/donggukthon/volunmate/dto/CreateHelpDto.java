package donggukthon.volunmate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import java.io.Serializable;
@Builder
public record CreateHelpDto (
        @JsonProperty("is_emergency") Boolean isEmergency,
        @JsonProperty("title") String title,
        @JsonProperty("content") String content,
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude
) implements Serializable {
public static CreateHelpDto of(
        Boolean isEmergency,String title,String content, Double latitude,Double longitude
        ){
        return CreateHelpDto.builder()
                .isEmergency(isEmergency)
                .title(title)
                .content(content)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        }
}