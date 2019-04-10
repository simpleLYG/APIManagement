package com.apimanagement.demo.controller;

import com.apimanagement.demo.bean.User;
import com.apimanagement.demo.dao.UserDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import static org.junit.Assert.*;


public class UserLoginControllerTest {

    @Resource
    UserDao userDao;

    @Test
    public void usernameTest(){
    }

}