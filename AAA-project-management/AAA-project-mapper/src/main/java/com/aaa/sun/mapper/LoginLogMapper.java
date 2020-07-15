package com.aaa.sun.mapper;

import com.aaa.sun.model.LoginLog;

import java.util.List;

public interface LoginLogMapper {
    int insert(LoginLog record);

    List<LoginLog> selectAll();
}