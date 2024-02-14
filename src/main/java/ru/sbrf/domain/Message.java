package ru.sbrf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
    private int id;
    private String data;

    public Message(int id, String data) {
        this.id = id;
        this.data = data;
    }
}
