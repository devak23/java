import functional.EmailCheckerImperative;
import mockit.Tested;
import org.junit.Test;

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

    @Test(expected = NullPointerException.class)
    public void testNullEmail() {
        classUnderTest.testEmail(null);
    }

    @Test
    public void testEmptyEmail() {
        classUnderTest.testEmail("");
    }
}