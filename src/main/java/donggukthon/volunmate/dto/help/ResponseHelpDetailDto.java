package donggukthon.volunmate.dto.help;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record ResponseHelpDetailDto(
        @JsonProperty("is_emergency") Boolean isEmergency,
        @JsonProperty("image_url") String imageUrl,
        @JsonProperty("title") String title,
        @JsonProperty("content") String content,
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude,
        @JsonProperty("name") String name,
        @JsonProperty("degree") Float degree,
        @JsonProperty("kakao_id") String kakaoId,
        @JsonProperty("created_at") String createdAt
) implements Serializable {

    public static ResponseHelpDetailDto of(
            Boolean isEmergency,String imageUrl,String title, String content, Double latitude, Double longitude,
            String name, Float degree,String kakaoId, String createdAt
    ) {
        return ResponseHelpDetailDto.builder()
                .isEmergency(isEmergency)
                .imageUrl(imageUrl)
                .title(title)
                .content(content)
                .latitude(latitude)
                .longitude(longitude)
                .name(name)
                .degree(degree)
                .kakaoId(kakaoId)
                .createdAt(createdAt)
                .build();
    }
}
