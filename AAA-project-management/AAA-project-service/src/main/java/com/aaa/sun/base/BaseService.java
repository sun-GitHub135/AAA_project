package com.aaa.sun.base;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/8 18:57
 * @Description  通用service
 */
public abstract class BaseService<T> {
    @Autowired
    Mapper<T> mapper;
    protected Mapper getMapper(){
        return mapper;
    }
    public ResultData insertDta(T t){
        int insert = mapper.insert(t);
        if (insert>0){
            return new ResultData().setCode("300").setMsg("查询数据成功");
        }
        return null;
    }
}
