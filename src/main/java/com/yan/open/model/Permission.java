package com.yan.open.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = -2618898253692944955L;
    private Integer id;
    private String url;
    @SerializedName("role_id")
    private Integer roleId;
}
