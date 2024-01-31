package com.blog.common.entity.file.vo;

import com.blog.common.entity.file.Chip;
import com.blog.common.valication.annotation.Equal;
import com.blog.common.valication.group.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * @description: 单片机表Vo类
 * @Author: 308501
 * @date 2024/1/31 11:38
 */

@Getter
@Setter
public class ChipVo extends Chip {

    /**
     * 单片机id
     */
    @NotNull(message = "文章id不为空", groups = {UpdateGroup.class, SelectIdGroup.class})
    @Min(value = 1, message = "id值最小为1", groups = {UpdateGroup.class, SelectIdGroup.class})
    private Integer id;

    /**
     * 单片机名称
     */
    @Size(min = 1, max = 50, message = "设备部署位置长度范围是1-50个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String chipName;

    /**
     * 设备编码
     */
    @NotNull(message = "单片机编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @NotEmpty(message = "单片机编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[a-zA-Z0-9-_]{1,32}$", message = "单片机编码只允许输入最长32位字母数字下划线和'-'", groups = {AddGroup.class, UpdateGroup.class})
    private String chipCode;

    /**
     * 单片机类型
     */
    @Size(min = 1, max = 50, message = "单片机类型长度范围是1-50个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String chipType;

    /**
     * 备注信息
     */
    @Size(min = 1, max = 255, message = "备注信息长度范围是1-255个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String memo;

    /**
     * 设备id列表 多个id使用 , 间隔
     */
    @Pattern(regexp = "^[0-9]+(,[0-9]+)+|[0-9]+$", message = "请输入正确的设备id字符串", groups = {DeleteGroup.class})
    private String ids;

    /**
     * 页大小
     */
    @NotNull(message = "分页查询页大小不能为空", groups = {SelectListGroup.class})
    @Max(value = 100, message = "分页大小最大为100", groups = {SelectListGroup.class})
    @Min(value = 5, message = "分页大小最小为5", groups = {SelectListGroup.class})
    private Integer pageSize;

    /**
     * 页数
     */
    @NotNull(message = "分页查询页数不能为空", groups = {SelectListGroup.class})
    @Min(value = 1, message = "分页查询页数最小1", groups = {SelectListGroup.class})
    private Integer pageNum;
}
