package com.apimanagement.demo.enums;

import lombok.Getter;

@Getter
public enum  UserRoleEnums {

    ROLE_ADMIN(1, "管理员"),
    ROLE_USER(0, "普通用户");

    private Integer code;

    private String msg;

    UserRoleEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
