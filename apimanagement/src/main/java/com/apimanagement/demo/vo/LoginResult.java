package com.apimanagement.demo.vo;

import lombok.Getter;

@Getter
public class LoginResult {

    private Integer code;

    private String msg;

    public LoginResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
