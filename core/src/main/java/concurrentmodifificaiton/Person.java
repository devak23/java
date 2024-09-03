package concurrentmodifificaiton;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class Person {
    private List<String> friends = new ArrayList<>();
    private ConcurrentHashMap<String, String> stocks = new ConcurrentHashMap<>();
}
