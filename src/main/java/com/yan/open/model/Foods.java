package com.yan.open.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import com.yan.open.util.MenuDoubleSerialize;
import lombok.Data;
import java.io.FileInputStream;
import java.io.Serializable;

@Data
public class Foods implements Serializable {

    private static final long serialVersionUID = -31159515098386401L;

    private Integer id;
    private String food;
    private String info;
    @JsonSerialize(using = MenuDoubleSerialize.class)
    private Double price;
    @SerializedName("now_price")
    private Double nowPrice;
    private Integer quantity;
    private String image;
    private Integer selled;
    @SerializedName("catalog_name")
    private String catalogName;
    @SerializedName("catalog")
    private int catalog;

}
