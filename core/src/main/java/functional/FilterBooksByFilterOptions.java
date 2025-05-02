package functional;

import functional.model.Book;
import util.DataLoader;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class FilterBooksByFilterOptions {

    static class FilterOptions {
        String genre;
        Float rating;
        String letter;

        public FilterOptions withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public FilterOptions withRating(float rating) {
            this.rating = rating;
            return this;
        }

        public FilterOptions withLetter(String letter) {
            this.letter = letter;
            return this;
        }
    }

    public static void main(String[] args) throws IOException {
        FilterBooksByFilterOptions demo = new FilterBooksByFilterOptions();

        List<Book> books = DataLoader.loadList("books.json", Book.class);
        List<Book> filteredBooks = demo.curriedFilter()
                .apply(books)
                .apply(new FilterOptions()
                        .withLetter("m")
                        .withRating(4.3f)
                );

        filteredBooks.forEach(System.out::println);
    }

    private Function<List<Book>, Function<FilterOptions, List<Book>>> curriedFilter() {
        return books -> options ->
                books.stream()
                        .filter(book -> options.genre == null || book.getGenre().strip().toLowerCase().contains(options.genre.strip().toLowerCase()))
                        .filter(book -> options.rating == null || book.getRating() >= options.rating)
                        .filter(book -> options.letter == null || book.getTitle().strip().toLowerCase().charAt(0) < options.letter.strip().toLowerCase().charAt(0))
                        .toList();
    }
}
