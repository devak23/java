import functional.EmailCheckerImperative;
import mockit.Tested;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class EmailCheckerFunctionalImperativeTest {

    @Tested
    EmailCheckerImperative classUnderTest = new EmailCheckerImperative();

    @Test
    public void testValidEmail() {
        classUnderTest.testEmail("john.doe@jpmchase.com");
    }

    @Test
    public void testInvalidEmail() {
        classUnderTest.testEmail("testMe");
    }

    @Test()
    public void testNullEmail() {
        Assertions.assertThatThrownBy(() -> classUnderTest.testEmail(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void testEmptyEmail() {
        classUnderTest.testEmail("");
    }
}