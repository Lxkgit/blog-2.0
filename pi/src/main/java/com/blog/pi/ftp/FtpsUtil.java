package com.blog.pi.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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
        log.info("开始连接ftps");
        int reply = 0;
        try {
            String ftpIp = "10.56.23.145";
            int ftpPort = 10012;
            String ftpUsername = "test3";
            String ftpPassword = "Admin123";
            ftpsClient.setConnectTimeout(3000);
            ftpsClient.connect(ftpIp, ftpPort);
            ftpsClient.login(ftpUsername, ftpPassword);
            reply = ftpsClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpsClient.disconnect();
            } else {
                log.info("ftps 连接成功");
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
     * @param pathName 文件目录
     * @return void
     * @throws IOException
     * @auther 27919
     * @date 2020年5月16日
     */
    private void createDirectoryByPathName(String pathName) throws IOException {
        String[] dirList = pathName.split("/");
        for (String dir : dirList) {
            log.info("ftps 当前目录 : {}", dir);
            if (!ftpsClient.changeWorkingDirectory(dir)) {
                ftpsClient.makeDirectory(dir);
                ftpsClient.changeWorkingDirectory(dir);
            }
        }
    }

    /**
     * 上传文件到ftps服务器
     *
     * @param sourceFilePath 源文件位置
     * @param sourceFileName 源文件名称
     * @param targetFilePath 服务器文件路径
     * @param targetFileName 服务器文件名称
     * @return 文件上传是否成功
     */
    public boolean uploadFtpFile(String sourceFilePath, String sourceFileName, String targetFilePath, String targetFileName) {
        log.info("ftps 上传文件  sourcePath: {}, sourceFileName: {}, targetName: {}, targetFileName: {}", sourceFilePath, sourceFileName, targetFilePath, targetFileName);
        boolean initSuccess = this.init();
        if (!initSuccess) {
            return false;
        }
        InputStream inputStream = null;
        try {
            File file = new File(sourceFilePath + sourceFileName);
            byte[] bytes = Files.readAllBytes(file.toPath());
            inputStream = new ByteArrayInputStream(bytes);
            createDirectoryByPathName(new String(targetFilePath.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            String fn = new String(targetFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            boolean success = ftpsClient.storeFile(fn, inputStream);
            log.info("ftps 文件上传结果 " + success);
            return success;
        } catch (Exception e) {
            log.error("ftps 文件上传失败", e);
        } finally {
            if (!ftpsClient.isConnected()) {
                try {
                    ftpsClient.disconnect();
                } catch (IOException e) {
                    log.error("ftps 文件上传失败", e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("ftps 文件上传失败", e);
                }
            }
        }
        return false;
    }



    /**
     * 下载服务器文件
     * @param serviceFilePath 服务器文件目录
     * @param serviceFileName 服务器文件名称
     * @param localFilePath 本地存放文件目录
     * @param localFileName 本地存放文件名称
     * @return 下载结果
     */
    public boolean downloadFtpFile(String serviceFilePath, String serviceFileName, String localFilePath, String localFileName) {
        log.info("ftps 下载文件  pathName: {}, fileName: {}", serviceFilePath, serviceFileName);
        init();
        try {
            ftpsClient.changeWorkingDirectory(new String(serviceFilePath.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            File localFile = new File(localFilePath + File.separatorChar + localFileName);
            OutputStream os = new FileOutputStream(localFile);
            boolean success = ftpsClient.retrieveFile(new String(serviceFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1), os);
            os.close();
            log.info("ftps 文件下载结果 " + success);
            return success;
        } catch (IOException e) {
            log.error("ftps 文件下载失败", e);
        } finally {
            if (!ftpsClient.isConnected()) {
                try {
                    ftpsClient.completePendingCommand();
                    ftpsClient.disconnect();
                } catch (IOException e) {
                    log.error("ftps 文件下载失败", e);
                }
            }
        }
        return false;
    }


    /**
     * 删除ftps服务器文件
     *
     * @param pathName 文件目录
     * @param fileName 文件名称
     * @return 删除结果
     */
    public boolean removeFile(String pathName, String fileName) {
        log.info("ftps 删除文件  pathName: {}, fileName: {}", pathName, fileName);
        init();
        try {
            ftpsClient.changeWorkingDirectory(new String(pathName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            boolean success = ftpsClient.deleteFile(new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            log.info("ftps 文件删除结果 " + success);
            return success;
        } catch (IOException e) {
            log.error("ftps 文件删除失败", e);
        } finally {
            if (!ftpsClient.isConnected()) {
                try {
                    ftpsClient.completePendingCommand();
                    ftpsClient.disconnect();
                } catch (IOException e) {
                    log.error("ftps 文件删除失败", e);
                }
            }
        }
        return false;
    }


}
