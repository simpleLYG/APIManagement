package com.apimanagement.demo.dao;

import com.apimanagement.demo.bean.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDao extends JpaRepository<Authority, Integer> {

    

}
