package com.apimanagement.demo.form;

import lombok.Data;

@Data
public class AddUserForm {

    private String username;

    private String email;

    private String phone;

    private String password;

    private int roleId;

}
