package com.apimanagement.demo.form;

import lombok.Data;

@Data
public class RegisterForm {

    private String email;

    private String password;

    private String repassword;

    private String phone;

    private String identifyingCode;

}
