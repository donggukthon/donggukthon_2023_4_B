package donggukthon.volunmate.dto.myPage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record HelpDto(
        @JsonProperty("help_id")
        Long helpId,

        @JsonProperty("title")
        String title,

        @JsonProperty("lattitude")
        Float lattitude,

        @JsonProperty("longitude")
        Float longitude,

        @JsonProperty("image_url")
        String imageUrl,

        @JsonProperty("emergency")
        Boolean emergency,

        @JsonProperty("created_at")
        String createdAt
) implements Serializable {
    public static HelpDto of(
            Long helpId, String title, Float lattitude, Float longitude,
            String imageUrl, Boolean emergency, String createdAt
    ) {
        return HelpDto.builder()
                .helpId(helpId)
                .title(title)
                .lattitude(lattitude)
                .longitude(longitude)
                .imageUrl(imageUrl)
                .emergency(emergency)
                .createdAt(createdAt)
                .build();
    }
}
