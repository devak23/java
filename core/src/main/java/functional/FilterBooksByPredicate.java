package functional;

import functional.model.Book;
import util.DataLoader;

import java.util.List;
import java.util.function.Predicate;

public class FilterBooksByPredicate {

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
        List<Book> books = DataLoader.loadList("books.json", Book.class);
        new FilterBooksByPredicate().filterBooks(books, "fantasy", 4.0f, "m").forEach(System.out::println);

    }
}
