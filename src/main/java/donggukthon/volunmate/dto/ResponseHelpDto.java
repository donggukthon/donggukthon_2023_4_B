package donggukthon.volunmate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record ResponseHelpDto(
        @JsonProperty("help_id") Long helpId,
        @JsonProperty("is_emergency") Boolean isEmergency,
        @JsonProperty("image_url") String imageUrl,
        @JsonProperty("title") String title,
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude,
        @JsonProperty("created_at") LocalDateTime createdAt
) implements Serializable {

    public static ResponseHelpDto of(
            Long helpId,Boolean isEmergency, String imageUrl, String title,
            Double latitude, Double longitude, LocalDateTime createdAt
    ) {
        return ResponseHelpDto.builder()
                .helpId(helpId)
                .isEmergency(isEmergency)
                .imageUrl(imageUrl)
                .title(title)
                .latitude(latitude)
                .longitude(longitude)
                .createdAt(createdAt)
                .build();
    }
}