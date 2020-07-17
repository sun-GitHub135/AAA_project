package com.aaa.sun.controller;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.base.CommonController;
import com.aaa.sun.base.ResultData;
import com.aaa.sun.model.Dict;
import com.aaa.sun.model.User;
import com.aaa.sun.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aaa.sun.status.OperationStatus.SUCCESS;

/**
 * @author LC
 */
public class DictController extends CommonController<Dict> {

    @Autowired
    private DictService dictService;

    @Override
    public BaseService<Dict> getBaseService() {
        return dictService;
    }

    /**
     * 分页查询字典信息
     * @param map
     * @return
     */
    @PostMapping("/dictList")
    public ResultData<User>  list(@RequestParam HashMap map){
        PageInfo pageInfo = dictService.selectAlls(map);
        if ( null != pageInfo || !("").equals(pageInfo)){
            return super.operationSuccess(pageInfo);
        }else {
            return  super.operationFailed("查询失败");
        }
    }
    /**
     * 字典新增信息
     * @param map
     * @return
     */
    @PostMapping("/addDict")
    public ResultData addDict(@RequestParam Map map){
        return super.add(map);
    }

    /**
     * 删除字典信息
     * @param map
     * @return
     */
    @PostMapping("/delDict")
    public  ResultData delDict(@RequestParam Map map){
        return super.delete(map);
    }

    /**
     * 修改字典信息
     * @param map
     * @return
     */
    @PostMapping("/updateDict")
    public ResultData updateDict(@RequestParam Map map){
        return super.update(map);
    }

    /**
     * @Description:
     *      批量删除字典
     * @Param: [ids]
     * @Author: lc
     * @Return:
     * @Date: 2020/5/24 17:54
     **/
    @DeleteMapping("/delDictsById")
    public ResultData delDictsById(@RequestBody List<Long> ids)
    {
        System.out.println("-----------------这里是删除方法---------------");
        System.out.println("id"+ids);
        HashMap<String, Object> delDictResult = dictService.delDictById(ids);
        Object code = delDictResult.get("code");
        if (SUCCESS.getCode().equals(code))
        {
            //删除成功
            return super.deleteOperation();
        }
        else
        {
            return super.operationFailed();
        }

    }





}
