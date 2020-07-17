package com.aaa.sun.controller;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.base.CommonController;
import com.aaa.sun.base.ResultData;
import com.aaa.sun.model.User;
import com.aaa.sun.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController extends CommonController<User> {

    @Autowired
    private UserService userService;


    @Override
    public BaseService<User> getBaseService() {
        return userService;
    }

    /**
     * 用户查询
     * @param map
     * @return
     */
    @PostMapping("/list")
    public ResultData<User> list(@RequestParam HashMap map){
        PageInfo pageInfo = userService.selectAlls(map);
        if (null != pageInfo || ("").equals(pageInfo)){
            return super.operationSuccess(pageInfo);
        }else{
            return  super.operationFailed("查询失败");
        }
    }


    /**
     * 用户添加
     * @param map
     * @return
     */
    @PostMapping("/addUser")
    public ResultData addUser(@RequestParam Map map){
        return super.add(map);
    }

    /**
     * 删除单个用户操作
     * @param map
     * @return
     */
    @PostMapping("/delUser")
    public ResultData  delUser(@RequestParam Map map){
        return  super.delete(map);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/delUserAll")
    public ResultData delUserAll(@RequestBody  List<Long> ids){
        Integer integer = userService.delUserAll(ids);
        if (integer > 0){
            return super.deleteOperation();
        }else {
            return super.operationFailed("删除失败");
        }
    }
    /**
     * 修改一个用户信息
     * @param map
     * @return
     */
    @PostMapping("/updateUser")
    public  ResultData updateUser(@RequestParam Map map){
        return  super.update(map);
    }





}
