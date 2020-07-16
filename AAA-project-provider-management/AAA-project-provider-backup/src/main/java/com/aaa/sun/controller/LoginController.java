package com.aaa.sun.controller;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.base.CommonController;
import com.aaa.sun.base.ResultData;
import com.aaa.sun.model.User;
import com.aaa.sun.service.LoginService;
import com.aaa.sun.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.aaa.sun.status.LoginStatus.*;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/15 15:53
 * @Description
 */
@RestController
public class LoginController extends CommonController<User> {

    @Autowired
    private LoginService loginService;

    public BaseService<User> getBaseService() {
        return loginService;
    }
    /**
     * @Author sun
     * @Description
     *      执行登陆操作
     * @Date 15:53 2020/7/15
     * @Param [user]
     * @return com.aaa.sun.base.ResultData
     */
    @PostMapping("/doLogin")
    public ResultData doLogin(@RequestBody User user){
        TokenVo tokenVo = loginService.doLogin(user);
        if (tokenVo.getIfSuccess()){
            return super.loginSuccess(tokenVo.getToken());
        }else if (tokenVo.getType() == 1){
            return super.loginFailed(USER_NOT_EXIST.getMsg());
        }else if (tokenVo.getType() == 2){
            return super.loginFailed(PASSWORD_WRONG.getMsg());
        }else {
            return super.loginFailed(SYSTEM_EXCEPTION.getMsg());
        }
    }


}
