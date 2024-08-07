package functional;


import org.junit.jupiter.api.Test;

public class EmailCheckerFunctionalTest {
    private EmailCheckerFunctional classUnderTest = new EmailCheckerFunctional();

    @Test
    public void testValidate() {
        classUnderTest.validate("john.doe@jpmchase.com").execute();
        classUnderTest.validate("").execute();
        classUnderTest.validate(null).execute();
    }
}