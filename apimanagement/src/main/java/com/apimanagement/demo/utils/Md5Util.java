package com.apimanagement.demo.utils;

import org.springframework.util.DigestUtils;

public class Md5Util {

    public static String encrypt(String password){

        return DigestUtils.md5DigestAsHex(password.getBytes());

    }

}
