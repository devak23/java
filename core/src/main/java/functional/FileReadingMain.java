package functional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class FileReadingMain {

    public static void main(String[] args) throws Exception {
        var main = new FileReadingMain();
        System.out.println(main.processFileViaInputstream());

        // reading a single line
        System.out.println(main.processFileViaUri(BufferedReader::readLine));

        // reading 2 lines
        System.out.println(main.processFileViaUri(br -> br.readLine() + br.readLine()));

        // as you can see now the behavior is parameterized.
    }

    public String processFileViaInputstream() throws Exception {
        var inputstream = FileReadingMain.class.getClassLoader().getResourceAsStream("numbers.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputstream)))) {
            return reader.readLine();
        }
    }

    public String processFileViaUri(BufferedReaderProcessor processor) throws Exception {
        var uri = FileReadingMain.class.getClassLoader().getResource("numbers.txt").toURI();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(uri)))) {
            return processor.process(reader);
        }
    }


    public interface BufferedReaderProcessor {
        String process(BufferedReader b) throws Exception;
    }
}
