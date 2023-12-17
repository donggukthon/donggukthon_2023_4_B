package donggukthon.volunmate.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ELoginProvider {
    GOOGLE("google");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
