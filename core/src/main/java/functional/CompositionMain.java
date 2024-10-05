package functional;

import functional.model.Editor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.stream.IntStream;

@Slf4j
public class CompositionMain {

    public static void main(String[] args) {
        var ints = IntStream.rangeClosed(1, 10).boxed().toList();

        Function<Integer, Integer> doubleIt = i -> i * 2;
        var toHex = doubleIt.andThen(Integer::toHexString);
        var toBinary = doubleIt.andThen(Integer::toBinaryString);
        ints.stream().map(toBinary).forEach(log::info);
        ints.stream().map(toHex).forEach(log::info);


        Function<Double, Double> doubleItAgain = i -> i * 2;
        Function<Double, Double> squareIt = i -> Math.pow(i, 2);
        Function<Double, Double> doubleAndSquare = doubleItAgain.andThen(squareIt);
        Function<Double, Double> composeIt = doubleItAgain.compose(squareIt);

        double result = doubleAndSquare.apply(4.0d);
        double resultCompose = composeIt.apply(4.0d);

        log.info("result: {}", result);                 // produces 64.0
        log.info("resultCompose: {}", resultCompose);   // produces 32.0

        // In the first result, the function doubleItAgain (say denoted by f) gets applied to 4.0 making it 8.0 and
        // then the next function squareIt (denoted by g) gets applied to the result of f(4.0). Therefore, we have a
        // case of g(f(4.0)) = 64.0.
        // In the next result, i.e. in compose, the squareIt function g(4.0) gets called first followed by f(16.0) which
        // causes it to doubleItAgain thereby giving us the result 32.0. This is the case of f(g(4.0))
        // Therefore g(f(4.0)) = 32.0 and
        //           f(g(4.0)) = 64.0
        // where f = doubleItAgain and g = squareIt



        // If composition just switches the function application. Can we work up an example where we have 2 functions
        // producing two different messages being clubbed together? Like: Hello Abhay! How are you doing today?
        // and other message like: How are you doing today? Hello Abhay!
        Function<String, String> greetings = name -> STR."Hello \{name}";
        Function<String, String> question = name -> STR."How are you doing today? \{name}";
        Function<String, String> greetAndAsk = greetings.andThen(question);
        Function<String, String> greetCompose = greetings.compose(question);

        log.info("chaining: {}", greetAndAsk.apply("Abhay!"));
        log.info("compose: {}", greetCompose.apply("Abhay!"));

        // OUTPUT:
        // 02:24:03.933 [main] INFO functional.CompositionMain -- chaining: How are you doing today? Hello Abhay!
        // 02:24:03.933 [main] INFO functional.CompositionMain -- compose: Hello How are you doing today? Abhay!

        // As you can see that is not how we had intended in the problem statement. The point that seems to be missed is
        // composition of function applies on the result of the first function. Therefore instead of "How are you doing
        // today? Hello Abhay!" What we end up getting is "Hello (How are you doing today?) Abhay!" as the question
        // gets asked first and the output is then passed to greetings. Therefore, the point to drive home is function
        // composition works on the entire output.

        // What happens if we reverse the arguments of the greetAndAsk and greetCompose?
        greetAndAsk = question.andThen(greetings);
        greetCompose = question.compose(greetings);

        log.info("chaining2: {}", greetAndAsk.apply("Abhay!"));
        log.info("compose2: {}", greetCompose.apply("Abhay!"));

        // Here is an example of how it is to be used properly
        Function<String, String> introduction = Editor::addIntroduction;
        Function<String, String> editor = introduction.andThen(Editor::addBody).andThen(Editor::addConclusion);
        String article  = editor.apply("\r\nArticle name\r\n");
        log.info("article: {}", article);

        Function<String, String> conclusion = Editor::addConclusion;
        Function<String, String> body = Editor::addBody;
        Function<String, String> editorCompose = conclusion.compose(body).compose(introduction);
        log.info("editorCompose: {}", editorCompose.apply("\r\nArticle name\r\n"));

        // OUTPUT:
        // 02:41:44.813 [main] INFO functional.CompositionMain -- article:
        // Article name
        //
        // Introduction: ...
        // Body: ...
        // End: ...
        // 02:41:44.813 [main] INFO functional.CompositionMain -- editorCompose:
        // Article name
        //
        // Introduction: ...
        // Body: ...
        // End: ...
    }
}
