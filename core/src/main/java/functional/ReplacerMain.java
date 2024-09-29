package functional;

import functional.spec.Replacer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ReplacerMain {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Ann a 15", "Mir el 28", "D oru 33");

        List<String> resultWs = performReplace2(names, (String s) -> s.replaceAll("\\s", ""));
        List<String> replaceNr = performReplace2(names, (String s) -> s.replaceAll("\\d", ""));

        log.info("resultWs = {}", resultWs);
        log.info("replaceNr = {}", replaceNr);
    }

    public static List<String> performReplace(List<String> source, Replacer<String> replacer) {
        // source.stream().map(s -> replacer.replace(s)).toList()   // lambda way.
        return source.stream().map(replacer::replace).toList();     // method reference.
    }

    public static List<String> performReplace2(List<String> source, Replacer<String> replacer) {
        List<String> result = new ArrayList<>();
        for (String s : source) {
            result.add(replacer.replace(s));
        }

        return result;
    }
}
