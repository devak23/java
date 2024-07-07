package game.wordle;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class WordleRepository {
    private final List<String> repository = new ArrayList<>(20);

    public WordleRepository() {
        repository.addAll(readWordsFromFile("wordle.txt"));
        log.info("repository initialized. Words = {}", repository.size());
    }

    public String fetchRandomWord() {
        Random rand = new Random();
        int randomIndex =  rand.nextInt(0, repository.size()-1);
        return repository.get(randomIndex);
    }

    private List<String> readWordsFromFile(String file) {
        try {
            URI uri = WordleRepository.class.getClassLoader().getResource(file).toURI();
            return Files.lines(Paths.get(uri)).toList();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
