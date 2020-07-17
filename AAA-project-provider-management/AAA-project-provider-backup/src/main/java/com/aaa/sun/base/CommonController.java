package com.aaa.sun.base;

import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/8 18:53
 * @Description
 */
public abstract class CommonController<T> extends BaseController {
    /**
     * @Author sun
     * @Description
     *      钩子函数
     *          在新增之前执行某些操作
     *          下单操作：
     *              需求：在购物车当中点击下单按钮的时候---》跳转下单页面（选择地址，选择优惠卷）
     *              把购物车中的这个商品删除
     *              deleteCart(List<Integer> id);--->是优先于insertOrder前置执行
     *
     *              insertOrder(Order order)
     * @Date 19:58 2020/7/9
     * @Param [map]
     * @return void
     */
    protected void  beforeAdd(Map map){
        //TODO AddMethod Before to do something
    }


    /**
     * @Author sun
     * @Description
     *      钩子函数
     *      是在新增之后执行
     *
     *      int result = insertOrder(Order order)
     *      if (result>0){
     *          insertOrderDetail (OrderDetail orderDetail);
     *      }
     * @Date 20:03 2020/7/9
     * @Param [map]
     * @return void
     */
    protected void afterAdd(Map map){
        //TODO  AddMethod After to do something
    }

    public abstract BaseService<T> getBaseService();


    /**
     * @Author sun
     * @Description
     *      通用新增方法
     *      因为目前市面上的所有公司，实现的都是异步
     *      前端向后端所传递的都是json格式
     *      之前controller的方法中接受固定的实体类，是因为我们知道前端传递的类型就是这个实体类
     *      但是既然做通用，前端传递的了类型就不会固定------>所以使用Map统一接收
     * @Date 20:09 2020/7/9
     * @Param [map]
     * @return com.aaa.sun.base.ResultData
     */
    public ResultData add (@RequestBody Map map){
        // 因为根据我们封装的规则，在service中需要传递泛型，意味着service需要接受固定的实体类
        // 但是controller是一个Map类型
        beforeAdd(map);
        // 1.Map转实体类
        T instance = getBaseService().newInstance(map);
        // 2.通用service
        Integer addResult = getBaseService().add(instance);
        if (addResult > 0){
            afterAdd(map);
            return super.operationSuccess();
        }
        return super.operationFailed();
    }


    /**
     * @Author sun
     * @Description
     *          删除操作
     * @Date 21:38 2020/7/9
     * @Param [map]
     * @return com.aaa.sun.base.ResultData
     */
    public ResultData delete(@RequestBody Map map){
        T instance = getBaseService().newInstance(map);
        Integer deleteResult = getBaseService().delete(instance);
        if (deleteResult >0){
            return super.operationSuccess();
        }
        return super.operationFailed();
    }


    /**
     * @Author sun
     * @Description
     *      批量删除
     * @Date 21:41 2020/7/9
     * @Param [ids]
     * @return com.aaa.sun.base.ResultData
     */
    public ResultData batchDelete(@RequestParam ("ids[]") Integer[] ids){
        Integer deleteByIds = getBaseService().deleteByIds(Arrays.asList(ids));
        if(deleteByIds >0 ){
            return super.operationSuccess();
        }
        return super.operationSuccess();
    }

    /**
     * @Author sun
     * @Description 修改数据
     * @Date 8:41 2020/7/10
     * @Param [map]
     * @return com.aaa.lee.base.ResultData
     */
    public ResultData update(@RequestBody Map map) {
        T instance = getBaseService().newInstance(map);
        Integer i = getBaseService().update(instance);
        if (i > 0) {
            return super.operationSuccess();
        }
        return super.operationFailed();
    }

    /**
     * @Author sun
     * @Description  查询一条数据
     * @Date 8:42 2020/7/10
     * @Param [map]
     * @return com.aaa.lee.base.ResultData
     */
    public ResultData selectOne(@RequestBody Map map) {
        T instance = getBaseService().newInstance(map);
        T t = getBaseService().selectOne(instance);
        if (null != t && !"".equals(t)) {
            return super.operationSuccess();
        }
        return super.operationFailed();
    }



    /**
     * 查询数据列表
     * @param map
     * @return
     */
    public ResultData selectList(@RequestBody Map map){
        ResultData resultData = new ResultData();
        T instance = getBaseService().newInstance(map);
        List<T> selectList = getBaseService().selectList(instance);
        if (null != selectList){
            return super.operationSuccess(selectList,"查询数据列表成功!");
        }
        return super.operationFailed("查询数据列表失败!");
    }



    /**
     * 不带条件分页查询
     * @param map
     * @return
     */
    public ResultData selectListByPage(@RequestBody Map map){
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");
        Object t = map.get("t");
        PageInfo<T> tPageInfo = getBaseService().selectListByPage((T) t, pageNo, pageSize);
        if (null != tPageInfo){
            return super.operationSuccess(tPageInfo,"分页查询数据列表成功!");
        }
        return super.operationFailed("分页查询数据列表失败!");
    }


    /**
     * @Author sun
     * @Description 带条件分页查询
     * @Date 15:08 2020/7/10
     * @Param [map, pageNo, pageSize]
     * @return com.aaa.sun.base.ResultData
     */
    public ResultData getListByPage(@RequestBody Map map,@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
        T t = getBaseService().newInstance(map);
        PageInfo<T> tPageInfo = getBaseService().selectListByPage(t, pageNo, pageSize);
        List<T> resultList = tPageInfo.getList();
        if (resultList.size()>0){
            return operationSuccess(tPageInfo);
        }
        return operationFailed("未查询到结果");
    }

    /**
     * @Author sun
     * @Description 带条件分页查询
     * @Date 15:08 2020/7/10
     * @Param [map, pageNo, pageSize]
     * @return com.aaa.sun.base.ResultData
     */
    public ResultData getListByPage(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
        PageInfo<T> tPageInfo = getBaseService().selectListByPage(null, pageNo, pageSize);
        List<T> resultList = tPageInfo.getList();
        if (resultList.size()>0){
            return operationSuccess(tPageInfo);
        }
        return operationFailed("未查询到结果");
    }



    /**
     * @Author sun
     * @Description 防止数据不安全，所以不能直接在controller某个方法中直接接收HttpServletRequest对象
     *       必须要从本地当前线程中获取request对象
     * @Date 8:45 2020/7/10
     * @Param []
     * @return HttpServletRequest
     */
    public HttpServletRequest getServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes;
        if (requestAttributes instanceof ServletRequestAttributes) {
            servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    
    /**
     * @Author sun
     * @Description 获取当前客户端的session对象(如果不存在 ， 则会重新创建一个)
     * @Date 8:46 2020/7/10
     * @Param []
     * @return javax.servlet.http.HttpSession
     */
    public HttpSession getSession() {
        return getServletRequest().getSession();
    }

    /**
     * @Author sun
     * @Description 获取当前客户端的session对象(如果不存在 ， 则直接返回为null)
     * @Date 8:46 2020/7/10
     * @Param []
     * @return javax.servlet.http.HttpSession
     */
    public HttpSession getExistSession() {
        return getServletRequest().getSession(false);

    }
}
