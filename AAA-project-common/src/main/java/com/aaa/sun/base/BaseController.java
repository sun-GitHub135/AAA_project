package com.aaa.sun.base;

import static com.aaa.sun.status.LoginStatus.*;
import static com.aaa.sun.status.OperationStatus.*;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/8 16:50
 * @Description
 *      统一controller
 *      也就是说所有的controller都需要继承这个controller，进行统一返回
 *      eg:
 *          登录成功或失败
 *              code：200 msg：登录成功
 *              code：400 msg：登录失败
 *              code：201 msg：用户已存在
 *              code: 401 msg: 用户不存在
 *              code：402 msg: 密码错误
 *              code：405 msg：用户退出异常
 */
public class BaseController {
    /**
     * @Author sun
     * @Description  登录成功，使用系统消息
     * @Date 17:08 2020/7/8
     * @Param []
     * @return com.aaa.sun.base.ResultData
     **/
    protected ResultData loginSuccess(){
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(LOGIN_SUCCESS.getMsg());
        return resultData;
    }


    /**
     * @Author sun
     * @Description
     *      登录成功
     *            自定义返回消息
     * @Date 21:38 2020/7/10
     * @Param [msg]
     * @return com.aaa.sun.base.ResultData
     */
    protected ResultData loginSuccess(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }


    /**
     * @Author sun
     * @Description
     * 登录成功
     *            返回数据信息，使用系统消息
     * @Date 21:38 2020/7/10
     * @Param [data]
     * @return com.aaa.sun.base.ResultData
     */
    protected ResultData loginSuccess(Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(LOGIN_SUCCESS.getMsg());
        resultData.setData(data);
        return resultData;
    }

    /**
     * @Author sun
     * @Description  登录成功，自定义返回消息（重载）
     *              返回数据信息，使用系统信息
     * @Date 17:08 2020/7/8
     * @Param []
     * @return com.aaa.sun.base.ResultData
     **/
    protected ResultData loginSuccess(String msg , Object data){
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }

    /**
     * @Author sun
     * @Description 登录失败，返回系统消息
     * @Date 18:44 2020/7/8
     * @Param []
     * @return com.aaa.sun.base.ResultData
     **/
    protected ResultData loginFailed(){
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_FAILED.getCode());
        resultData.setMsg(LOGIN_FAILED.getMsg());
        return resultData;
    }

    /**
     * @Author sun
     * @Description 登录系统消息，详细解释说明
     * @Date 18:45 2020/7/8
     * @Param [detail]
     * @return com.aaa.sun.base.ResultData
     **/
    protected ResultData loginFailed(String detail){
        ResultData resultData = new ResultData();
        resultData.setCode(LOGIN_FAILED.getCode());
        resultData.setMsg(LOGIN_FAILED.getMsg());
        resultData.setDetail(detail);
        return resultData;
    }

    /**
     * @Author sun
     * @Description
     *         操作成功，返回系统消息
     * @Date 21:40 2020/7/10
     * @Param []
     * @return com.aaa.sun.base.ResultData
     */
    protected ResultData operationSuccess() {
        ResultData resultData = new ResultData();
        resultData.setCode(SUCCESS.getCode());
        resultData.setMsg(SUCCESS.getMsg());
        return resultData;
    }


    /**
     * @Author sun
     * @Description
     *          操作成功，返回系统消息
     * @Date 21:41 2020/7/10
     * @Param [data]
     * @return com.aaa.sun.base.ResultData
     */
    protected ResultData operationSuccess(Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(SUCCESS.getCode());
        resultData.setMsg(SUCCESS.getMsg());
        resultData.setData(data);
        return resultData;
    }

    /**
     * @Author sun
     * @Description
     *          操作失败，返回系统消息
     * @Date 21:43 2020/7/10
     * @Param []
     * @return com.aaa.sun.base.ResultData
     */
    protected ResultData operationFailed() {
        ResultData resultData = new ResultData();
        resultData.setCode(FAILED.getCode());
        resultData.setMsg(FAILED.getMsg());
        return resultData;
    }

    /**
     * @Author sun
     * @Description
     *      操作失败，返回系统消息
     * @Date 21:43 2020/7/10
     * @Param [msg]
     * @return com.aaa.sun.base.ResultData
     */
    protected ResultData operationFailed(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(FAILED.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    // TODO

    /**
     * 删除操作返回系统消息
     * @return
     */
    protected  ResultData deleteOperation(){
        ResultData resultData = new ResultData();
        resultData.setCode(DELETE_OPERATION.getCode());
        resultData.setMsg(DELETE_OPERATION.getMsg());
        return  resultData;
    }

    /**
     * 修改操作返回消息
     * @return
     */
    protected  ResultData updateOperation(){
        ResultData resultData = new ResultData();
        resultData.setCode(UPDATE_OPERATION.getCode());
        resultData.setMsg(UPDATE_OPERATION.getMsg());
        return resultData;
    }

    /**
     * 新增返回系统消息
     * @return
     */
    protected  ResultData insertOperation(){
        ResultData resultData = new ResultData();
        resultData.setCode(INSERT_OPERATION.getCode());
        resultData.setMsg(INSERT_OPERATION.getMsg());
        return  resultData;
    }



}
