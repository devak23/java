package com.ak.reactive.lectures.section01.assignment;

import com.ak.reactive.utils.Util;

/**
 * @see FileService for problem definition
 */
public class FileMain {
    public static void main(String[] args) {
        FileService fs = new FileService();

        fs.read("quotes.txt")
                .subscribe(Util.onNext());
        fs.write("MyFile.txt", "I love coding Reactive programming")
                .subscribe(Util.onNext());
        fs.delete("MyFile.txt")
                .subscribe(Util.onNext());
    }
}
