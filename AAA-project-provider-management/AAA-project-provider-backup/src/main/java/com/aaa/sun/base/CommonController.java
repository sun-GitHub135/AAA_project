package com.aaa.sun.base;

import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/8 18:53
 * @Description
 */
public abstract class CommonController<T> extends BaseController {
    public abstract BaseService<T> getBaseService();

    public ResultData add(Map map){
        return new ResultData();
    }

}
