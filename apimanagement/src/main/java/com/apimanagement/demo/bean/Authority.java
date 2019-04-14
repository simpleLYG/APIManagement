package com.apimanagement.demo.bean;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Authority {

    private Integer authorityId;

    private Integer type;

    private String url;

    private String remark;

}
