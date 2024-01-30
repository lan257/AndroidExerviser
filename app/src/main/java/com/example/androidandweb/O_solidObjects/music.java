package com.example.androidandweb.O_solidObjects;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class music {
    UUID uid= UUID.randomUUID();
    String name;
    String src;
    LocalDate create=LocalDate.now();
    String author;

    public music(String 天苍苍, String s, String 西蚕) {
        name=天苍苍;
        src=s;
        author=西蚕;
    }
}
