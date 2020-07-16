package com.aaa.sun.mapper;

import com.aaa.sun.model.Dict;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author LC
 */
public interface DictMapper extends Mapper<Dict> {


    List<Dict>  selectAlls(HashMap hashMap);
}