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

    public boolean isGameOver() {
        return status.equals(Status.GAME_OVER);
    }

    public boolean doesUserWin() {
        return status.equals(Status.USER_WINS);
    }
}
