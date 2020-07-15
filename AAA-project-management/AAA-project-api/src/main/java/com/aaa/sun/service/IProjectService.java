package com.aaa.sun.service;

import com.aaa.sun.base.ResultData;
import com.aaa.sun.model.LoginLog;
import com.aaa.sun.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/15 15:30
 * @Description
 */
@FeignClient(value = "")
public interface IProjectService {

    /**
     * @Author sun
     * @Description
     *      执行登陆操作
     * @Date 15:32 2020/7/15
     * @Param [user]
     * @return com.aaa.sun.base.ResultData
     */
    @PostMapping("/doLogin")
    ResultData doLogin(@RequestBody User user);

    /**
     * @Author sun
     * @Description
     *      新增日志
     * @Date 20:14 2020/7/15
     * @Param [loginLog]
     * @return java.lang.Integer
     */
    @PostMapping("/addLoginLog")
    Integer addLoginLog(@RequestBody LoginLog loginLog);
}
