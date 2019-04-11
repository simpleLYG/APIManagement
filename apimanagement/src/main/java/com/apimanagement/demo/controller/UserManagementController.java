package com.apimanagement.demo.controller;

import com.apimanagement.demo.bean.User;
import com.apimanagement.demo.dao.UserDao;
import com.apimanagement.demo.form.AddUserForm;
import com.apimanagement.demo.utils.Md5Util;
import com.apimanagement.demo.vo.RegisterResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/userManagement")
public class UserManagementController {

    @Autowired
    UserDao userDao;

    @GetMapping("")
    public ModelAndView userManagement(@RequestParam("page") Integer page,
                                 @RequestParam("size") Integer size,
                                 Map<String, Object> map){
        Pageable pageable = PageRequest.of(page-1, size, Sort.Direction.ASC, "id");
        Page<User> userPage = userDao.findAll(pageable);
        List<User> userList = userPage.getContent();
        System.out.println(userList);
        map.put("userList", userList);
        return new ModelAndView("userManagement", map);
    }

    @PostMapping("/addUser")
    public ModelAndView addUser(AddUserForm addUserForm,
                                Map<String, Object> map){

        User user = userDao.findByEmail(addUserForm.getEmail());
        if(user != null){
            map.put("registerResult", new RegisterResult(100, "邮箱已被注册"));
            System.out.println("100");
            return new ModelAndView("userManagement", map);
        }

        user = userDao.findByPhone(addUserForm.getPhone());
        if(user != null){
            map.put("registerResult", new RegisterResult(110, "手机号已被注册"));
            System.out.println("110");
            return new ModelAndView("userManagement", map);
        }

        user = new User();

        user.setUsername(addUserForm.getPhone());
        user.setPassword(Md5Util.encrypt(addUserForm.getPassword()));
        user.setPhone(addUserForm.getPhone());
        user.setEmail(addUserForm.getEmail());
        user.setRoleId(addUserForm.getRoleId());
        userDao.save(user);

        return new ModelAndView("redirect:/userManagement");
    }

    @DeleteMapping("/deleteUser/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Integer id){
        userDao.deleteById(id);
        return new ModelAndView("redirect:/userManagement");
    }

    @PutMapping("/putUser/{id}")
    public ModelAndView putUser(@PathVariable("id") Integer id,
                                AddUserForm addUserForm){
        Optional<User> optionalUser = userDao.findById(id);
        User user = optionalUser.get();

        user.setUsername(addUserForm.getUsername());
        user.setPassword(Md5Util.encrypt(addUserForm.getPassword()));
        user.setPhone(addUserForm.getPhone());
        user.setEmail(addUserForm.getEmail());
        user.setRoleId(addUserForm.getRoleId());

        userDao.save(user);
        return new ModelAndView("redirect:/userManagement");
    }

}
