package com.blog.file.controller;

import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.file.vo.ImportDiaryVo;
import com.blog.common.entity.file.vo.UploadVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.enums.file.FilePathEnum;
import com.blog.common.enums.file.FileTypeEnum;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.file.service.ImportService;
import com.blog.file.service.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lxk
 * @date 2022/7/7 15:42
 * @description: 文件上传接口服务
 */

@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadFileController {

    @Resource
    private UploadFileService fileUploadService;

    @Resource
    private ImportService importService;

    @PostMapping
    public Result uploadFile(@RequestHeader HttpHeaders headers,@Validated UploadVo uploadVo) {
        if (uploadVo.getFiles() == null || uploadVo.getFiles().length == 0) {
            log.info(ErrorMessage.FILE_SIZE_NULL.getDesc());
            return ResultFactory.buildFailResult(ErrorMessage.FILE_SIZE_NULL.getDesc());
        }
        String filePath = FilePathEnum.getFilePathByCode(uploadVo.getFilePathCode());
        if (filePath == null) {
            return ResultFactory.buildFailResult(ErrorMessage.FILE_PATH_ERROR.getDesc());
        }
        List<String> typeList = FileTypeEnum.getTypeListByTypeName(uploadVo.getFileTypeCode());
        if (typeList == null) {
            return ResultFactory.buildFailResult(ErrorMessage.FILE_TYPE_ERROR.getDesc());
        }
        for (MultipartFile file : uploadVo.getFiles()) {
            String fileName = file.getName();
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
            if (typeList.contains(fileSuffix)) {
                return ResultFactory.buildFailResult(ErrorMessage.FILE_TYPE_ERROR_SUFFIX.getDesc());
            }
        }

        String token = String.valueOf(headers.get("Authorization"));
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            if (uploadVo.getFilePathCode().equals(FilePathEnum.USER_PATH.getFilePathCode())) {
                filePath = filePath + "/" + blogUser.getId() + uploadVo.getAddPath();
            } else {
                filePath = filePath + FileTypeEnum.getTypePathByTypeName(uploadVo.getFileTypeCode());
            }
            return fileUploadService.upload(uploadVo.getFiles(), blogUser.getId(), filePath);
        } catch (Exception e) {
            log.error(ErrorMessage.FILE_UPLOAD_ERROR.getDesc(), e);
        }
        return ResultFactory.buildFailResult(ErrorMessage.FILE_UPLOAD_ERROR.getDesc());
    }

    @PostMapping("/diary/import")
    public Result importDiary(@RequestHeader HttpHeaders headers,@RequestBody ImportDiaryVo importDiaryVo) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        importDiaryVo.setUserId(blogUser.getId());
        if (importService.importDiary(importDiaryVo)){
            return ResultFactory.buildSuccessResult();
        } else {
            return ResultFactory.buildFailResult("部分日记上传失败");
        }
    }

//    @GetMapping("/export")
//    public void exportImg(HttpServletResponse response) throws IOException {
//        List<String> strings = new ArrayList<>();
//        String str1 = "http://172.25.238.129:9876/65fbf56f-1930-11ed-b10f-d094663eb298/20220929/1/b66ee81d-3fa1-11ed-9910-d094663eb298.png";
//        String str2 = "http://172.25.238.129:9876/65fbf56f-1930-11ed-b10f-d094663eb298/20220929/1/b66ee81d-3fa1-11ed-9910-d094663eb298.png";
//        strings.add(str1);
//        strings.add(str2);
//        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
//        //设置返回响应头
//        response.reset();
//        // 自动判断下载文件类型
//        response.setContentType("multipart/form-data");
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("picture.zip", "UTF-8"));
//        try {
//            for (int i=0; i<strings.size(); i++) {
//                URL url = new URL(strings.get(i));
//                //打开链接
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                //设置请求方式为"GET"
//                conn.setRequestMethod("GET");
//                //超时响应时间为5秒
//                conn.setConnectTimeout(5 * 1000);
//                //通过输入流获取图片数据
//                InputStream inStream = conn.getInputStream();
//                //得到图片的二进制数据，以二进制封装得到数据，具有通用性
//                byte[] data = readInputStream(inStream);
//                //重点开始，创建压缩文件
//                ZipEntry zipEntry = new ZipEntry( i + ".png");
//                zipOutputStream.putNextEntry(zipEntry);
//                zipOutputStream.write(data);
//                inStream.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                zipOutputStream.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 得到图片的二进制数据，以二进制封装得到数据，具有通用性
//     *
//     * @param inStream
//     * @return
//     * @throws Exception
//     */
//    private byte[] readInputStream(InputStream inStream) throws Exception {
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        //创建一个Buffer字符串
//        byte[] buffer = new byte[1024];
//        //每次读取的字符串长度，如果为-1，代表全部读取完毕
//        int len = 0;
//        //使用一个输入流从buffer里把数据读取出来
//        while ((len = inStream.read(buffer)) != -1) {
//            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
//            outStream.write(buffer, 0, len);
//        }
//        //关闭输入流
//        inStream.close();
//        //把outStream里的数据写入内存
//        return outStream.toByteArray();
//    }
//
//    //使用easyExcel导出
//    @RequestMapping(value = "/download", method = RequestMethod.GET)
//    public void easyExcelExport(HttpServletResponse response) {
//        String fileName;
//        try {
////            //获取需要导出的数据
//            List<UserInfo> dataList = getData();
//            fileName = new String("test".getBytes(), StandardCharsets.UTF_8);
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
//            // 表一写入
//            ExcelWriter writer = EasyExcel.write(response.getOutputStream(), UserInfo.class).build();
//            WriteSheet sheet = EasyExcel.writerSheet(0, "基础信息").build();
//            writer.write(dataList, sheet);
//
//            // 表二写入
//            WriteSheet sheet2 = EasyExcel.writerSheet(1, "详细信息").head(UserInfo.class).build();
//            writer.write(dataList, sheet2);
//            // 关闭流
//            writer.finish();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //设置数据
//    private List<UserInfo> getData() {
//        List<UserInfo> list = new ArrayList<>();
//        list.add(new UserInfo("张三", "男", 18, "189cm", "唱歌"));
//        list.add(new UserInfo("李四", "女", 16, "160cm", "跳舞"));
//        return list;
//    }
//
//    @GetMapping("/getImg/{filename}")
//    public void getImg(HttpServletResponse response, @PathVariable("filename") String filename){
//        log.info("/getImg->访问图片->开始" );
//
//        String rootPath = "D:/img";
//        String filePath = rootPath + "/" + filename;
//        File imageFile = new File(filePath);
//        if (imageFile.exists()){
//            FileInputStream fis = null;
//            OutputStream os = null;
//            try {
//                fis = new FileInputStream(imageFile);
//                os = response.getOutputStream();
//                int count = 0;
//                byte[] buffer = new byte[1024 * 8];
//                while((count = fis.read(buffer)) != -1){
//                    os.write(buffer,0,count);
//                    os.flush();
//                }
//                log.info("[图片接口]输出完成");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }finally {
//                if(fis != null){
//                    try {
//                        fis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(os != null){
//                    try {
//                        os.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//
//                    }
//                }
//
//            }
//
//        }else{
//
//        }
//    }

}
