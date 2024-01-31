package com.ak.reactive.assignment.section03;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Assignment is the FileReaderService reads a file (on the path) and emits the contents of the file line by line.
 * The consumer can request contents of the file line by line or can request all the contents in one go or can choose
 * to select a couple of lines itself.
 */
public class FileReaderService {
    // Defining where our file is residing


    private Callable<BufferedReader> openReader(Path path) {
        return () -> Files.newBufferedReader(path);
    }

    private BiFunction<BufferedReader, SynchronousSink<String>, BufferedReader> read() {
        return (br, sink) -> {
            try {
                String line = br.readLine();
                if (Objects.isNull(line)) {
                    sink.complete();
                } else {
                    sink.next(line);
                }
            } catch (Exception ex) {
                sink.error(ex);
            }
            return br;
        };
    }

    private Consumer<BufferedReader> closeReader() {
        return br -> {
            try {
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
    }

    public Flux<String> readFile(Path path) {
        return Flux.generate(openReader(path),
                read(),
                closeReader());
    }
}
