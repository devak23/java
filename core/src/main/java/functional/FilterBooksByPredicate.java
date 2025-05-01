package functional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import functional.model.Book;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.function.Predicate;

public class FilterBooksByPredicate {
    public static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    // Filter book by Genre
    Predicate<Book> withGenre(String genre) {
        return book -> book.getGenre().strip().toLowerCase().contains(genre.strip().toLowerCase());
    }

    // Filter book by rating
    Predicate<Book> withRating(float rating) {
        return book -> book.getRating() >= rating;
    }

    Predicate<Book> withLetterLower(String letter) {
        return book -> book.getTitle().strip().toLowerCase().charAt(0) < letter.strip().toLowerCase().charAt(0);
    }

    public List<Book> filterBooks(List<Book> books, String genre, float rating, String letter) {
        return books.stream()
                .filter(withGenre(genre)
                        .and(withRating(rating))
                        .and(withLetterLower(letter)))
                .toList();
    }


    public static void main(String[] args) throws Exception {
        List<Book> books = getBooks();
        new FilterBooksByPredicate().filterBooks(books, "fantasy", 4.0f, "m").forEach(System.out::println);

    }

    public static List<Book> getBooks() throws IOException {
        URL resource = FilterBooksByPredicate.class.getClassLoader().getResource("books.json");
        return MAPPER.readValue(resource, new TypeReference<>() {
        });
    }
}
