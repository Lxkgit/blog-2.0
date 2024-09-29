package com.blog.common.entity.file.vo;

import com.blog.common.entity.file.SensorTemplate;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 传感器模板页面数据返回类
 * @Author lxk
 * @CreateTime 2024-09-29
 */

@Getter
@Setter
public class SensorTemplateVO extends SensorTemplate {

    /**
     * 校验表单数据
     */
    private String from;
}
