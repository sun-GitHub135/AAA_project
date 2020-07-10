package com.aaa.sun.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import sun.net.ftp.FtpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/10 20:02
 * @Description
 */
public class FtpUtils {
    // 构造方法私有化；不让他去new对象
    private FtpUtils(){

    }


    /**
     * @Author sun
     * @Description
     *      ftp文件上传方法工具
     * @Date 20:06 2020/7/10
     * @Param [host, port, password, basePath, filePath, fileName, inputStream]
     * @return java.lang.Boolean
     */
    public static Boolean upload(String host, Integer port, String username, String password, String basePath,
                                 String filePath, String fileName, InputStream inputStream)  {
        /**
         * 按照每天的日期的文件夹进行上传、
         *          2020/7/10--》10 文件夹
         *          cd/home/redis/src
         */
        // 1.创建临时路径
        String tempPath="";
        // 2.创建FTPClient对象，这个对象是FTP给Java提供的api
        FTPClient ftpClient = new FTPClient();

        try{
            // 3.定义返回状态码
            int replyCode;
            // 4.连接ftp
            ftpClient.connect(host,port);
            // 5.登录ftp服务器
            ftpClient.login(username,password);
            // 6.接受返回的状态码
            replyCode = ftpClient.getReplyCode();// 如果成功返回230，如果失败则返回503
// 7.判断
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                // 连接失败
                ftpClient.disconnect();
                return  false;
            }
            // 8.先检测上传的目录是否存在（2020/07/10）
            // basePath:/home/ftp/www
            // filePath:/2020/07/10
            // ---> 拼接 /home/ftp/www/2020/07/10
            if (!ftpClient.changeWorkingDirectory(basePath + fileName)){
                // 该文件夹不存在
                // 9.创建文件夹
                String [] dirs = filePath.split("/");//  [2020,07,10]
                // 10.把basePath赋值给临时路径
                // tempPath：/home/ftp/www/
                tempPath = basePath;
                // 11.循环
                for (String dir : dirs){
                    if (null == dir || StringUtils.isEmpty(dir)){
                        // 没有数据
                        continue;
                    }
                    // 12.拼接临时路径（如果没有进去if，则取到的值应该是2020）
                    tempPath += "/" + dir;
                    // tempPath : /home/ftp/www/2020
                    // 13.再次检测tempPath是否存在
                    if (ftpClient.changeWorkingDirectory(tempPath)){
                        // 文件夹不存在
                        // 14.创建文件夹
                        if (!ftpClient.makeDirectory(tempPath)){
                            // 说明文件夹创建失败
                            return false;
                        }else {
                            // 15.严谨判断，判断创建出来的目录确实存在
                            ftpClient.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            // 16.把文件转换为二进制的形式来进行上传
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // 17.这里才是真正的文件上传
            if (!ftpClient.storeFile(fileName,inputStream)){
                // 说明上传失败
                return false;
            }
            // 18.关闭输入流
            inputStream.close();
            // 19.推出我的ftp
            ftpClient.logout();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (ftpClient.isConnected()){
                // 说明正在连接中
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
