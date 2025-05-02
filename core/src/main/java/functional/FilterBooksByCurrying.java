package functional;

import functional.model.Book;
import util.DataLoader;

import java.util.List;
import java.util.function.Function;

public class FilterBooksByCurrying {

    public static void main(String[] args) throws Exception {
        List<Book> books = DataLoader.loadList("books.json", Book.class);

        FilterBooksByCurrying demo = new FilterBooksByCurrying();

        List<Book> filteredBooks = demo.curriedFilter()
                .apply(books)              // Function<String, Function<Float, Function<String, List<Book>>>>
                .apply("fantasy")       // Function<Float, Function<String, List<Book>>>
                .apply(4.0f)            // Function<String, List<Book>>
                .apply("m");

        filteredBooks.forEach(System.out::println);


        // Partially applied curried function can pause its execution
        Function<Float, Function<String, List<Book>>> filterFantasyBooks
                = demo.curriedFilter().apply(books).apply("fantasy");

        // Later on apply more filters when needed
        List<Book> highRatedBooks = filterFantasyBooks.apply(4.5f).apply("z");
        System.out.println(STR."highRatedBooks = \{highRatedBooks}");

        // Average fantasy books
        List<Book> averageFantasyBooks = filterFantasyBooks.apply(3.5f).apply("z");
        System.out.println(STR."averageFantasyBooks = \{averageFantasyBooks}");
    }

    public Function<List<Book>, Function<String, Function<Float, Function<String, List<Book>>>>> curriedFilter() {
        return books -> genre -> rating -> letter ->
                books.stream()
                        .filter(book -> book.getGenre().strip().toLowerCase().contains(genre.strip().toLowerCase()))
                        .filter(book -> book.getRating() >= rating)
                        .filter(book -> book.getTitle().strip().toLowerCase().charAt(0) < letter.strip().toLowerCase().charAt(0))
                        .toList();
    }
}
