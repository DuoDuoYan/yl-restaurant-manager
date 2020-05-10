package com.yan.open.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import com.sun.org.apache.xml.internal.resolver.Catalog;
import com.yan.open.util.MenuDoubleSerialize;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.Serializable;

@Data
public class Menu implements Serializable{

    private static final long serialVersionUID = -4532831989049647819L;

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
    private Integer catalog;
    private Integer discount;
}
