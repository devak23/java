package functional;

import functional.model.Author;
import functional.model.Book;
import functional.model.Bookshelf;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MultiMap2Main {

    public static void main(String[] args) {
        List<Bookshelf> classic = Author.getSampleAuthors()
                .stream()
                .flatMap(a -> a.getBooks()
                        .stream()
                        .map(b -> new Bookshelf(a.getName(), b.getTitle()))
                )
                .toList();
        log.info("classic: {}", classic);

        List<Bookshelf> modernMM = Author.getSampleAuthors()
                .stream()
                .<Bookshelf>mapMulti((a, consumer) -> {
                    for (Book book : a.getBooks()) {
                        consumer.accept(new Bookshelf(a.getName(), book.getTitle()));
                    }
                }).toList();
        log.info("modernMM: {}", modernMM);


        // Case 1: mapMulti() is useful when replacing each stream element with a small (possibly zero) number of elements
        List<Bookshelf> bookshelfGT2005Classic = Author.getSampleAuthors()
                .stream()
                .flatMap(a -> a.getBooks()
                        .stream()
                        .filter(b -> b.getPublished().getYear() > 2005)
                        .map(b -> new Bookshelf(a.getName(), b.getTitle())))
                .toList();
        log.info("bookshelfGT2005Classic: {}", bookshelfGT2005Classic);

        List<Bookshelf> bookshelfModern = Author.getSampleAuthors()
                .stream()
                .<Bookshelf>mapMulti((a, consumer) -> {
                    for (Book book : a.getBooks()) {
                        if (book.getPublished().getYear() > 2005) {
                            consumer.accept(new Bookshelf(a.getName(), book.getTitle()));
                        }
                    }
                }).toList();
        log.info("bookshelfModern: {}", bookshelfModern);

        // Case 2: mapMulti() is useful when it is easier to use an imperative approach for generating result elements
        // than it is to return them in the form of a Stream
        List<Bookshelf> bookshelfMM2 = Author.getSampleAuthors()
                .stream()
                .mapMulti(Author::bookshelfGt2005)
                .toList();
        log.info("bookshelfMM2: {}", bookshelfMM2);
    }
}
