package my.learnings.controller;

import my.learnings.model.TickerResponse;
import my.learnings.service.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @Autowired
    private Ticker ticker;

    @GetMapping("/produce")
    @ResponseBody
    public TickerResponse info(@RequestParam("message") String message) {
        ticker.send(message);
        return TickerResponse.builder().code("200").status("OK").build();
    }
}
