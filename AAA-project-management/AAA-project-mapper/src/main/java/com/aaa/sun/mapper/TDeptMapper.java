package com.aaa.sun.mapper;


import com.aaa.sun.model.TDept;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author LC
 */
public interface TDeptMapper extends Mapper<TDept> {


    List<TDept>  selectAlls (HashMap hashMap);
}