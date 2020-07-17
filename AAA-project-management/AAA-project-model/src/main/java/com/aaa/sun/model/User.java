package com.aaa.sun.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    private Long id;

    private String username;

    private String password;

    private Long deptId;

    private String email;

    private String mobile;

    private String status;

    private String createTime;

    private String modifyTime;

    private String lastLoginTime;

    private String ssex;

    private String description;

    private String avatar;

    private String type;

    private String token;

    private String deptName;

    }