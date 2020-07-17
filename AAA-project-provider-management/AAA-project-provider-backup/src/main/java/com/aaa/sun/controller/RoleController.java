package com.aaa.sun.controller;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.base.CommonController;
import com.aaa.sun.base.ResultData;
import com.aaa.sun.model.Role;
import com.aaa.sun.service.RoleService;
import com.aaa.sun.vo.RoleVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController extends CommonController<Role> {

    @Autowired
    private RoleService roleService;


    /**
     * 分页查询
     * @param roleVo
     * @param unit_id
     * @return
     * @throws Exception
     */
    @GetMapping("/selectAllRule")
    public ResultData selectAllRule(RoleVo roleVo, Long unit_id) throws Exception{
        PageInfo<Role> rolePageInfo = roleService.selectAllRole(roleVo, unit_id);
        if (rolePageInfo !=null){
            return  operationSuccess(rolePageInfo);
        }else {
            return  operationFailed("查询角色失败");
        }
    }

    /**
     * 添加角色
     * @param roleVo
     * @return
     */
    @PostMapping("/addRule")
    public ResultData addRule(@RequestBody RoleVo roleVo){
        Boolean role = roleService.addRole(roleVo);
        if (role){
            return  insertOperation();
        }else {
            return  operationFailed("添加角色失败");
        }
    }

    /**
     * 修改角色信息
     * @param roleVo
     * @return
     */
    @PostMapping("/updateRule")
    public ResultData updateRule(@RequestBody RoleVo roleVo){
        Boolean updateRole = roleService.updateRole(roleVo);
        if (updateRole){
            return updateOperation();
        }else {
            return operationFailed("修改角色失败");
        }
    }


    /**
     *
     * @param ruleId
     * @return
     */
    @PostMapping("/delRule")
    public ResultData delRule(Long ruleId){
        Boolean deleteRole = roleService.deleteRole(ruleId);
        if (deleteRole){
           return super.deleteOperation();
        }else {
          return   super.operationFailed("删除失败");
        }
    }

    @PostMapping("/delRuleAlls")
    public ResultData delRuleAlls(@RequestBody List<Long> ids){
        Boolean delRoleAlls = roleService.delRoleAlls(ids);
        if (delRoleAlls){
            return  super.deleteOperation();
        }else {
            return super.operationFailed("批量删除失败");
        }
    }


    @Override
    public BaseService<Role> getBaseService() {
        return roleService;
    }
}
