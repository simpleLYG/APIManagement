package com.apimanagement.demo.dao;

import com.apimanagement.demo.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByPhone(String phone);


}
