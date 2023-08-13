package com.blog.common.entity.file.vo;

import com.blog.common.entity.file.FileData;
import com.blog.common.entity.user.vo.SysPermissionVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @description:
 * @Author: lxk
 * @date 2023/8/2 16:08
 */


@Setter
@Getter
public class FileDataVo extends FileData implements Comparable<FileDataVo>{

    private boolean flag;

    @Pattern(message = "路径需要以/开始且只允许汉字、数字、字母、下划线", regexp = "^((/[a-zA-Z0-9_\\u4e00-\\u9fa5]+)+)|(/)$")
    private String filePath;

    // 文件/目录 新的名称
    private String rename;

    // 图片path
    private String imgPath;

    // 最近修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Override
    public int compareTo(FileDataVo fileDataVo) {
        return this.getType() - fileDataVo.getType();
        //所有比较最底层的逻辑都是发生两两比较逻辑的,返回比较结果
        //只关心结果结果三种:
        //正数:   this.age - o.age    >
        //负数:   this.age - o.age    <
        //0       this   ==
        //return this.age-o.age; 升序排序
        //return o.age-this.age; 降序排序
    }
}
