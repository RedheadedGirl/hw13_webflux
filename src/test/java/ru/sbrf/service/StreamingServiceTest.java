package ru.sbrf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sbrf.domain.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StreamingServiceTest {

    @Spy
    private ObjectMapper objectMapper;

    private StreamingService streamingService;

    @BeforeEach
    void setUp() {
        streamingService = new StreamingService(objectMapper);
    }

    @Test
    void givenObject_whenCreateJson_thenCorrect() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any(Message.class))).thenCallRealMethod();
        Message message = new Message(22, "DATA");
        String validJson = streamingService.createValidJson(message);
        assertEquals("{\"id\":22,\"data\":\"DATA\"}", validJson);
    }
}
