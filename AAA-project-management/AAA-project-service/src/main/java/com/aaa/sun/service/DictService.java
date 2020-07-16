package com.aaa.sun.service;

import com.aaa.sun.base.BaseService;
import com.aaa.sun.mapper.DictMapper;
import com.aaa.sun.model.Dict;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.HashMap;
import java.util.List;

import static com.aaa.sun.status.OperationStatus.DELETE_OPERATION;
import static com.aaa.sun.status.OperationStatus.FAILED;

/**
 * @author LC
 */
@Service
public class DictService extends BaseService<Dict> {

    @Autowired
    private  DictMapper dictMapper;

    /**
     * 分页条件查询字典信息
     * @param hashMap
     * @return
     */
    public PageInfo selectAlls(HashMap hashMap){
        PageHelper.startPage(Integer.parseInt(hashMap.get("pageNo")+""),Integer.parseInt(hashMap.get("pageSize")+""));
        PageInfo page = new PageInfo(dictMapper.selectAlls(hashMap));
        if (null != page && !"".equals(page)){
            return page;
        }
        return  null;
    }


    /**
     * 根据ID批量删除
     * @param ids
     * @return
     */
    public HashMap<String,Object>  delDictById(List<Long> ids){
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        //获取参数类型
        Example example = Example.builder(Dict.class).where(Sqls.custom().andIn("dictId",ids)).build();
        int i = dictMapper.deleteByExample(example);
        if (i>0){
            resultMap.put("code",DELETE_OPERATION.getCode());
            resultMap.put("msg",DELETE_OPERATION.getMsg());
        }else {
          resultMap.put("code", FAILED.getCode());
          resultMap.put("msg",FAILED.getMsg());
        }
        return  resultMap;
    }
}
