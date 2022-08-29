package functional;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public class EmailCheckerImperative {
    final Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    public void testEmail(String email) {
        if (emailPattern.matcher(email).matches()) {
            sendVerification(email);
        } else {
            log.error("invalid email");
        }
    }

    public void sendVerification(String email) {
        log.info("email verified successfully");
    }
}
