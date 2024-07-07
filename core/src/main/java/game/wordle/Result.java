package game.wordle;

import lombok.Builder;

import java.util.List;

@Builder
public record Result(Status status
        , String message
        , String userInput
        , int attempt
        , List<Character> correctPositionalChars
        , List<Character> incorrectPositionalChars
        , List<Character> remainingChars) {

    public boolean isOk() {
        return status.equals(Status.OK);
    }
}
