package com.apimanagement.demo.bean;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class User {

    @Id
    private Integer id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private int sex;

    private Date birth;

    private int roleId;

}
