package com.apimanagement.demo.utils;

public class RandomNumUtil {

    public static String createRandomNum(int num){
        int randomNum = 0;
        for(int i = 0; i < num; i++){
            randomNum = randomNum * 10 + (int)(Math.random()*10);
        }
        return String.format("%04d", randomNum);
    }

}
