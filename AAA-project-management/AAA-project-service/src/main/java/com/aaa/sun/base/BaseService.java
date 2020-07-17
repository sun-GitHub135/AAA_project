package com.aaa.sun.base;

import com.aaa.sun.utils.Map2BeanUtils;
import com.aaa.sun.utils.SpringContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.aaa.sun.staticproperties.OrderStatic.*;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/8 18:57
 * @Description  通用service
 *              这个service中封装了很多通用方法
 */
public abstract class BaseService<T> {
    /**
     *   全局变量，缓存子类的泛型类型
     */
    private Class<T> cache  = null;

    @Autowired
    private Mapper<T> mapper;

    protected Mapper getMapper(){
        return mapper;
    }
    /**
     * @Author sun
     * @Description  新增数据
     * @Date 16:54 2020/7/9
     * @Param [t]
     * @return java.lang.Integer
     **/
    public Integer add (T t){
        return mapper.insert(t);
    }

    /**
     * @Author sun
     * @Description 根据主键进行删除
     * @Date 16:54 2020/7/9
     * @Param [t]
     * @return java.lang.Integer
     **/
    public Integer delete(T t){
        return mapper.deleteByPrimaryKey(t);
    }

    /**
     * @Author sun
     * @Description   根据主键进行批量删除
     * @Date 16:56 2020/7/9
     * @Param [ids]
     * @return java.lang.Integer
     **/

    public Integer deleteByIds(List<Integer>  ids){
        /**
         *         delete * from user where username = zhangsan and id in (1,2,3,4,5,6)
         */
        Example example = Example.builder(getTypeArguement()).where(Sqls.custom().andIn("id", ids)).build();
        return mapper.deleteByPrimaryKey(example);
    }

    /**
     * @Author sun
     * @Description  更新操作
     * @Date 17:13 2020/7/9
     * @Param
     * @return
     */

    public Integer update(T t){
        return mapper.updateByPrimaryKeySelective(t);
    }

    /**
     * @Author sun
     * @Description 批量更新操作
     *          update username = ? from user where id in (1,2,3,4,5,6)
     * @Date 17:16 2020/7/9
     * @Param
     * @return
     */
    public Integer batchUpdate(T t,Integer ids){
        Example example = Example.builder(getTypeArguement()).where(Sqls.custom().andIn("id", Arrays.asList(ids))).build();
        return mapper.updateByExample(t,example);
    }

    /**
     * @Author sun
     * @Description 查询一条数据  形参中所传递的数据---》主键，唯一键
     * @Date 18:36 2020/7/9
     * @Param
     * @return
     */
    public T selectOne(T t){
        return mapper.selectOne(t);
    }





    /**
     * @Author sun
     * @Description 查询一条数据，可以排序（orderByFiled :ASC,DESC）
     *              fileds:不只代表唯一键
     *              password
     *              age
     *              address
     *              select * from user where password = XXX and age = xxx and address = xxx
     * @Date 18:39 2020/7/9
     * @Param
     * @return
     */
    public T selectOneByFiled(Sqls where, String orderByFiled,String... fileds){
        return selectByFileds(null,null, where, orderByFiled, null, fileds).get(0);
    }

    
    /**
     * @Author sun
     * @Description 
     * @Date 19:11 2020/7/9
     * @Param [pageNo, pageSize, where, orderFiled, fileds]
     * @return com.github.pagehelper.PageInfo<T>
     */
    public PageInfo<T> selectListByPageAndFiled(Integer  pageNo, Integer pageSize, Sqls where, String orderFiled, String... fileds){
        return new PageInfo<T>(selectByFileds(pageNo, pageSize,where,orderFiled,null,fileds));
    }


    /**
     * @Author sun
     * @Description 查询集合，条件查询
     * @Date 19:13 2020/7/9
     * @Param [t]
     * @return java.util.List<T>
     */
    public List<T> selectList(T t){
        return mapper.select(t);
    }

    /**
     * @Author sun
     * @Description
     *     查询集合，分页查询
     * @Date 19:14 2020/7/9
     * @Param []
     * @return com.github.pagehelper.PageInfo<T>
     */
    public PageInfo<T> selectListByPage(T t, Integer pageNo, Integer pageSize){
        PageHelper.startPage(pageNo, pageSize);
        List<T> select = mapper.select(t);
        PageInfo<T> pageInfo = new PageInfo<T>(select);
        return pageInfo;
    }


    /**
     * @Author sun
     * @Description
     *      Map转换实体类型
     * @Date 20:19 2020/7/9
     * @Param [map]
     * @return T
     */
    public T newInstance(Map map){
        return (T) Map2BeanUtils.map2Bean(map,getTypeArguement());
    }


    /**
     * @Author sun
     * @Description
     *       实现查询通用
     *       不但可以作用与分页，还可以用于排序，还能作用与多条件查询
     *       orderByFiled:所要排序的字段
     * @Date 18:42 2020/7/9
     * @Param
     * @return
     */
    private List<T> selectByFileds(Integer pageNo, Integer pageSize, Sqls where, String orderByFiled,String orderWord, String... fileds){
        Example.Builder builder = null;
        if (null == fileds || fileds.length == 0){
            //查询所有数据
            builder = Example.builder(getTypeArguement());
        }else {
            // 说明需要进行条件查询
            builder = Example.builder(getTypeArguement()).select(fileds);
        }
        if (where !=null){
            //说明有用户的自定义的where语句条件
            builder = builder.where(where);
        }
        if (orderByFiled != null){
            // 说明我需要对某个字段进行排序\
            if (DESC.equals(orderByFiled.toUpperCase())){
                builder = builder.orderByDesc(orderByFiled);
            }else  if (ASC.equals(orderByFiled.toUpperCase())){
                builder = builder.orderByAsc(orderByFiled);
            }else {
                builder = builder.orderByDesc(orderByFiled);
            }
        }
        Example example = builder.build();
        // 实现分页
        if (pageNo !=null & pageSize != null){
            PageHelper.startPage(pageNo,pageSize);
        }
        return getMapper().selectByExample(example);
    }

    /**
     * @Author sun
     * @Description 获取子类泛型类型
     * @Date 17:05 2020/7/9
     * @Param
     * @return
     */
    public Class<T> getTypeArguement(){
        if (null ==  cache){
            cache = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return cache;
    }

    /**
     * @Author sun
     * @Description
     *          获取spring容器上获取spring的上下文
     *          在项目开始运行的时候，会去加载spring配置
     *           如果项目需要在启动的时候加载自己的配置文件
     *           在spring的源码中有一个方法（init()）
     *           init()--->就是在项目启动的时候会去加载spring的配置
     *           如果项目中需要把一些配置一开始就托管给审判日给你
     *           需要获取spring的上下文ApplicationContext
     * @Date 19:20 2020/7/9
     * @Param []
     * @return org.apache.catalina.core.ApplicationContext
     */
    public ApplicationContext getApplicationContext(){
        return SpringContextUtils.getApplicationContext();
    }
}
