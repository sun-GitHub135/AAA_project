package com.aaa.sun.controller;

import com.aaa.sun.annotation.LoginAnnotation;
import com.aaa.sun.base.BaseController;
import com.aaa.sun.base.ResultData;

import com.aaa.sun.model.User;
import com.aaa.sun.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/15 15:02
 * @Description
 */
@RestController

public class LoginController extends BaseController {
    @Autowired
    private IProjectService iProjectService;

    /**
     * @Author sun
     * @Description
     *      执行登陆操作
     * @Date 15:28 2020/7/15
     * @Param [user]
     * @return com.aaa.sun.base.ResultData
     */
    @PostMapping("/doLogin")
    @LoginAnnotation(opeationName = "登陆操作", opeationType = "管理员操作")
    public ResultData dologin(User user){
        return iProjectService.doLogin(user);
    }
}
