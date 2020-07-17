package com.aaa.sun.mapper;

import com.aaa.sun.model.Role;
import com.aaa.sun.model.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: zj
 * @Date: 2020/7/14
 */
public interface RoleMapper extends Mapper<Role> {
    /**
     * 分页查询所有角色
     * @param hashMap
     * @return
     */
    List<Role> selectAlls(HashMap hashMap);

    /**
     * 添加"角色与菜单关联表"信息
     * @param roleMenu
     * @return
     */
    @Insert("insert into t_role_menu(ROLE_ID,MENU_ID) values(#{roleId},#{menuId})")
    int addRoleMenu(RoleMenu roleMenu);

    /**
     * 查询当前角色的权限信息
     * @param roleId
     * @return
     */
    @Select("select * from t_role_menu where ROLE_ID=#{roleId}")
    List<RoleMenu> selectByRoleId(Long roleId);

    /**
     * 删除"角色与菜单关联表"当前roleId信息
     * @param roleId
     * @return
     */
    @Delete("delete from t_role_menu where ROLE_ID=#{roleId}")
    int delRoleMenu(Long roleId);
}
