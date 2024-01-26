package my.learnings.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TickerResponse {
    String status;
    String code;
    String message;
}
