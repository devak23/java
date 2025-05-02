package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URL;
import java.util.List;

// This class is used to load data into its object or a collection of objects.
public final class DataLoader {
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());


    public static <T> List<T> loadList(String fileName, Class<T> clazz) {
        try {
            URL resource = DataLoader.class.getClassLoader().getResource(fileName);
            // See the note below on why this was commented
//            return MAPPER.readValue(resource, new TypeReference<List<T>>() {
//            });

            return MAPPER.readValue(resource
                    , MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new RuntimeException("Could not load data from: " + fileName, e);
        }
    }
}


/*
    NOTE: if you uncomment the lines 24,25 and comment lines 27/28 you will get the following exception:
Exception in thread "main" java.lang.ClassCastException: class java.util.LinkedHashMap cannot be cast to class functional.model.Book (java.util.LinkedHashMap is in module java.base of loader 'bootstrap'; functional.model.Book is in module core of loader 'app')
	at java.base/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
	at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1708)
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:575)
	at java.base/java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
	at java.base/java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
	at java.base/java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:622)
	at java.base/java.util.stream.ReferencePipeline.toList(ReferencePipeline.java:627)
	at core/functional.FilterBooksByCurrying.lambda$curriedFilter$3(FilterBooksByCurrying.java:44)
	at core/functional.FilterBooksByCurrying.main(FilterBooksByCurrying.java:20)


This is because Java’s type erasure causes the generic type `T` to get erased at runtime. Consequently, Jackson doesn’t
know the actual type of `T` during deserialization and cannot map the JSON into the specified type. This results in
deserializing JSON objects into default types (e.g., `LinkedHashMap` for objects and `ArrayList` for lists), which leads
to `ClassCastException` when you try to use them as specific domain objects (e.g., `List<Book>` or `List<Pet>`).

 */