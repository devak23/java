package lambdas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderDemo {

    public static void main(String[] args) throws IOException {
        FileReaderDemo demo = new FileReaderDemo();
        String _2lines = demo.processFile(args[0], (BufferedReader reader) -> reader.readLine() + " ---- " + reader.readLine());
        System.out.println(_2lines);

        String _1line = demo.processFile(args[0], (BufferedReader reader) -> reader.readLine());
        System.out.println(_1line);
    }

    private String processFile(String filename, BufferedReaderProcessor processor) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return processor.process(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
