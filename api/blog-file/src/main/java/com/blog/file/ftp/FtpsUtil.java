package com.blog.file.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/2 16:40
 */

@Slf4j
@Service
public class FtpsUtil {

//    @Value("${ftps.data.connection.tls.version}")
//    private String dataConnectionTlsVersion;

    /**
     * ftp发送数据超时时间，默认5000ms
     */
//    @Value("${ftps.data.timeout:5000}")
//    private Integer dataTimeout;

    private FTPSClient ftpsClient;

    private boolean init() {
        ftpsClient = new FTPSClient(true);
        if (ftpsClient.isConnected()) {
            return true;
        }
        log.info(">>>>> 开始连接ftps");
        int reply = 0;
        try {
            String ftpIp = "10.56.23.145";
            Integer ftpPort = 10012;
            String ftpUsername = "test3";
            String ftpPassword = "Admin123";
            ftpsClient.setConnectTimeout(3000);
            ftpsClient.connect(ftpIp, ftpPort);
            ftpsClient.login(ftpUsername, ftpPassword);
            reply = ftpsClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpsClient.disconnect();
            } else {
                log.info(">>>>>Successful to connect the FTPS Server");
                ftpsClient.setControlEncoding("UTF-8");
                ftpsClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpsClient.enterLocalActiveMode();
                ftpsClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
                ftpsClient.execPBSZ(0);
                ftpsClient.execPROT("P");
            }
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 创建ftp目录并切换ftp工作目录
     *
     * @param pathName
     * @return void
     * @throws IOException
     * @auther 27919
     * @date 2020年5月16日
     */
    private void createDirectoryByPathName(String pathName) throws IOException {
        String[] dirList = pathName.split("/");
        for (String dir : dirList) {
            log.info("current directory : {}", dir);
            if (!ftpsClient.changeWorkingDirectory(dir)) {
                ftpsClient.makeDirectory(dir);
                ftpsClient.changeWorkingDirectory(dir);
            }
        }
    }

    /**
     * 上传文件到
     * @param pathName
     * @param fileName
     * @param bytes
     * @return String
     * @auther 27919
     * @date 2020年5月16日
     */
    public boolean uploadFtpFile(String pathName, String fileName, byte[] bytes) {

        boolean initSuccess = this.init();
        if(!initSuccess){
            return false;
        }
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            // create hierachy directory
            createDirectoryByPathName(new String(pathName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            String fn = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            System.out.println(ftpsClient.printWorkingDirectory());
            boolean success = ftpsClient.storeFile(fn, inputStream);

            return true;
        } catch (Exception e) {
            log.error(">>>>>Failed to upload the file to FTP Server!", e);
        } finally {
            if (!ftpsClient.isConnected()) {
                try {
                    ftpsClient.disconnect();
                } catch (IOException e) {
                    log.error(">>>>>Failed to disconnect ftpsClient!", e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(">>>>>Failed to close inputStream!", e);
                }
            }
        }
        return false;
    }
}
