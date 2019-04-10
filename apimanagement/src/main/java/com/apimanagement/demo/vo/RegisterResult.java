package com.apimanagement.demo.vo;

import lombok.Getter;

@Getter
public class RegisterResult {

    private Integer code;

    private String msg;

    public RegisterResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
