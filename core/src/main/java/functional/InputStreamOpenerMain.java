package functional;

import functional.spec.InputStreamOpener;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputStreamOpenerMain {

    public static final String OBJECT_FILE = "src/main/resources/numbers.obj";

    public static void main(String[] args) {
        // Implementation #1: open a DataInputStream
        InputStreamOpener dataInputStreamOpener = path -> new DataInputStream(new FileInputStream(path));

        // Implementation #2: open ObjectInputStream
        InputStreamOpener objectInputStreamOpener = path -> new ObjectInputStream(new FileInputStream(path));

        // Implementation # 3: Open BufferedInputStream
        InputStreamOpener bufferedInputStreamOpener = path -> new java.io.BufferedInputStream(new FileInputStream(path));

        String fileToRead = "src/main/resources/numbers.txt";

        System.out.println("=========== DataInputStream ============");
        try (InputStream is = dataInputStreamOpener.getStream(fileToRead)) {
            printContent(is, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=========== BufferedInputStream ============");
        try (InputStream is = bufferedInputStreamOpener.getStream(fileToRead)) {
            printContent(is, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=========== ObjectInputStream ============");
        try (InputStream is = objectInputStreamOpener.getStream(OBJECT_FILE)) {
            if (!isFileValid(OBJECT_FILE)) {
                System.out.println("The object file is invalid or does not exist. Skipping ObjectInputStream processing.");
                return;
            } else {
                System.out.println("File is valid!");
            }

            ObjectInputStream ois = new ObjectInputStream(is);
            List<Chunk> chunks = (List<Chunk>) ois.readObject();
            System.out.println(chunks);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static boolean isFileValid(String path) {
        File file = new File(path);
        return file.exists() && file.isFile() && file.length() > 0;
    }


    private static void printContent(InputStream is, boolean writeToFileAsObject) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        List<Chunk> chunks = new ArrayList<>();

        while ((bytesRead = is.read(buffer)) != -1) {
            String data = new String(buffer, 0, bytesRead);
            System.out.println(data);
            chunks.add(new Chunk(data, bytesRead));
        }

        if (writeToFileAsObject) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OBJECT_FILE))) {
                oos.writeObject(chunks);
            }
        }
    }

    @Data
    @AllArgsConstructor
    static class Chunk implements Serializable {
        String data;
        int bytesRead;
    }
}
