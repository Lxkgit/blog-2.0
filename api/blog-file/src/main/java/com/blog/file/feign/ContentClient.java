package com.blog.file.feign;

import com.blog.common.entity.content.diary.Diary;
import com.blog.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @Author: lxk
 * @date 2022/7/9 16:56
 * @description:
 */

@FeignClient("blog-content")
public interface ContentClient {

    @PostMapping("/content/diary/save/list")
    Result saveDiaryList(@RequestBody Map<Integer, Diary> map);

}
