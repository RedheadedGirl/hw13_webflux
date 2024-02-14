package ru.sbrf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.sbrf.domain.Message;
import ru.sbrf.exception.JsonException;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
public class StreamingService {

    private static final List<Message> tasks = new ArrayList<>();

    private ObjectMapper mapper = new ObjectMapper();

    private boolean carryOn = true;


    static {
        for (int i = 1; i <= 50; i++) {
            Message message = new Message(i, "data ");
            tasks.add(message);
        }
    }

    public Flux<String> findAll() {
        return Flux.fromIterable(tasks)
                .map(task -> {
                    task.setData(task.getData() + Instant.now().toString());
                    return createValidJson(task);
                })
                .takeUntil(s -> !checkValue())
                .delayElements(Duration.ofSeconds(2));
    }

    private boolean checkValue() {
        System.out.println("check called");
        return this.carryOn;
    }

    private String createValidJson(Message message) {
        String json = "";
        try {
            json = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new JsonException("Failed to create json", e);
        }
        try { // проверим на валидность перед тем как отдать. Если не валидно, кинем исключение
            Message messageBack = mapper.readValue(json, Message.class);
        } catch (JsonProcessingException e) {
            throw new JsonException("Failed to restore object from json", e);
        }
        return json;
    }
}
