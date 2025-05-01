package functional.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Book {
    private String title;
    private String genre;
    private float rating;
    private LocalDate published;

    public Book(String title, LocalDate localDate) {
        this.title = title;
        this.published = localDate;
    }
}
