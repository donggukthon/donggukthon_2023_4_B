package donggukthon.volunmate.dto.help;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record EmergencyHelpDto (
        @JsonProperty("help_id") Long helpId,
        @JsonProperty("image_url") String imageUrl,
        @JsonProperty("title") String title,
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude,
        @JsonProperty("created_at") String createdAt
) implements Serializable {

        public static EmergencyHelpDto of(
                Long helpId, String imageUrl, String title,
                Double latitude, Double longitude, String createdAt
        ) {
            return EmergencyHelpDto.builder()
                    .helpId(helpId)
                    .imageUrl(imageUrl)
                    .title(title)
                    .latitude(latitude)
                    .longitude(longitude)
                    .createdAt(createdAt)
                    .build();
        }
}
