package com.apimanagement.demo.controller;

import com.apimanagement.demo.bean.User;
import com.apimanagement.demo.dao.UserDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import static org.junit.Assert.*;


public class UserControllerTest {

    @Resource
    UserDao userDao;

    @Test
    public void usernameTest(){
        User user = userDao.findByUsername("lyg");
//        if(user == null){
//            System.out.println("null");
//        }
        System.out.println(user);
    }

}