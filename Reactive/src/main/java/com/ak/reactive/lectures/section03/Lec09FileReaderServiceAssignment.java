package com.ak.reactive.lectures.section03;

import com.ak.reactive.assignment.section03.FileReaderService;
import com.ak.reactive.utils.Util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Lec09FileReaderServiceAssignment {
    private static final Path PATH = Paths.get("src/main/resources");

    public static void main(String[] args) {
        FileReaderService frs = new FileReaderService();
        Path path = PATH.resolve("matrix.txt");
        frs.readFile(path).subscribe(Util.getSubscriber());

        frs.readFile(path).take(5).subscribe(Util.getSubscriber());
    }
}
