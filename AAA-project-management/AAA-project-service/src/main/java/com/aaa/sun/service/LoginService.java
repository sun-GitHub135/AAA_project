package com.aaa.sun.service;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.base.ResultData;
import com.aaa.sun.mapper.UserMapper;
import com.aaa.sun.model.User;
import com.aaa.sun.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.UUID;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/15 15:54
 * @Description
 */
@Service
public class LoginService extends BaseService<User> {

    @Autowired
    private UserMapper userMapper;
    /**
     * @Author sun
     * @Description
     *      执行登陆操作
     *
     *      pojo:实体类
     *      povo:封装类型（实际开发中，会有很多种情况导致多表联查的时候无法装载数据--》我就根据返回端的数据自己
     *      去封装一个对象出来，---》view object
     * @Date 15:56 2020/7/15
     * @Param [user]
     * @return com.aaa.sun.base.ResultData
     */
    public TokenVo doLogin(User user){
        TokenVo tokenVo = new TokenVo();
        User user1 = new User();
        // 1.判断User是否为null
        if (null != user){
            user1.setUsername(user.getUsername());
            User user2 = super.selectOne(user1);
            // 2.判断user2是否为null
            if (null == user2){
                tokenVo.setIfSuccess(false).setType(1);
                return tokenVo;
            } else {
                // 用户名OK，查询密码
                user1.setPassword(user.getPassword());
                User user3 = super.selectOne(user1);
                // 3.判断user3是否为null
                if (null == user3){
                    tokenVo.setIfSuccess(false).setType(2);
                    return tokenVo;
                }else {
                    // 登陆成功
                    /**
                     * !!!!mybatis是无法检测连接符的，他会把连接符进行转译（\\-）
                     * 需要把连接符替换掉
                     */
                    String token = UUID.randomUUID().toString().replaceAll("-", "");
                    user3.setToken(token);
                    Integer updateResult = super.update(user3);
                    if (updateResult >0){
                        tokenVo.setIfSuccess(true).setToken(token);
                    }else {
                        tokenVo.setIfSuccess(false).setType(4);
                    }
                }
            }
        }else {
            tokenVo.setIfSuccess(false).setType(4);
            return tokenVo;
        }
        return tokenVo;
    }
}
