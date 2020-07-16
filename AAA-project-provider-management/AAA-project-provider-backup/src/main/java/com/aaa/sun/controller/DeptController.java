package com.aaa.sun.controller;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.base.CommonController;
import com.aaa.sun.base.ResultData;
import com.aaa.sun.model.Dict;
import com.aaa.sun.model.TDept;
import com.aaa.sun.service.DeptService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LC
 */
public class DeptController extends CommonController<TDept> {

    @Autowired
    private DeptService deptService;

    @Override
    public BaseService<TDept> getBaseService() {
        return deptService;
    }

    /**
     * 分页查询部门信息
     * @param map
     * @return
     */
    @PostMapping("/deptList")
    public ResultData<Dict> list(@RequestParam HashMap map){
        PageInfo pageInfo = deptService.selectAlls(map);
        if ( null != pageInfo || !("").equals(pageInfo)){
            return super.operationSuccess(pageInfo);
        }else {
            return  super.operationFailed("查询失败");
        }
    }

    /**
     * 通用部门新增
     * @param map
     * @return
     */
    @PostMapping("/addDept")
    public ResultData addDept(@RequestParam Map map){
        return super.add(map);
    }

    /**
     * 通用部门删除
     * @param map
     * @return
     */
    @PostMapping("/delDept")
    public ResultData delDept(@RequestParam Map map){
        return  super.delete(map);
    }

    /**
     * 通用部门修改
     * @param map
     * @return
     */
    @PostMapping("/updateDept")
    public ResultData updateDept(@RequestParam Map map){
        return  super.update(map);
    }



}
