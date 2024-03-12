package com.blog.pi.ftp;

import com.blog.pi.config.InitConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
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
public class FtpUtil {


    private FTPClient ftpClient;

    private boolean init() {
        ftpClient = new FTPClient();
        if (ftpClient.isConnected()) {
            return true;
        }
        log.info("开始连接ftp");
        int reply;
        try {
            String ftpIp = (String) InitConfig.getRegisterConfig("ftp", "ip");
            int ftpPort = (int) InitConfig.getRegisterConfig("ftp", "port");
            String ftpUsername = (String) InitConfig.getRegisterConfig("ftp", "username");
            String ftpPassword = (String) InitConfig.getRegisterConfig("ftp", "password");
            ftpClient.setConnectTimeout(5000);
            ftpClient.connect(ftpIp, ftpPort);
            ftpClient.login(ftpUsername, ftpPassword);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            } else {
                log.info("ftp 连接成功");
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
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
            log.info("ftp 当前目录 : {}", dir);
            if (!ftpClient.changeWorkingDirectory(dir)) {
                ftpClient.makeDirectory(dir);
                ftpClient.changeWorkingDirectory(dir);
            }
        }
    }

    /**
     * 上传文件到ftp服务器
     *
     * @param sourceFilePath 源文件位置
     * @param sourceFileName 源文件名称
     * @param targetFilePath 服务器文件路径
     * @param targetFileName 服务器文件名称
     * @return 文件上传是否成功
     */
    public boolean uploadFtpFile(String sourceFilePath, String sourceFileName, String targetFilePath, String targetFileName) {
        log.info("ftp 上传文件  sourcePath: {}, sourceFileName: {}, targetName: {}, targetFileName: {}", sourceFilePath, sourceFileName, targetFilePath, targetFileName);
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
            boolean success = ftpClient.storeFile(fn, inputStream);
            log.info("ftp 文件上传结果 " + success);
            return success;
        } catch (Exception e) {
            log.error("ftp 文件上传失败", e);
        } finally {
            if (!ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("ftp 文件上传失败", e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("ftp 文件上传失败", e);
                }
            }
        }
        return false;
    }



    /**
     * 下载服务器文件
     * @param serviceFilePath 服务器文件目录
     * @param serviceFileName 服务器文件名称
     * @param localFilePath 本地存放文件相对目录
     * @param localFileName 本地存放文件名称
     * @return 下载结果
     */
    public boolean downloadFtpFile(String serviceFilePath, String serviceFileName, String localFilePath, String localFileName) {
        log.info("ftp 下载文件  pathName: {}, fileName: {}", serviceFilePath, serviceFileName);
        init();
        try {
            ftpClient.changeWorkingDirectory(new String(serviceFilePath.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            File localFile = new File( localFilePath + File.separatorChar + localFileName);
            OutputStream os = new FileOutputStream(localFile);
            boolean success = ftpClient.retrieveFile(new String(serviceFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1), os);
            os.close();
            log.info("ftp 文件下载结果 " + success);
            return success;
        } catch (IOException e) {
            log.error("ftp 文件下载失败", e);
        } finally {
            if (!ftpClient.isConnected()) {
                try {
                    ftpClient.completePendingCommand();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("ftp 文件下载失败", e);
                }
            }
        }
        return false;
    }


    /**
     * 删除ftp服务器文件
     *
     * @param pathName 文件目录
     * @param fileName 文件名称
     * @return 删除结果
     */
    public boolean removeFile(String pathName, String fileName) {
        log.info("ftp 删除文件  pathName: {}, fileName: {}", pathName, fileName);
        init();
        try {
            ftpClient.changeWorkingDirectory(new String(pathName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            boolean success = ftpClient.deleteFile(new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            log.info("ftp 文件删除结果 " + success);
            return success;
        } catch (IOException e) {
            log.error("ftp 文件删除失败", e);
        } finally {
            if (!ftpClient.isConnected()) {
                try {
                    ftpClient.completePendingCommand();
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("ftp 文件删除失败", e);
                }
            }
        }
        return false;
    }


}
