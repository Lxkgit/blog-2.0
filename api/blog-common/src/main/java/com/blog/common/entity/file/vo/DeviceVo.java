package com.blog.common.entity.file.vo;

import com.blog.common.entity.file.Device;
import com.blog.common.valication.annotation.Equal;
import com.blog.common.valication.group.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * @description: 设备
 * @Author: 308501
 * @date 2024/1/30 11:33
 */

@Getter
@Setter
public class DeviceVo extends Device {

    /**
     * 设备名称
     */
    @Size(min = 1, max = 50, message = "设备部署位置长度范围是1-50个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String deviceName;

    /**
     * 设备编码
     */
    @NotNull(message = "设备编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @NotEmpty(message = "设备编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[a-zA-Z0-9-_]{1,32}$", message = "设备编码只允许输入最长32位字母数字下划线和'-'", groups = {AddGroup.class, UpdateGroup.class})
    private String deviceCode;

    /**
     * 设备部署位置
     */
    @Size(min = 1, max = 50, message = "设备部署位置长度范围是1-50个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String devicePosition;

    /**
     * 设备在离线时间模板
     */
    @Min(value = 1, message = "设备在离线时间模板id值最小为1", groups = {AddGroup.class, UpdateGroup.class})
    private Integer timeTemplate;

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
}
