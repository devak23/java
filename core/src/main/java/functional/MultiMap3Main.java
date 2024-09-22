package functional;

import functional.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MultiMap3Main {

    private static final Logger log = LoggerFactory.getLogger(MultiMap3Main.class);

    // So we have a class that shapes some blog posts. Each post has several properties, including its tags.
    // The tags of each post are actually represented as a string of tags separated by hashtag (#). Whenever
    // we need the list of tags for a given post, we can call the getAllTags() helper. For instance, here is a list
    // of posts and their tags:
    public static void main(String[] args) {

        // Our goal is to extract from this list a Map<String, List<Integer>>, containing, for each tag (key) the
        // list of posts (value). For instance, for the tag #database, we have articles 1 and 3; for tag #rdbms, we
        // have articles 1, 2, 3, and 5, and so on.

        traditionalWay();

        // Using the mapMulti way:
        mapMultiWay();
    }

    private static void mapMultiWay() {
        Map<String, List<Integer>> results = Post.getSamplePosts()
                .stream()
                .<Map.Entry<String, Integer>>mapMulti(((post, consumer) -> {
                    for (String tag : Post.getAllTags(post)) {
                        consumer.accept(Map.entry(tag, post.getId()));
                    }
                }))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        log.info("results via MM: {}", results);

        // OUTPUT:
        // 20:53:44.109 [main] INFO functional.MultiMap3Main -- results via MM: {=[1, 2, 3, 4, 5], jdbc =[5], rdbms=[1, 2, 3, 5], jpa =[3], sql =[1], io =[2, 4], database =[1, 3], storage =[2], network=[4]}
    }

    private static void traditionalWay() {
        // Accomplishing this in the traditional way can be done via flatMap() and groupingBy(). In a nutshell,
        // flatMap() is useful for flattening a nested Stream<Stream<R>> model, while groupingBy() is a collector useful
        // for grouping data in a map by some logic or property.

        // We need flatMap() because we have the List<Post> that, for each Post, nests via allTags() a List<String>
        // (so if we simply call stream(), then we get back a Stream<Stream<R>>). After flattening, we wrap each tag
        // in Map.Entry<String, Integer>. Finally, we group these entries by tags into a Map.

        Map<String, List<Integer>> results =
                Post.getSamplePosts()                   // List<Post>
                .stream()                               // Stream<Post>
                .flatMap(post -> Post.getAllTags(post)  // List<String>
                        .stream()                       // Stream<String>
                        .map(tag -> Map.entry(tag, post.getId()))) // Stream<Entry<String,Integer>>
                .collect(Collectors.groupingBy(Map.Entry::getKey
                        , Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
        log.info("results: {}", results);

        // OUTPUT:
        // 20:38:42.379 [main] INFO functional.MultiMap3Main -- results: {=[1, 2, 3, 4, 5], jdbc =[5], rdbms=[1, 2, 3, 5], jpa =[3], sql =[1], io =[2, 4], database =[1, 3], storage =[2], network=[4]}
    }
}
