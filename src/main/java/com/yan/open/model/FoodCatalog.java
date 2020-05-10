package com.yan.open.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class FoodCatalog implements Serializable{

    private static final long serialVersionUID = 8972210461359720836L;

    private Integer id;

    @SerializedName("catalog_name")
    private String catalogName;

    private Integer status;

    private String statusName;
}
