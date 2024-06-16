### Source
Udemy: Springboot testing - Ramesh Fadtare
www.udemy.com/course/testing-spring-boot-application-with-junit-and-mockito

### live template for test
```
@DisplayName("$CURSOR$")
@Test
public void given$OBJECT$_when$PRECONDITION$_then$POSTCONDITION$() {
    // given - define precondition for test
    $OBJECT_TO_TEST$
    
    // when - perform the desiredAction
    $DEFINE_PRECONDITION$
    
    // then - verify the output
    $DEFINE_POSTCONDITON$
}
```