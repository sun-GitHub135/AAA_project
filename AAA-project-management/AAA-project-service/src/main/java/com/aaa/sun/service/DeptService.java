package com.aaa.sun.service;


import com.aaa.sun.base.BaseService;
import com.aaa.sun.mapper.DeptMapper;
import com.aaa.sun.model.Dept;
import com.aaa.sun.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;


import java.util.*;



/**
 * @author LC
 */
@Service
public class DeptService  extends BaseService<Dept> {

    @Autowired
    private DeptMapper deptMapper;
    /**
     *条件查询部门信息
     * @param hashMap
     * @return
     */
    public List<Dept> deptList(){
            List<Dept> deptList = new ArrayList<Dept>();
            Dept deptId =new Dept();
            deptId.setParentId(0L);
        List<Dept> tDepts = super.selectList(deptId);
        if (null != tDepts && tDepts.size() >0){
            for (int i = 0; i < tDepts.size(); i++) {
                //获取当前一级部门
                Dept depts = tDepts.get(i);
                Dept dept = new Dept();
                dept.setParentId(depts.getDeptId());
                //获取当前一级部门中所有的子部门
                List<Dept> subDept = super.selectList(dept);
                depts.setSubDept(subDept);
                deptList.add(depts);
            }
        }
        return deptList;
    }

    /**
     * 批量删除部门
     * @param ids
     * @return
     */
    public Integer delDeptAlls(List<Long> ids){
        Example example = Example.builder(Dept.class).where(Sqls.custom().andIn("deptId", ids)).build();
        return deptMapper.deleteByExample(example);
    }


}
