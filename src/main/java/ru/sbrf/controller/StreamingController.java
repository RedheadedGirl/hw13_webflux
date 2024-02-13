package ru.sbrf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.sbrf.domain.Message;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/stream")
public class StreamingController  {

    @GetMapping
    public Mono<Rendering> showIndex(final Model model) {
        IReactiveDataDriverContextVariable variable =
                new ReactiveDataDriverContextVariable(findAll(), 1);
        model.addAttribute("messages", variable);
        return Mono.just(Rendering.view("index").modelAttribute("messages", variable).build());
    }

    private Flux<Message> source() {
        return Flux.interval(Duration.ofSeconds(2))
                .take(5)
                .map(i -> new Message(Instant.now().toString()));
    }

    private static final List<Message> tasks = new ArrayList<>();

    static {
        for (int i = 1; i <= 50; i++) {
            Message message = new Message("data " + i);
            tasks.add(message);
        }
    }

    public Flux<Message> findAll() {
        return Flux.fromIterable(tasks).delayElements(Duration.ofSeconds(2));
    }

}
