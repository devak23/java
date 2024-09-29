package functional;

import functional.spec.RemovalStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrategyPatternMain {

    public static void main(String[] args) {

        String text = "This text is from 29 Sep 2024. I am surprised it has lasted this longer.";

        String noNumbers = Remover.remove(text, s -> s.replaceAll("\\d", ""));
        String noSpaces = Remover.remove(text, s -> s.replaceAll("\\s", ""));

        log.info("NoNumbers: {}", noNumbers);
        log.info("NoSpaces: {}", noSpaces);
    }

    static class Remover {

        private Remover() {}

        public static String remove(String source, RemovalStrategy strategy) {
            return strategy.applyRemoval(source);
        }
    }

}
