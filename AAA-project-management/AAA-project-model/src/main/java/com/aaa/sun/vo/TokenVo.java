package com.aaa.sun.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/15 16:12
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TokenVo  implements Serializable {
    private String token;
    private Boolean ifSuccess;
    /**
     * 规定：
     *      1.账号不存在
     *      2.密码错误
     *      3.账号被锁定
     *      4.系统异常
     */
    private Integer type;
}
