package functional;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class FilterBooksWithNoModel {

    // List of books
    List<Map<String, Object>> books = Arrays.asList(
            new HashMap<String, Object>() {{
                put("title", "The Hobbit");
                put("genre", "Fantasy");
                put("rating", 4.7);
            }},
            new HashMap<String, Object>() {{
                put("title", "Dune");
                put("genre", "Sci-Fi");
                put("rating", 4.5);
            }},
            new HashMap<String, Object>() {{
                put("title", "To Kill a Mocking bird");
                put("genre", "Classics");
                put("rating", 4.9);
            }},
            new HashMap<String, Object>() {{
                put("title", "Eragon");
                put("genre", "Fantasy");
                put("rating", 4.1);
            }},
            new HashMap<String, Object>() {{
                put("title", "The Catcher in the rye");
                put("genre", "Classics");
                put("rating", 3.8);
            }},
            new HashMap<String, Object>() {{
                put("title", "Mistborn");
                put("genre", "Fantasy");
                put("rating", 4.7);
            }}
    );

    // Filter by genre
    public Predicate<Map<String, Object>> withGenre(String genre) {
        return book -> book.get("genre").toString().strip().toLowerCase()
                .equals(genre.strip().toLowerCase());
    }

    // Filter by rating
    public Predicate<Map<String, Object>> withRating(double minRating) {
        return book -> Double.parseDouble(book.get("rating").toString()) >= minRating;
    }

    // Filter by first letter of the title being less than input letter
    public Predicate<Map<String, Object>> withLetterLower(String letter) {
        return book -> book.get("title").toString().strip().toLowerCase().charAt(0)
                < letter.toLowerCase().charAt(0);
    }

    // Filtering books based on genre, rating, and title
    public List<Map<String, Object>> filterBooks(
            List<Map<String, Object>> books, String genre, double rating, String letter) {
        Predicate<Map<String, Object>> filterGenre = withGenre(genre);
        Predicate<Map<String, Object>> filterRating = withRating(rating);
        Predicate<Map<String, Object>> filterTitle = withLetterLower(letter);

        return books.stream()
                .filter(filterGenre.and(filterRating).and(filterTitle))
                .collect(Collectors.toList());
    }
}