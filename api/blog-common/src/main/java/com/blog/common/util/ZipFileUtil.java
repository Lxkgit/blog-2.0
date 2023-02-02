package com.blog.common.util;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;


public class ZipFileUtil {

    /**
     * 把一个目录打包到一个指定的zip文件中
     * @param dirPath 目录路径
     * @param zipPath zip文件路径
     */
    public static void compressFoldToZip(String dirPath,String zipPath) {
        compressFoldToZip(dirPath,zipPath,"");
    }

    /**
     *  把一个目录打包到一个指定的zip文件中
     * @param dirPath 被压缩文件目录
     * @param zipPath 压缩文件目录
     * @param entryPath 压缩内文件逻辑路径。如static/
     */
    public static void compressFoldToZip(String dirPath,String zipPath,String entryPath){
        if(!entryPath.endsWith(File.separator)&& StringUtils.isNotBlank(entryPath)){
            entryPath+=File.separator;
        }
        ZipArchiveOutputStream out = null;
        try {
            out = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(new File(zipPath))));
            out.setEncoding("UTF-8");
            compressFoldToZip(out,dirPath,entryPath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 把一个目录打包到一个指定的zip文件中
     * @param out
     * @param dirPath 目录路径
     * @param entryPath zip中文件的逻辑路径
     */
    private static void compressFoldToZip(ZipArchiveOutputStream out,String dirPath,String entryPath) {
        InputStream ins = null;
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length < 1) {
            return;
        }
        try {
            for (File file : files) {
                // 判断此文件是否是一个文件夹
                if (file.isDirectory()) {
                    if (Objects.requireNonNull(file.listFiles()).length > 0) {
                        compressFoldToZip(out, file.getAbsolutePath(), entryPath + file.getName() + File.separator);
                    } else {
                        addFileToZip(file, out, entryPath);
                    }
                } else {
                    addFileToZip(file, out, entryPath);
                }
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ins);
        }
    }

    private static void addFileToZip(File file,ZipArchiveOutputStream out,String entryPath) {
        InputStream ins = null;
        try {

            String path=entryPath + file.getName();
            if(file.isDirectory()){
                path=formatDirPath(path); //为了在压缩文件中包含空文件夹
            }
            ZipArchiveEntry entry = new ZipArchiveEntry(path);
            entry.setTime(file.lastModified());
            // entry.setSize(files[i].length());
            out.putArchiveEntry(entry);
            if(!file.isDirectory()){
                ins = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
                IOUtils.copy(ins,out);
            }
            out.closeArchiveEntry();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ins);
        }
    }

    /**
     * 解压zip文件到指定目录
     *
     * @param zipPath
     * @param destDir
     */
    public static void unZipToFold(String zipPath,String destDir) {
        ZipArchiveInputStream ins = null;
        OutputStream os = null;
        File zip = new File(zipPath);
        if (!zip.exists()) {
            return;
        }
        File dest = new File(destDir);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        destDir=formatDirPath(destDir);
        try {
            ins = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipPath)),"UTF-8");
            ZipArchiveEntry entry = null;
            while ((entry = ins.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(destDir,entry.getName());
                    directory.mkdirs();
                    directory.setLastModified(entry.getTime());
                } else {
                    String absPath=formatPath(destDir+entry.getName());
                    mkdirsForFile(absPath);
                    File tmpFile=new File(absPath);
                    os=new BufferedOutputStream(new FileOutputStream(tmpFile));
                    IOUtils.copy(ins,os);
                    IOUtils.closeQuietly(os);
                    tmpFile.setLastModified(entry.getTime());
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ins);
        }
    }
    private static String formatPath(String path){
        path=path.replace('\\',File.separatorChar);
        path=path.replace('/',File.separatorChar);
        return path;
    }
    private static String formatDirPath(String dir){
        if(!dir.endsWith(File.separator)){
            dir+=File.separator;
        }
        return dir;
    }
    private static void mkdirsForFile(String filePath){
        String absPath=filePath;
        String tmpPath=absPath.substring(0,absPath.lastIndexOf(File.separator));
        File tmp=new File(tmpPath);
        if(!tmp.exists()){
            tmp.mkdirs();
        }
    }
    public static void main(String[] args) throws ParseException {
        long start = System.currentTimeMillis();

//        compressFoldToZip("D:\\abc2","D:\\abc2.zip");
        String descPath = "D:/file/file/diary/test";
        unZipToFold("D:/file/file/diary/2023-02-01_15-37-42_QVVXd_Desktop.zip",descPath);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
        File diaryDir = new File(descPath);
        File[] files = diaryDir.listFiles();

        if (files != null) {
            for (File file : files) {
                StringBuilder result = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
                    String s;
                    while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                        result.append(System.lineSeparator()).append(s);
                    }
                    br.close();
                    System.out.println("文件名称：" + file.getName());
                    System.out.println("文件最后修改时间：" + DateUtil.timeStampToDateTime(file.lastModified()));
                    System.out.println("文件内容:" + result.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



