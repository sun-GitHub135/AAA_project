package com.aaa.sun.mapper;

import com.aaa.sun.model.Menu;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zj
 * @Date: 2020/7/13
 */
public interface MenuMapper extends Mapper<Menu> {

    /**
     * 一级菜单
     * @param id
     * @return
     */
    List<Menu> selectFirstMenu(Long id);

    /**
     * 二级菜单
     * @param menuId
     * @return
     */
    @Select("select * from t_menu where PARENT_ID=#{menuId}")
    List<Menu> selectSecondMenu(Long menuId);
}
