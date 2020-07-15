package com.aaa.sun.controller;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.base.CommonController;
import com.aaa.sun.model.LoginLog;
import com.aaa.sun.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/15 20:15
 * @Description
 */
public class LoginLogController  extends CommonController<LoginLog> {

    @Autowired
    private LoginLogService loginLogService;

    public BaseService<LoginLog> getBaseService() {
        return loginLogService;
    }

    /**
     * @Author sun
     * @Description
     *      新增日志
     * @Date 20:14 2020/7/15
     * @Param [loginLog]
     * @return java.lang.Integer
     */
    @PostMapping("/addLoginLog")
    public Integer addLoginLog(@RequestBody LoginLog loginLog){
        return getBaseService().add(loginLog);
    }
}
