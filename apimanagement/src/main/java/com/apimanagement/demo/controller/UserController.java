package com.apimanagement.demo.controller;

import com.apimanagement.demo.bean.User;
import com.apimanagement.demo.dao.UserDao;
import com.apimanagement.demo.enums.UserRoleEnums;
import com.apimanagement.demo.form.LoginForm;
import com.apimanagement.demo.vo.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/loginRequest")
    public ModelAndView loginRequest(LoginForm loginForm,
                                     Map<String, Object> map){

        User user = userDao.findByUsername(loginForm.getUsername());


        if(user == null){
            map.put("loginResult", new LoginResult(100, "用户名不存在"));
            return new ModelAndView("login", map);
        }

        if(!user.getPassword().equals(loginForm.getPassword())){
            map.put("loginResult", new LoginResult(110, "用户名或密码错误"));
            return new ModelAndView("login", map);
        }

        if(user.getRoleId() == UserRoleEnums.ROLE_USER.getCode()){

        }

        return new ModelAndView("login", map);

    }

}
