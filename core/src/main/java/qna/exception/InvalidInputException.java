package qna.exception;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode (callSuper = true)
public class InvalidInputException extends Exception {
    private String message;

    public InvalidInputException(String message) {
        super(message);
    }
}
