package game.wordle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Wordle {
    public static final String NEW_LINE = "\r\n";
    private final WordleRepository repository;
    private final Validator validator;
    private final String WORD;
    private Stack<String> userInput;
    private List<Character> availableChars;

    public Wordle() {
        repository = new WordleRepository();
        validator = new Validator();
        WORD = repository.fetchRandomWord();
        userInput = new Stack<>();
        availableChars = new ArrayList<>();
        initializeAvailableChars(availableChars);
    }

    public void start() {
        while (true) {
            String input = readWord();
            Result validWord = validator.validateInput(input);
            if (validWord.isOk()) {
                userInput.push(input);

                Result result = validator.validate(userInput, WORD, availableChars);
                prompt(result);

                if (result.isOk()) {
                    break;
                }

                Result canPlay = validator.canPlay(userInput);
                if (!canPlay.isOk()) {
                    prompt(canPlay);
                    break;
                }
            }
        }
    }

    private void prompt(Result result) {
        StringBuilder builder = new StringBuilder();
        builder.append("Attempt # ").append(result.attempt()).append(": ").append(NEW_LINE)
                .append("Word chosen: ").append(result.userInput()).append(NEW_LINE);
        if (result.isOk() && result.correctPositionalChars().size() == 5) {
            System.out.println(result.message());
        } else {
            System.out.println(result.message());
            builder.append("Correctly placed characters are: ").append(result.correctPositionalChars()).append(NEW_LINE);
            builder.append("Incorrectly placed characters are: ").append(result.incorrectPositionalChars()).append(NEW_LINE);
            builder.append("Remaining characters to try: ").append(result.remainingChars()).append(NEW_LINE);
            System.out.println(builder.toString());
        }
    }

    private String readWord() {
        System.out.println("--------------------");
        System.out.println("Make a guess");
        System.out.println("--------------------");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toUpperCase();
    }

    public void initializeAvailableChars(List<Character> chars) {
        chars.add('A');
        chars.add('B');
        chars.add('C');
        chars.add('D');
        chars.add('E');
        chars.add('F');
        chars.add('G');
        chars.add('H');
        chars.add('I');
        chars.add('J');
        chars.add('K');
        chars.add('L');
        chars.add('M');
        chars.add('N');
        chars.add('O');
        chars.add('P');
        chars.add('Q');
        chars.add('R');
        chars.add('S');
        chars.add('T');
        chars.add('U');
        chars.add('V');
        chars.add('W');
        chars.add('X');
        chars.add('Y');
        chars.add('Z');
    }
}
