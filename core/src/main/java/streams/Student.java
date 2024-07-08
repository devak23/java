package streams;

import lombok.Builder;

@Builder
public record Student(int id, String name, int marks) {
}
