package com.aaa.sun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author LC
 */
@Table(name = "t_dict")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Dict implements Serializable {
    /**
     * 字典ID
     */
    private Long dictId;

    /**
     * 键
     */
    private Long keyy;

    /**
     * 值
     */
    private String valuee;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 表名
     */
    private String tableName;


}