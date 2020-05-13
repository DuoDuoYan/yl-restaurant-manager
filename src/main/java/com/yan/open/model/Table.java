package com.yan.open.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Table implements Serializable{

    private static final long serialVersionUID = -5967140873266698579L;

    private Integer id;
    private String num;
    private Integer status;
    private String statusName;
    private String position;
}

