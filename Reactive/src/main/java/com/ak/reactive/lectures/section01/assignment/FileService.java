package com.ak.reactive.lectures.section01.assignment;

import reactor.core.publisher.Mono;

/**
 * We are required to create a File Service which will
 * 1. Read the contents of a file
 * 2. Write contents to a file
 * 3. Delete the file
 * using the Mono Publisher concepts that we learnt in Lecture 01
 */
public class FileService {

    // So first let's define the file operations. These are straightforward. We will define these operations in a
    // FileRepository (which gets autowired into the service) in a real project. We will ofcourse just use the 'new'
    // operator. All the operations required by this service will be defined in this repository

    private final FileRepository fileRepository = new FileRepository();

    // The first API requires us to read the contents of the file which is an implementation encapsulated by
    // FileRepository. Since we now have the data we will be using the `fromSupplier` API thereby connecting the
    // data to the stream using the Mono.fromSupplier.
    public Mono<String> read(String file) {
        return Mono.fromSupplier(() -> fileRepository.readFile(file));
    }

    // Same goes for writing to the file as well. Note that this method can return a void in which case you will have
    // the following definition.
    // public static Mono<Void> write(String file, String contents)
    public Mono<String> write(String file, String contents) {
        return Mono.fromSupplier(() -> fileRepository.writeToFile(file, contents));
    }

    // Lastly we define the delete method which again delegates to the fileRepository to delete the file. This method
    // too could be a Mono<Void> instead.
    public Mono<String> delete(String file) {
        return Mono.fromSupplier(() -> fileRepository.delete(file));
    }

    // The write and delete API's don't have to return String. I have used it to convey the operation of was over
    // so that the client receives some data of the operation being completed.
}
