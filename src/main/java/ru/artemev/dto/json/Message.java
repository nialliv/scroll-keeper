package ru.artemev.dto.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Message {

    @SerializedName("text_entities")
    private List<TextEntity> textEntities;

}
