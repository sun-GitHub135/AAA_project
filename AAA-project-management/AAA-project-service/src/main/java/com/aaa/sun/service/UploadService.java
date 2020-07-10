package com.aaa.sun.service;

import com.aaa.sun.properties.FtpProperties;
import com.aaa.sun.utils.FileNameUtils;
import com.aaa.sun.utils.FtpUtils;
import lombok.Data;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

import static com.aaa.sun.staticproperties.RedisProperties.POINT;
import static com.aaa.sun.staticproperties.TimeForatProperties.*;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/10 20:53
 * @Description
 */
@Service
public class UploadService {

    @Autowired
    private FtpProperties ftpProperties;

    /**
     * @Author sun
     * @Description
     *      文件上传
     * @Date 20:55 2020/7/10
     * @Param [file]
     * @return java.lang.Boolean
     */
    public Boolean upload(MultipartFile file){
        // 1.获取文件的演示名称（为了获取文件后缀名）
        String oleFileName = file.getOriginalFilename();
        // 2.生成新的文件名
        String newFileName = FileNameUtils.getFileName();
        // 3.截取后缀名
        newFileName = newFileName + oleFileName.substring(oleFileName.lastIndexOf(POINT));
        // 4.获取文件的上传路径（2020/07/10）
        // TODO 暂时没有完成，目前使用的是apache开源基金会的日期工具类，不符合我们团队的技术水平，需要自己手动编写
        String filePath = DateUtil.formatDate(new Date(), DATE_FORMAT);
            // 5.调用文件上传工具类
            try {
            return FtpUtils.upload(ftpProperties.getHost(), ftpProperties.getPort(), ftpProperties.getUsername(),ftpProperties.getPassword(),
            ftpProperties.getBasePath(), filePath,newFileName,file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
