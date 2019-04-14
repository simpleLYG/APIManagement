package com.apimanagement.demo.controller;

import com.apimanagement.demo.bean.Role;
import com.apimanagement.demo.dao.RoleDao;
import com.apimanagement.demo.form.AddRoleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/roleManagement")
public class RoleManagementController {

    @Autowired
    RoleDao roleDao;

    @GetMapping("")
    public ModelAndView roleManagement(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "5") Integer size,
                                       Map<String, Object> map){

        Pageable pageable = PageRequest.of(page-1, size, Sort.Direction.ASC, "roleId");
        Page<Role> rolePage = roleDao.findAll(pageable);
        List<Role> roleList = rolePage.getContent();
        System.out.println(roleList);
        map.put("roleList", roleList);

        return new ModelAndView("roleManagement", map);

    }

    @Transactional
    @PostMapping("addRole")
    public ModelAndView addRole(AddRoleForm roleForm,
                                Map<String, Object> map){

        Role role = roleDao.findByRoleName(roleForm.getRoleName());
        if(role != null){
            map.put("rusult", "角色已存在");
            System.out.println("角色已存在");
            return new ModelAndView("roleManagement", map);
        }

        role = new Role();
        role.setRoleName(roleForm.getRoleName());
        role.setAuthorityId(roleForm.getAuthorityId());
        role.setRemark(roleForm.getRemark());

        roleDao.save(role);
        map.put("rusult", "角色添加成功");
        System.out.println("角色添加成功");
        return new ModelAndView("roleManagement", map);
    }

    @PutMapping("/putRole")
    public ModelAndView putRole(Role role){

        roleDao.save(role);
        return new ModelAndView("redirect:/roleManagement");

    }

    @Transactional
    @DeleteMapping("/deleteRole")
    public ModelAndView deleteRole(@RequestParam("roleId") Integer roleId){

        roleDao.deleteByRoleId(roleId);
        return new ModelAndView("redirect:/roleManagement");

    }


}
