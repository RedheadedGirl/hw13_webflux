package ru.sbrf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.result.view.Rendering;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;
import ru.sbrf.StreamingService;

@Controller
@RequestMapping("/stream")
@RequiredArgsConstructor
public class StreamingController  {

    private final StreamingService streamingService;

    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value="/do")
    public Mono<Rendering> start(final Model model) {
        IReactiveDataDriverContextVariable variable =
                new ReactiveDataDriverContextVariable(streamingService.findAll(), 1);
        model.addAttribute("messages", variable);
        return Mono.just(Rendering.view("index").modelAttribute("messages", variable).build());
    }

    @RequestMapping(value="/stop")
    @ResponseStatus(value = HttpStatus.OK)
    public void stop() {
        System.out.println("stopped");
        streamingService.setCarryOn(false);
    }

}
