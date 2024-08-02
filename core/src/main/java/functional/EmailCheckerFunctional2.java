package functional;
import functional.spec.Effect;
import functional.spec.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.regex.Pattern;

@Slf4j
public class EmailCheckerFunctional2 {
    static final Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    static Function<String, Result> emailChecker = str -> {
        if (str == null || str.isEmpty()) {
            return new Result.Failure("Email cannot be blank or null");
        } else if(emailPattern.matcher(str).matches()) {
            return new Result.Success(str);
        } else {
            return new Result.Failure("invalid email pattern");
        }
    };

    public void validate(String email) {
        emailChecker.apply(email).bind(onSuccess, onFailure);
    }

    Effect<String> onSuccess = s -> log.info("email - {} verified successfully", s);
    Effect<String> onFailure = s -> log.error("couldn't send email. {}", s);

    public static void main(String[] args) {
        EmailCheckerFunctional2 emailChecker = new EmailCheckerFunctional2();
        emailChecker.validate("john.doe@gmail.com");
        emailChecker.validate(null);
        emailChecker.validate("");
    }
}
