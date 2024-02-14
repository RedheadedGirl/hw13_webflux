package ru.sbrf.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.result.view.Rendering;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import reactor.core.Disposable;
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

    private Disposable disposable;

    private boolean carryOn = true;

    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value="/do")
    public Mono<Rendering> start(final Model model) {
        IReactiveDataDriverContextVariable variable =
                new ReactiveDataDriverContextVariable(findAll(), 1);
        model.addAttribute("messages", variable);
        return Mono.just(Rendering.view("index").modelAttribute("messages", variable).build());
    }

    @RequestMapping(value="/stop")
    @ResponseStatus(value = HttpStatus.OK)
    public void stop(final Model model) {
        disposable.dispose();
        System.out.println("stopped");
        carryOn = false;
    }

    private static final List<Message> tasks = new ArrayList<>();

    static {
        for (int i = 1; i <= 50; i++) {
            Message message = new Message("data " + i);
            tasks.add(message);
        }
    }

    public Flux<Message> findAll() {
        Flux<Message> messageFlux = Flux.fromIterable(tasks)
                .takeUntil(s -> !checkValue())
                .delayElements(Duration.ofSeconds(2));

        disposable = messageFlux.subscribe(i -> {
                    System.out.println("Received: " + i);
                }, e -> System.err.println("Error: " + e.getMessage())
        );
        return messageFlux;
    }

    private boolean checkValue() {
        System.out.println("check called");
        return this.carryOn;
    }

}
