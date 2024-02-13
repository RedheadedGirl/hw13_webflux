package ru.sbrf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
    private Long id;
    private String data;

    public Message(String data) {
        this.data = data;
    }
}
