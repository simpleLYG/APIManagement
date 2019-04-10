package com.apimanagement.demo.controller;


import com.apimanagement.demo.bean.User;
import com.apimanagement.demo.dao.UserDao;
import com.apimanagement.demo.form.RegisterForm;
import com.apimanagement.demo.utils.CookieUtil;
import com.apimanagement.demo.utils.MessageUtil;
import com.apimanagement.demo.vo.RegisterResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class UserRegisterController {

    @Autowired
    UserDao userDao;

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/registerRequest")
    public ModelAndView registerRequest(HttpServletRequest request,
                                        RegisterForm registerForm,
                                        Map<String, Object> map){

        User user = userDao.findByEmail(registerForm.getEmail());
        if(user != null){
            map.put("registerResult", new RegisterResult(100, "邮箱已被注册"));
            System.out.println("100");
            return new ModelAndView("register", map);
        }

        user = userDao.findByPhone(registerForm.getPhone());
        if(user != null){
            map.put("registerResult", new RegisterResult(110, "手机号已被注册"));
            System.out.println("110");
            return new ModelAndView("register", map);
        }

        if(!registerForm.getPassword().equals(registerForm.getRepassword())){
            map.put("registerResult", new RegisterResult(120, "两次输入密码不一致"));
            System.out.println("120");
            return new ModelAndView("register", map);
        }

        if(CookieUtil.get(request,registerForm.getPhone()) == null){
            map.put("registerResult", new RegisterResult(130, "验证码不存在或已过期"));
            System.out.println("130");
            return new ModelAndView("register", map);
        }

        if(!registerForm.getIdentifyingCode().equals(CookieUtil.get(request,registerForm.getPhone()).getValue())){
            map.put("registerResult", new RegisterResult(140, "验证码错误"));
            System.out.println("140");
            return new ModelAndView("register", map);
        }

        user = new User();

        user.setPassword(registerForm.getPassword());
        user.setPhone(registerForm.getPhone());
        user.setEmail(registerForm.getEmail());
        userDao.save(user);
        System.out.println("success");
        return new ModelAndView("register", map);

    }

    @RequestMapping("/sendRegisterIdentifyingCode")
    public ModelAndView sendIdentifyingCode(HttpServletResponse response,
                                            String phone,
                                            Map<String, Object> map){
        MessageUtil.sendMessage(response, phone);
        map.put("phone", phone);
        return new ModelAndView("register", map);
    }

}
