package ru.artemev.dto.json;

import lombok.Data;

import java.util.List;

@Data
public class ExportChat {

    private String name;

    private List<Message> messages;

}