package com.aaa.sun.status;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/8 17:05
 * @Description
 *      枚举是在封装的时候，规定的一些永远不会改变的状态值或数据
 */
public enum LoginStatus {
    /**
     * 200
     * 登陆成功
     */
    LOGIN_SUCCESS("200","登录成功"),
    LOGIN_FAILED("400","登录失败，系统异常"),
    USER_EXIST("201","用户已存在"),
    USER_NOT_WRONG("401","用户不存在"),
    PASSWORD_WRONG("402","密码错误"),
    LOGOUT_WRONG("405","用户退出异常");

    LoginStatus(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
