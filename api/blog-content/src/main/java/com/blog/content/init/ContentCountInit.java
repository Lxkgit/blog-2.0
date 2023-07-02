package com.blog.content.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @description: 博客内容统计初始化
 * @Author: 308501
 * @date 2023/6/29 11:06
 */

@Component
public class ContentCountInit implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initArticleCount();
        initDiaryCount();
        initDocCount();
    }

    /**
     * 初始化统计文章数量
     */
    private void initArticleCount() {

    }

    /**
     * 初始化日记统计数量
     */
    private void initDiaryCount() {

    }

    /**
     * 初始化文档统计数量
     */
    private void initDocCount() {

    }
}
