package com.blog.common.entity.file.vo;

import com.blog.common.entity.file.FileData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Pattern;

/**
 * @description:
 * @Author: lxk
 * @date 2023/8/2 16:08
 */


@Setter
@Getter
public class FileDataVo extends FileData {

    private boolean flag;

    @Pattern(message = "路径需要以/开始且只允许汉字、数字、字母、下划线", regexp = "^((/[a-zA-Z0-9_\\u4e00-\\u9fa5]+)+)|(/)$")
    private String filePath;

    // 文件/目录 新的名称
    private String rename;
}
