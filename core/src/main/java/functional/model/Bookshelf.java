package functional.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Bookshelf {
    private final String author;
    private final String book;
}
