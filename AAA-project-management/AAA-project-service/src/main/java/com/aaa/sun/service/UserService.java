package com.aaa.sun.service;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.mapper.UserMapper;
import com.aaa.sun.model.Dept;
import com.aaa.sun.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.HashMap;
import java.util.List;

/**
 * @author LC
 */
@Service
public class UserService extends BaseService<User> {
    @Autowired
    private UserMapper userMapper;


    /**
     * 分页查询信息
     * @param hashMap
     * @return
     */
    public PageInfo selectAlls(HashMap hashMap){
        PageHelper.startPage(Integer.parseInt(hashMap.get("pageNo")+""),Integer.parseInt(hashMap.get("pageSize")+""));
        PageInfo pageInfo =new PageInfo(userMapper.selectAlls(hashMap));
        if (null != pageInfo && !"".equals(pageInfo)){
            return pageInfo;
        }
        return  null;
    }


    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    public Integer delUserAll(List<Long> ids){
        Example example = Example.builder(Dept.class).where(Sqls.custom().andIn("deptId", ids)).build();
        return  userMapper.deleteByExample(example);
    }

    public List<User> selectAll(){
        List<User> users = userMapper.selectAlls(null);
        if (null != users && users.size() >0){
            return users;
        }
        return null;
    }
}
