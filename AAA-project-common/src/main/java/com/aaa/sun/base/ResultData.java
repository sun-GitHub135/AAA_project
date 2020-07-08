package com.aaa.sun.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/8 16:43
 * @Description
 *              通过接口返回值
 *               也就是说把后端的controller的返回值统一
 *               detail:补充消息状态，给程序员自己设置
 *               T：占位符
 */

@Data
@Accessors(chain = true)
public class ResultData<T> implements Serializable {
    private String code;
    private String msg;
    private String detail;
    private T data;
}
