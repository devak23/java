package functional;
import functional.spec.Executable;
import functional.spec.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.regex.Pattern;

@Slf4j
public class EmailCheckerFunctional {
    static final Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    static Function<String, Result> emailChecker = str -> {
        if (str == null || str.isEmpty()) {
            return new Result.Failure("email cannot be blank or null");
        } else if(emailPattern.matcher(str).matches()) {
            return new Result.Success(str);
        } else {
            return new Result.Failure("invalid email pattern");
        }
    };

    public Executable validate(String email) {
        Result result = emailChecker.apply(email);
        return result instanceof Result.Success
                ? () -> sendVerification(email)
                : () -> registerError(email);
    }

    public void sendVerification(String email) {
        log.info("email verified successfully");
    }

    public void registerError(String email) {
        log.error("Problem sending email to " + email);
    }
}
