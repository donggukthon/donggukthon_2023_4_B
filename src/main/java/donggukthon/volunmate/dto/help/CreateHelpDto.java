package donggukthon.volunmate.dto.help;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateHelpDto (
        @JsonProperty("is_emergency") Boolean isEmergency,
        @JsonProperty("title") String title,
        @JsonProperty("content") String content,
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude
)  {
}