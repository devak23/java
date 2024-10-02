package functional;

import functional.spec.Eraser;
import functional.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class ReplacerMain {

    public static void main(String[] args) {
        // Here is list of strings which contains spaces and numbers that we would like to get rid off.
        List<String> names = Arrays.asList("Ann a 15", "Mir el 28", "D oru 33");

        // In a traditional way, we will define a util function that will do the job for us. To be honest, in a toy
        // example we wouldn't even be bothered with creating a util function, but let's assume this isn't as trivial
        // as it is. So we will define replacement functions in a Utils class and use it like so:
        List<String> traditionalWayToErase = names.stream()
                .map(CommonUtil::eraseSpace)
                .map(CommonUtil::eraseNumbers)
                .toList();

        log.info("traditionalWayToErase = {}", traditionalWayToErase);
        // where CommonUtil.eraseSpace and CommonUtil.eraseNumbers are just 2 methods implemented. Nothing wrong with
        // this implementation except the fact that the boilerplate code is more.

        // With Functional interfaces, here we define the implementation of the Eraser via a lambda function. The first
        // one replaces all spaces and second one replaces all numbers.
        Eraser<String> spaceEraser = s -> s.replaceAll("\\s", "");
        Eraser<String> numberEraser = s -> s.replaceAll("\\d", "");

        // As you can see the same interface is being used to provide different functionality, the same thing our
        // Utils function did. And we can now apply it as we normally do.
        List<String> erasedSpaces = performErasure(names, spaceEraser);
        log.info("erasedSpaces = {}", erasedSpaces);

        List<String> erasedNumbers = performErasure(names, numberEraser);
        log.info("erasedNumbers = {}", erasedNumbers);


        // If we want to apply both, we can create a higher order function that does both or apply each lambda
        // individually
        List<String> eraseSpacesAndIntegers = names.stream()
                .map(numberEraser::eraseAll)
                .map(spaceEraser::eraseAll)
                .toList();
        log.info("eraseSpacesAndIntegers = {}", eraseSpacesAndIntegers);

        // Using the higher order function, we utilize the Java's Function composition like so:
        Function<String, String> funcEraseSpace = s -> s.replaceAll("\\s", "");
        Function<String, String> funcEraseNumber = s -> s.replaceAll("\\d", "");
        Function<String, String> funcHOF = funcEraseNumber.andThen(funcEraseSpace);
        List<String> erasedNumbersAndSpaces = names.stream().map(funcHOF::apply).toList();
        log.info("higher order function output = {}", erasedNumbersAndSpaces);

    }

    public static List<String> performErasure(List<String> source, Eraser<String> eraser) {
        // source.stream().map(s -> replacer.replace(s)).toList()   // lambda way.
        return source.stream().map(eraser::eraseAll).toList();     // method reference.
    }
}
