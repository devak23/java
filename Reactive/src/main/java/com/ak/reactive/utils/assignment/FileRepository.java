package com.ak.reactive.utils.assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @see FileService for problem definition
 */
public class FileRepository {

    // So we define a base path where our file lies.
    private static final Path PATH = Paths.get("src/main/resources");
    // Be careful with the path string. It shouldn't start with a '/' else the program is not able to find the file.

    // Then we define a method that will read the file. The `PATH.resolve` essentially locates the file by creating a
    // valid path to the file provided. As per the Java documentation, it:
    // Converts a given path string to a Path and resolves it against this Path in exactly the manner specified by the
    // resolve method.
    // For example, suppose that the name separator is "/" and a path represents "foo/bar", then invoking this method
    // with the path string "gus" will result in the Path "foo/bar/gus".
    // The readString just reads the entire file in memory and passes on to us as a string.
    public String readFile(String file) {
        try {
            return Files.readString(PATH.resolve(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Similarly, we define another method to write a file (given by the parameters - filename and contents)
    public String writeToFile(String file, String contents) {
        try {
            Files.writeString(PATH.resolve(file), contents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return String.format("%s bytes wrote into the file: %s", contents.getBytes().length, file);
    }

    // ... and finally we define the 'delete file' api that the FileService can invoke.
    public String delete(String file) {
        try {
            Files.delete(PATH.resolve(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return String.format("Deleted file: %s", file);
    }

    // Note that all these 3 methods (the actual data/business logic providers) aren't connected to the publisher.
    // There is no element of "asynchronicity" in these implementations. They could be if you wanted these
    // implementations to be asynchronous in nature.
}
