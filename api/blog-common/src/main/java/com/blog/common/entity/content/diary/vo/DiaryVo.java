package com.blog.common.entity.content.diary.vo;

import com.blog.common.entity.content.diary.Diary;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: lxk
 * @date 2022/12/28 17:26
 * @description:
 */

public class DiaryVo extends Diary {

    private Integer pageSize;

    private Integer pageNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    @DateTimeFormat(pattern = "yyyy-MM")
    private String dateMonth;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }
}
