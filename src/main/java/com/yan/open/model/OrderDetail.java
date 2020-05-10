package com.yan.open.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.io.Serializable;

@Data
public class OrderDetail implements Serializable{

    private static final long serialVersionUID = -2325750951327510050L;

    private Integer id;

    private String food;

    private Double price;

    private Integer quantity;

}
