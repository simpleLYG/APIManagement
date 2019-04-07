package com.apimanagement.demo.dao;

import com.apimanagement.demo.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
