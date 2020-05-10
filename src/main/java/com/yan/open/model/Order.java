package com.yan.open.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import com.yan.open.util.MenuDoubleSerialize;
import lombok.Data;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Order implements Serializable{
    private static final long serialVersionUID = 4218289723684052068L;

    private Integer id;

    private String ids;

    @SerializedName("table_num")
    private String tableNum;

    @SerializedName("robot_num")
    private String robotNum;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @SerializedName("order_date")
    private Long orderDate;

    private String date;

    @JsonSerialize(using = MenuDoubleSerialize.class)
    @SerializedName("total_price")
    private Double totalPrice;

    @SerializedName("order_status")
    private Integer orderStatus;

    private String status;

    private String customer;

    private String cooker;

}
