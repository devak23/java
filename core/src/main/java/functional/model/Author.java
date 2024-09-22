package functional.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Author {
    private String name;
    private final List<Book> books;

    public void bookshelfGt2005(Consumer<Bookshelf> bookshelfConsumer) {
        this.books.stream()
                .filter(b -> b.getPublished().getYear() > 2005)
                .forEach(b -> bookshelfConsumer.accept(new Bookshelf(this.getName(), b.getTitle())));
    }

    public static List<Author> getSampleAuthors() {
        Book b1 = new Book("Book1", LocalDate.of(2000, 3, 12));
        Book b2 = new Book("Book2", LocalDate.of(2002, 3, 11));
        Book b3 = new Book("Book3", LocalDate.of(2004, 11, 24));
        Book b4 = new Book("Book4", LocalDate.of(2002, 6, 10));
        Book b5 = new Book("Book5", LocalDate.of(2009, 5, 7));
        Book b6 = new Book("Book6", LocalDate.of(2007, 2, 17));
        Book b7 = new Book("Book7", LocalDate.of(1995, 10, 27));
        Book b8 = new Book("Book8", LocalDate.of(2001, 10, 17));
        Book b9 = new Book("Book9", LocalDate.of(2004, 8, 10));
        Book b10 = new Book("Book10", LocalDate.of(2008, 1, 4));

        Author a1 = new Author("Joana Nimar", List.of(b1, b2, b3));
        Author a2 = new Author("Olivia Goy", List.of(b4, b5));
        Author a3 = new Author("Marcel Joel", List.of(b6));
        Author a4 = new Author("Alexender Tohn", List.of(b7, b8, b9, b10));

        return List.of(a1, a2, a3, a4);
    }
}
