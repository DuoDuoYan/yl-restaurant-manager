package com.yan.open.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.io.Serializable;

@Data
public class OrderDetail implements Serializable{

    private static final long serialVersionUID = -2325750951327510050L;

    private Integer id;

    @SerializedName("order_num")
    private Integer orderNum;

    @SerializedName("table_num")
    private Integer tableNum;

    private String tables;

    private String position;

    private String food;

    private String foodName;

    private Double price;

    private Integer quantity;

}
