package com.aaa.sun.controller;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.base.CommonController;
import com.aaa.sun.base.ResultData;
import com.aaa.sun.model.User;
import com.aaa.sun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/8 19:09
 * @Description
 */
@RestController
public  class UserController extends CommonController<User> {
    @Autowired
    private UserService userService;
    @Override
    public BaseService<User> getBaseService() {
        return userService;
    }


}
