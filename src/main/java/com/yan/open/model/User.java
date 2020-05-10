package com.yan.open.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -8874217318938527553L;
    
    private Integer id;
    private String phone;
    private String password;
    private String username;
    private String gender;
    private Integer age;
    private String address;
    @SerializedName("role_id")
    private Integer roleId;
    private Double balance;
    private String image;
}
