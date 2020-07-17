package com.aaa.sun.controller;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.base.CommonController;
import com.aaa.sun.base.ResultData;
import com.aaa.sun.model.Menu;
import com.aaa.sun.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LC
 */
@RestController
public class MenuController extends CommonController<Menu> {

    @Autowired
    private MenuService menuService;

    @Override
    public BaseService<Menu> getBaseService() {
        return menuService;
    }


    /**
     * 查询菜单
     * @return
     */
    @GetMapping("/allMenu")
    public ResultData<Menu> allMenu(){
        List<Menu> menus = menuService.allMenu(1L);
        if (null != menus && menus.size()  != 0){
        return  super.operationSuccess(menus);
        }else {
            return  super.operationFailed("查询失败");
        }
    }


    /**
     * 查询子菜单
     * @return
     */
    @GetMapping("/allMenus")
    public  ResultData<Menu> allMenus(){
        List<Menu> menus = menuService.allMenus();
        if (null != menus && menus.size()  != 0){
            return  super.operationSuccess(menus);
        }else {
            return  super.operationFailed("查询失败");
        }
    }

    /**
     * 添加一个菜单
     * @param map
     * @return
     */
    @PostMapping("/insetMenu")
    public ResultData insetMenu(@RequestParam Map map){
            map.put("parentId",0L);
            map.put("menuName","游戏菜单");
            map.put("type","1");
            map.put("createTime",new Date());
            return super.add(map);
    }

    /**
     * 修改菜单
     * @param map
     * @return
     */
    @PostMapping("/updateMenus")
    public  ResultData updateMenus(@RequestParam Map map){
        return  super.update(map);
    }

    /**
     * 删除
     * @param  // STOPSHIP: 2020/7/16
     * @return
     */
    @PostMapping("/delMenu")
    public ResultData delMenu(@RequestParam Long menuId){
        Integer integer = menuService.delMenu(menuId);
        if (integer >0){
            return super.deleteOperation();
        }else {
            return  super.operationFailed();
        }
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/delMenuAll")
    public ResultData delMenuAll(@RequestBody List<Long> ids){
        Integer integer = menuService.delMenuAll(ids);
        if (integer>0){
            return super.deleteOperation();
        }else {
            return super.operationFailed();
        }
    }
}
