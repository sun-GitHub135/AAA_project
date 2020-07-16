package com.aaa.sun.service;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.mapper.TDeptMapper;
import com.aaa.sun.model.TDept;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author LC
 */
@Service
public class DeptService extends BaseService<TDept> {

    @Autowired
    private TDeptMapper tDeptMapper;


    /**
     * 分页条件查询字典信息
     * @param hashMap
     * @return
     */
    public PageInfo selectAlls(HashMap hashMap){
        PageHelper.startPage(Integer.parseInt(hashMap.get("pageNo")+ "" ),Integer.parseInt(hashMap.get("pageSize")+""));
        PageInfo page = new PageInfo(tDeptMapper.selectAlls(hashMap));
        if (null != page && !"".equals(page)){
            return page;
        }
        return  null;
    }

}