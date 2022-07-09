package com.blog.common.entity.file;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @Author: lxk
 * @date 2022/7/8 14:33
 * @description:
 */

@Data
@ExcelIgnoreUnannotated //忽视无注解的属性
@ContentRowHeight(20) //文本高度
@HeadRowHeight(20) //标题高度
//设置标题居中
//@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND,horizontalAlignment= HorizontalAlignment.CENTER)
// 头背景设置成红色 IndexedColors.RED.getIndex()
//@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 10)
// 头字体设置成20
//@HeadFontStyle(fontHeightInPoints = 20)
// 内容的背景设置成绿色 IndexedColors.GREEN.getIndex()
//@ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 17)
public class UserInfo {


    @ColumnWidth(20) //列宽
    @ExcelProperty(value = {"用户信息","姓名"},index = 0) //value：第一个值是标题,第二个是字段行名称,index表示哪列位置
    private String name;

    @ColumnWidth(20) //列宽
    @ExcelProperty(value = {"用户信息","性别"},index = 1) //value：第一个值是标题,第二个是字段行名称,index表示哪列位置
    private String sex;

    @ColumnWidth(20) //列宽
    @ExcelProperty(value = {"用户信息","年龄"},index = 2) //value：第一个值是标题,第二个是字段行名称,index表示哪列位置
    private Integer age;

    @ColumnWidth(20) //列宽
    @ExcelProperty(value = {"用户信息","身高"},index = 3) //value：第一个值是标题,第二个是字段行名称,index表示哪列位置
    private String height;


    //@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 10)//列背景颜色红色
    //@ContentFontStyle(fontHeightInPoints = 11,color = 10)//内容颜色为红色
    @ColumnWidth(20) //列宽
    @ExcelProperty(value = {"用户信息","爱好"},index = 4) //value：第一个值是标题,第二个是字段行名称,index表示哪列位置
    private String hobby;

    public UserInfo(String name, String sex, Integer age, String height, String hobby) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.hobby = hobby;
    }
}
