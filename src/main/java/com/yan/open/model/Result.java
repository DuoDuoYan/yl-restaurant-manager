package com.yan.open.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = 7332673958461887439L;

    private Integer code;

    private String message;
}
