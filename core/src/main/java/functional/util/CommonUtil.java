package functional.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import functional.model.Person;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

@Slf4j
public class CommonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Person fetchPersonName(int id) {
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(URI.create(STR."https://reqres.in/api/users/\{id}"))
                    .build();

            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (!Objects.isNull(response) && response.statusCode() == 200) {
                return mapper.readValue(response.body(), Person.class);
            }

            throw new IllegalArgumentException(STR."No user exists with this id\{id}");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
