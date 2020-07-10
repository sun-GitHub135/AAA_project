package com.aaa.sun.utils;

import java.util.Random;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/10 20:57
 * @Description
 */
public class FileNameUtils {

    private FileNameUtils(){

    }

    /**
     * @Author sun
     * @Description
     *          文件名的生成
     * @Date 20:57 2020/7/10
     * @Param []
     * @return java.lang.String
     */
    public static String getFileName(){
        // 1.获取当前系统时间的毫秒数
        long currentTimeMillis = System.currentTimeMillis();
        // 2.创建随机数对象
        Random random = new Random();
        // 3.随机从0~999
        int number = random.nextInt(999);
        // 4.生成当前的文件名
        /**
         * format():
         *      格式化方法
         *      %：占位符
         *      03：三位数,如果不够三位，向前补0
         *      d:数字（正则）
         */
        return  currentTimeMillis + String.format("%03d",number);
    }
}
