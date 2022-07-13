package com.blog.file.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.blog.common.entity.file.UserInfo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.file.service.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: lxk
 * @date 2022/7/7 15:42
 * @description: 文件上传接口服务
 */

@Slf4j
@RestController
@RequestMapping("/files")
public class UploadFileController {

    @Autowired
    private UploadFileService fileUploadService;

    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile[] files, String type) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        if (files == null || files.length == 0) {
            return ResultFactory.buildFailResult("文件上传失败 ... ");
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            return fileUploadService.uploadFileList(files, type, blogUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultFactory.buildFailResult("文件上传失败 ... ");
    }

    //使用easyExcel导出
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void easyExcelExport(HttpServletResponse response) {
        String fileName;
        try {
//            //获取需要导出的数据
            List<UserInfo> dataList = getData();
            fileName = new String("test".getBytes(), StandardCharsets.UTF_8);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
            // 表一写入
            ExcelWriter writer = EasyExcel.write(response.getOutputStream(), UserInfo.class).build();
            WriteSheet sheet = EasyExcel.writerSheet(0, "基础信息").build();
            writer.write(dataList, sheet);

            // 表二写入
            WriteSheet sheet2 = EasyExcel.writerSheet(1, "详细信息").head(UserInfo.class).build();
            writer.write(dataList, sheet2);
            // 关闭流
            writer.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //设置数据
    private List<UserInfo> getData() {
        List<UserInfo> list = new ArrayList<>();
        list.add(new UserInfo("张三", "男", 18, "189cm", "唱歌"));
        list.add(new UserInfo("李四", "女", 16, "160cm", "跳舞"));
        return list;
    }
}
