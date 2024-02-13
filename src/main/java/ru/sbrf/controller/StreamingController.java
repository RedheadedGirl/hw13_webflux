package ru.sbrf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.sbrf.domain.Message;

import java.time.Duration;
import java.time.Instant;

@Controller
@RequestMapping("/stream")
public class StreamingController  {

    @GetMapping
    public String showIndex(Model model) {
        Flux<Message> source = source();
        model.addAttribute("messages", source);
        return "index.html";
    }

    private Flux<Message> source() {
        return Flux.interval(Duration.ofSeconds(2))
                .take(5)
                .map(i -> new Message(Instant.now().toString()));
    }

}
