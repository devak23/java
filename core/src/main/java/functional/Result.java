package functional;

import lombok.Builder;
import lombok.Getter;

public interface Result {

    class Success implements Result {}

    @Getter
    @Builder
    class Failure implements Result {
        private final String errorMessage;
    }
}
