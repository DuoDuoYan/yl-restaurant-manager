package com.yan.open.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class Sales implements Serializable{

    private static final long serialVersionUID = 6077275345505861617L;

    private String day;

    private String date;

    @SerializedName("catalog_name")
    private String catalogName;

    private Double price;

    private Integer quantity;

}
