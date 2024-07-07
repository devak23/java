package game.wordle;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Validator {

    public static final int MAX_ALLOWED_ATTEMPTS = 6;
    public static final int MAX_ALLOWED_WORD_LENGTH = 5;

    public Result validate(Stack<String> userInput, String WORD, List<Character> availableChars) {
        String input = userInput.peek();
        List<Character> actualCharacters =  WORD.toUpperCase().chars().mapToObj(i -> (char)i).toList();
        List<Character> inputCharacters = input.toUpperCase().chars().mapToObj(i -> (char)i).toList();

        availableChars.removeAll(inputCharacters);

        List<Character> incorrectlyPlacedChars = new ArrayList<>(10);
        List<Character> correctlyPlacedChars = new ArrayList<>(10);

        for (int i = 0; i < inputCharacters.size(); i++) {
            char chr = inputCharacters.get(i);
            if (actualCharacters.contains(chr)) {
                if (actualCharacters.indexOf(chr) == i) {
                    correctlyPlacedChars.add(chr);
                } else {
                    incorrectlyPlacedChars.add(chr);
                }
            }
        }

        if (correctlyPlacedChars.size() == MAX_ALLOWED_WORD_LENGTH) {
            return Result.builder()
                    .correctPositionalChars(correctlyPlacedChars)
                    .status(Status.USER_WINS)
                    .message("You win!").build();
        }
        return Result.builder()
                .attempt(userInput.size())
                .userInput(input)
                .status(Status.NOT_OK)
                .message("Some characters are missing!")
                .correctPositionalChars(correctlyPlacedChars)
                .incorrectPositionalChars(incorrectlyPlacedChars)
                .remainingChars(availableChars)
                .build();
    }


    public Result validateInput(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return Result.builder().status(Status.NOT_OK).message("input word cannot be empty.").build();
        }

        if (userInput.trim().length() != MAX_ALLOWED_WORD_LENGTH) {
            return Result.builder().status(Status.NOT_OK).message("input word needs to be 5 characters long.").build();
        }

        return Result.builder().status(Status.OK).message("everything is good.").build();
    }

    public Result canUserPlay(Stack<String> userInput) {
        return userInput.size() == MAX_ALLOWED_ATTEMPTS
                ? Result.builder().status(Status.GAME_OVER).message("Game Over!").build()
                : Result.builder().status(Status.OK).message("").build();
    }
}
