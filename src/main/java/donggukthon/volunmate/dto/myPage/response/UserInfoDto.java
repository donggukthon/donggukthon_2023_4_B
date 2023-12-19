package donggukthon.volunmate.dto.myPage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserInfoDto (
        @JsonProperty("name")
        String name,

        @JsonProperty("degree")
        Float degree,

        @JsonProperty("mate")
        Integer mate,

        @JsonProperty("help")
        Integer help,

        @JsonProperty("heart")
        Integer heart
) implements Serializable {
    public static UserInfoDto of(
            String name, Float degree, Integer mate,
            Integer help, Integer heart
    ) {
        return UserInfoDto.builder()
                .name(name)
                .degree(degree)
                .mate(mate)
                .help(help)
                .heart(heart)
                .build();
    }
}
