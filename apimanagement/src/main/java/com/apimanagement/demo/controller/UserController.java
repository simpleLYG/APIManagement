package com.apimanagement.demo.controller;

import com.apimanagement.demo.bean.User;
import com.apimanagement.demo.dao.UserDao;
import com.apimanagement.demo.enums.UserRoleEnums;
import com.apimanagement.demo.form.EmailLoginForm;
import com.apimanagement.demo.form.PhoneLoginForm;
import com.apimanagement.demo.utils.CookieUtil;
import com.apimanagement.demo.utils.MessageUtil;
import com.apimanagement.demo.vo.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    @RequestMapping("/emailLogin")
    public String emailLoginPage(){
        return "emailLogin";
    }

    @RequestMapping("/phoneLogin")
    public String phoneLoginPage(HttpServletResponse response){
        CookieUtil.set(response, "13142122283", "123456", 5*3600);
        return "phoneLogin";
    }

    @PostMapping("/emailLoginRequest")
    public ModelAndView emailLoginRequest(EmailLoginForm emailLoginForm,
                                     Map<String, Object> map){

        User user = userDao.findByEmail(emailLoginForm.getEmail());


        if(user == null){
            map.put("loginResult", new LoginResult(100, "邮箱不存在"));
            return new ModelAndView("emailLogin", map);
        }

        if(!user.getPassword().equals(emailLoginForm.getPassword())){
            map.put("loginResult", new LoginResult(110, "邮箱或密码错误"));
            return new ModelAndView("emailLogin", map);
        }

        if(user.getRoleId() == UserRoleEnums.ROLE_USER.getCode()){
            map.put("loginResult", new LoginResult(200, "普通用户登陆成功"));
            return new ModelAndView("redirect:/userIndex", map);
        }

        if(user.getRoleId() == UserRoleEnums.ROLE_ADMIN.getCode()){
            map.put("loginResult", new LoginResult(210, "管理员登陆成功"));
            return new ModelAndView("redirect:/managerIndex", map);
        }

        return new ModelAndView("emailLogin", map);

    }


    @PostMapping("/phoneLoginRequest")
    public ModelAndView phoneLoginRequest(HttpServletRequest request,
                                          PhoneLoginForm phoneLoginForm,
                                          Map<String, Object> map){

        User user = userDao.findByPhone(phoneLoginForm.getPhone());

        if(user == null){
            map.put("loginResult", new LoginResult(120, "手机号不存在"));
            System.out.println("手机号不存在");
            return new ModelAndView("phoneLogin", map);
        }

        if(!phoneLoginForm.getIdentifyingCode().equals(CookieUtil.get(request, phoneLoginForm.getPhone()).getValue())){
            map.put("loginResult", new LoginResult(130, "验证码错误"));
            System.out.println(phoneLoginForm.getIdentifyingCode() + ":" + CookieUtil.get(request, phoneLoginForm.getPhone()).getValue() + " 验证码错误");
            return new ModelAndView("phoneLogin", map);
        }

        System.out.println("验证码正确");
        return new ModelAndView("emailLogin", map);
    }

    /**
     * 未完成
     * @param response
     * @param phone
     * @param map
     * @return
     */
    @RequestMapping("/sendIdentifyingCode")
    public ModelAndView sendIdentifyingCode(HttpServletResponse response,
                                            String phone,
                                            Map<String, Object> map){
        MessageUtil.sendMessage(response, phone);
        map.put("phone", phone);
        return new ModelAndView("phoneLogin", map);
    }

}
