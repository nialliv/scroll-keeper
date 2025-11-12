package ru.artemev.dto.json;

import lombok.Data;

import java.util.List;

@Data
public class RanobeLibData {

    private Integer index;

    private Integer number;

    private Integer volume;

    private List<RanobeBranchTranslators> branches;
}
