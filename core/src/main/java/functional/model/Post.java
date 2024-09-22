package functional.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public class Post {
    private final int id;
    private final String title;
    private final String tag;

    public static List<String> getAllTags(Post post) {
        return Arrays.asList(post.getTag().split("#"));
    }

    public static List<Post> getSamplePosts() {
        return List.of(
                new Post(1, "Running jOOQ", "#database #sql #rdbms"),
                new Post(2, "I/O files in Java", "#io #storage #rdbms"),
                new Post(3, "Hibernate Course", "#jpa #database #rdbms"),
                new Post(4, "Hooking Java Sockets", "#io #network"),
                new Post(5, "Analysing JDBC transactions", "#jdbc #rdbms")
        );
    }
}
