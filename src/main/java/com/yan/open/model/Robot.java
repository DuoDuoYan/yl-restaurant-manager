package com.yan.open.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Robot implements Serializable{

    private static final long serialVersionUID = 2236156302411349208L;

    private Integer id;

    private String num;

    private Integer status;

    private String statusName;
}
