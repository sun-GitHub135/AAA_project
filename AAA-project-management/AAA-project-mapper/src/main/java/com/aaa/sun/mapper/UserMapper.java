package com.aaa.sun.mapper;

import com.aaa.sun.model.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;


public interface UserMapper extends Mapper<User> {

    List<User> selectAlls(HashMap hashMap);

}