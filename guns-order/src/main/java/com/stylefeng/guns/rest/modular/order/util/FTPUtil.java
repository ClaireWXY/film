package com.stylefeng.guns.rest.modular.order.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Wang Xueyang
 * @create 2019-04-25
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "ftp")
public class FTPUtil {
    private String hostName;//地址
    private Integer port;//端口
    private String userName;//用户名
    private String password;//密码

    private FTPClient ftpClient =null;

    private void initFTPClient(){

            ftpClient=new FTPClient();
            ftpClient.setControlEncoding("utf-8");
        try {
            ftpClient.connect(hostName,port);
            ftpClient.login(userName,password);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("初始化FTP失败。"+e);
        }
    }

    //输入一个路径，将路径中的文件转换成字符串然后返回
    public String getFileStrByAddress(String fileAddress){
        BufferedReader bufferedReader=null;

        initFTPClient();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(ftpClient.retrieveFileStream(fileAddress));
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while (true){
                String lineStr = bufferedReader.readLine();
                if (lineStr==null){
                    break;
                }
                stringBuffer.append(lineStr);
            }
            ftpClient.logout();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("获取文件信息失败"+e);
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static  void main(String[] args){
        FTPUtil ftpUtil = new FTPUtil();
        String fileStrByAddress = ftpUtil.getFileStrByAddress("https://clairewang.oss-cn-shanghai.aliyuncs.com/seats/cgs.json");
        System.out.println(fileStrByAddress);
    }
}
