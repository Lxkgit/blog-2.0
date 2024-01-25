package com.blog.common.entity.content.diary.vo;

import com.blog.common.entity.content.diary.Diary;
import com.blog.common.valication.annotation.Equal;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.valication.group.DeleteGroup;
import com.blog.common.valication.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Author: lxk
 * @date 2022/12/28 17:26
 * @description:
 */

@Getter
@Setter
public class DiaryVo extends Diary {

    @Pattern(regexp = "^20[0-9]{2}-[0-9]{1,2}-[0-9]{1,2}$", message = "请输入正确的日记日期格式(yyyy-MM-dd)", groups = {AddGroup.class, UpdateGroup.class})
    private Date diaryDate;

    @Equal(value = "0,1", message = "日记状态（1：发布 0：草稿）字段错误", groups = {AddGroup.class, UpdateGroup.class})
    private Integer diaryStatus;

    @Pattern(regexp = "^[0-9]+(,[0-9]+)+|[0-9]+$", message = "请输入正确的日记id字符串", groups = {DeleteGroup.class})
    private String ids;

    private Integer pageSize;

    private Integer pageNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    @DateTimeFormat(pattern = "yyyy-MM")
    private String dateMonth;
}
