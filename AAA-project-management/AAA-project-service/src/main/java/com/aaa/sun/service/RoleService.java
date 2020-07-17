package com.aaa.sun.service;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.mapper.RoleMapper;
import com.aaa.sun.model.Menu;
import com.aaa.sun.model.Role;
import com.aaa.sun.model.RoleMenu;
import com.aaa.sun.vo.RoleVo;
import com.aaa.sun.vo.TokenVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javafx.scene.chart.PieChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * @author LC
 */
@Service
public class RoleService extends BaseService {


    @Autowired
    private RoleMapper roleMapper;

    /**
     * 分页查询角色
     * @param roleVo
     * @param role_id
     * @return
     * @throws Exception
     */
    public PageInfo<Role> selectAllRole(RoleVo roleVo, Long role_id) throws  Exception{
        if (roleVo.getPageNo() == null){
            roleVo.setPageNo(1);
        }
        if (roleVo.getPageSize()==null){
            roleVo.setPageSize(10);
        }
        Role role =new Role();
        Role voRole =  roleVo.getRole();
        if (voRole != null){
            role.setRoleId(voRole.getRoleId());
        }
        role.setRoleId(role_id);
        PageInfo<Role> pageInfo = selectListByPage(role,roleVo.getPageNo(),roleVo.getPageSize());
        return  pageInfo;
    }

    /**
     * 添加一个权限
     * @param roleVo
     * @return
     */
    public Boolean addRole(RoleVo roleVo){
        roleVo.getRole().setCreateTime(new Date() );
        //添加角色信息
        Integer add = super.add(roleVo.getRole());
        //如果大于0添加成功
        if (add > 0){
            //如果为空，只是添加一个角色
            if (null == roleVo.getMenuId() || "".equals(roleVo.getMenuId())){
                return true;
            } else {
                int res = 0;
                for (Long menuId:roleVo.getMenuId()) {
                    //添加到"角色与菜单关联表"
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setMenuId(menuId);
                    roleMenu.setRoleId(roleVo.getRole().getRoleId());
                    res = roleMapper.addRoleMenu(roleMenu);
                }
                return res > 0;
            }
        }

        return false;

    }


    /**
     * 修改权限
     * @param roleVo
     * @return
     */
    public Boolean updateRole(RoleVo roleVo){
            int rul=0;
        roleVo.getRole().setCreateTime(new Date() );
        Integer update = super.update(roleVo.getRole());
        if (update > 0){
            if (null == roleVo.getMenuId() || "".equals(roleVo.getMenuId())){
                return  true;
            }else {
                List<RoleMenu> roleMenus = roleMapper.selectByRoleId(roleVo.getRole().getRoleId());
                if (roleMenus.size() > 0 ){
                    int delRoleMenu = roleMapper.delRoleMenu(roleVo.getRole().getRoleId());
                    if (delRoleMenu > 0){
                        for (Long menuId:roleVo.getMenuId()) {
                            RoleMenu roleMenu =new RoleMenu();
                            roleMenu.setRoleId(roleVo.getRole().getRoleId());
                            roleMenu.setMenuId(menuId);
                            rul = roleMapper.addRoleMenu(roleMenu);
                        }
                        return rul > 0;
                    }else  {
                        return  false;
                    }
                }else {
                    for (Long menuId:roleVo.getMenuId()){
                        RoleMenu roleMenu =new RoleMenu();
                        roleMenu.setRoleId(roleVo.getRole().getRoleId());
                        roleMenu.setMenuId(menuId);
                        rul = roleMapper.addRoleMenu(roleMenu);
                    }
                    return rul > 0;
                }
            }
        }
        return false;
    }


    /**
     * 删除权限
     * @param roleId
     * @return
     */
    public Boolean deleteRole(Long roleId){
        Role role = new Role();
        role.setRoleId(roleId);
        Integer delete = super.delete(role);
        if (delete > 0){
            List<RoleMenu> roleMenus = roleMapper.selectByRoleId(roleId);
            if (roleMenus.size() >0){
                int i = roleMapper.delRoleMenu(roleId);
                return i > 0;
            }else {
                return  false;
            }
        }else {
            return false;
        }

    }

    /**
     * 删除所有权限
     * @param ids
     * @return
     */
    public Boolean delRoleAlls(List<Long> ids){
        int rq = 0;
        Example example = Example.builder(Menu.class).where(Sqls.custom().andIn("roleId",ids)).build();
        int res = roleMapper.deleteByExample(example);
        if (res > 0){
            //查询当前角色是否拥有权限
            for (Long id : ids){
                List<RoleMenu> roleMenus = roleMapper.selectByRoleId(id);
                if (roleMenus.size() > 0){
                    //删除当前角色的所有权限信息
                    rq = roleMapper.delRoleMenu(id);
                }else {
                    return true;
                }
            }
            return rq > 0;
        } else {
            return false;
        }
    }

}
