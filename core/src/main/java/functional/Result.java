package functional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface Result {

    void bind(Effect<String> onSuccess, Effect<String> onFailure);

    @Getter
    @Builder
    @AllArgsConstructor
    class Success implements Result {
        private final String value;
        @Override
        public void bind(Effect<String> onSuccess, Effect<String> onFailure) {
            onSuccess.apply(value);
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    class Failure implements Result {

        private final String errorMessage;

        @Override
        public void bind(Effect<String> onSuccess, Effect<String> onFailure) {
            onFailure.apply(errorMessage);
        }
    }
}
