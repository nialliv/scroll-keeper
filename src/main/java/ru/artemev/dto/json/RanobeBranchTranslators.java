package ru.artemev.dto.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class RanobeBranchTranslators {

    @SerializedName("branch_id")
    private Integer branchId;

}
