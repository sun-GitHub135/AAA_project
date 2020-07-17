package com.aaa.sun.controller;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.base.CommonController;
import com.aaa.sun.base.ResultData;
import com.aaa.sun.model.Dept;
import com.aaa.sun.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author LC
 */
@RestController
public class DeptController extends CommonController<Dept> {

    @Autowired
    private DeptService deptService;

    @Override
    public BaseService<Dept> getBaseService() {
        return deptService;
    }

    /**
     * 查询所有一级部门以及子部门
     */
    @GetMapping("/deptList")
    public ResultData<Dept> deptList(){
        List<Dept> deptList = deptService.deptList();
        if (null != deptList && deptList.size() > 0){
            return super.operationSuccess(deptList);
        } else {
            return super.operationFailed("查询失败");
        }
    }

    /**
     * 添加部门信息
     * @param map
     * @return
     */
    @PostMapping("/addDept")
    public ResultData addDept(@RequestParam Map map){
        return super.add(map);
    }

    /**
     * 删除部门信息
     * @param map
     * @return
     */
    @PostMapping("/deleteDept")
    public ResultData deleteDept(@RequestParam Map map){
        return super.delete(map);
    }

    /**
     * 批量删除部门信息
     * @param ids
     * @return
     */
    @PostMapping("/delDeptAll")
    public ResultData delDeptAll(@RequestBody List<Long> ids){
        Integer integer = deptService.delDeptAlls(ids);
        if (integer>0){
            return super.operationSuccess();
        }else {
            return  super.operationFailed();
        }
    }


    /**
     * 修改部门信息
     * @param map
     * @return
     */
    @PostMapping("/updateDept")
    public ResultData updateDept(@RequestParam Map map){
        return super.update(map);
    }


}
