package com.apimanagement.demo.dao;

import com.apimanagement.demo.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {

    Role findByRoleName(String roleName);

    void deleteByRoleId(Integer roleId);



}
