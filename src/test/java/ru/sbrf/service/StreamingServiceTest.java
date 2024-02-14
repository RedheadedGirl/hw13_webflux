package ru.sbrf.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbrf.domain.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamingServiceTest {

    private StreamingService streamingService;

    @BeforeEach
    void setUp() {
        streamingService = new StreamingService();
    }

    @Test
    void givenObject_whenCreateJson_thenCorrect() {
        Message message = new Message(22, "DATA");
        String validJson = streamingService.createValidJson(message);
        assertEquals("{\"id\":22,\"data\":\"DATA\"}", validJson);
    }
}
