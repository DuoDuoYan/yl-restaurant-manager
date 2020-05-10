package com.yan.open.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import com.yan.open.util.MenuDoubleSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
public class Discount implements Serializable{
    private static final long serialVersionUID = -501694136395928512L;

    private Integer id;
    private String food;
    @SerializedName("food")
    private Integer foodId;
    @SerializedName("original_price")
    private Double originalPrcice;
    @JsonSerialize(using = MenuDoubleSerialize.class)
    @SerializedName("now_price")
    private Double nowPrcice;
    private String image;
    private String info;
    private Integer quantity;
    private Integer selled;
    @SerializedName("catalog_name")
    private String catalog;
}
