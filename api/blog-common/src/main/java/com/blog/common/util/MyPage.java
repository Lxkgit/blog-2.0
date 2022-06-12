package com.blog.common.util;

import java.util.List;

/**
 * @author: lxk
 * @time: 2021/10/12 20:24
 * @description :分页实体类
 */

public class MyPage<T> {
    private int page;
    private int size;
    private int total;
    private List<T> list;

    public MyPage(List<T> list, int pageSize, int pageNum, int total) {
        this.list = list;
        this.size = pageNum;
        this.page = pageSize;
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
