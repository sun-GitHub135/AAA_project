package com.aaa.sun.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/8 19:05
 * @Description
 */
@Data
public class User  implements Serializable {
    private String id;
    private String username;
    private String password;
}
