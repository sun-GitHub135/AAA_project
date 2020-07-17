package com.aaa.sun.service;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.mapper.MenuMapper;
import com.aaa.sun.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LC
 */
@Service
public class MenuService extends BaseService<Menu> {

    @Autowired
   private MenuMapper menuMapper;


    /**
     * 查询菜单所有信息
     * @param id
     * @return
     */
    public List<Menu> allMenu(Long id){
        List<Menu> allMenu = new ArrayList<Menu>();
        //获取一级菜单
        List<Menu> menus = menuMapper.selectFirstMenu(id);
        if ( null != menus && menus.size() > 0){
            for (int i =0 ;i<menus.size();i++){
                Menu menu = menus.get(i);
                List<Menu> menus1 = menuMapper.selectSecondMenu(menu.getMenuId());
                menu.setSubMenu(menus1);
                allMenu.add(menu);
            }
        }
        return allMenu;
    }

    /**
     * 查询二级菜单
     * @return
     */
    public  List<Menu> allMenus(){
        Menu menu = new Menu();
        menu.setParentId(0L);
        List<Menu> allMenu = new ArrayList();
        List<Menu> tMenu = super.selectList(menu);
        if (null != tMenu && tMenu.size()>0 ){
        for (int i=0;i<tMenu.size();i++){
            Menu menu1 = tMenu.get(i);
            List<Menu> menus = menuMapper.selectSecondMenu(menu1.getMenuId());
            menu1.setSubMenu(menus);
            allMenu.add(menu1);
        }
        }
        return allMenu;
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    public Integer delMenu(Long menuId){
        int delOne = menuMapper.deleteByPrimaryKey(menuId);
        Menu menu =new Menu();
        menu.setParentId(menuId);
        menuMapper.delete(menu);
        return  delOne;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public  Integer delMenuAll(List<Long> ids){
        Example example = Example.builder(Menu.class).where(Sqls.custom().andIn("menuId",ids)).build();
        return menuMapper.deleteByExample(example);
    }
}
