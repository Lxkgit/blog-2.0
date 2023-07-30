package com.blog.file.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.file.BlogSetting;
import com.blog.common.entity.file.vo.BlogSettingVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.file.dao.BlogSettingDAO;
import com.blog.file.service.BlogSettingService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description: 博客设置服务
 * @Author: 308501
 * @date 2023/7/25 17:27
 */

@Service
public class BlogSettingServiceImpl implements BlogSettingService {

    @Resource
    private BlogSettingDAO blogSettingDAO;

    @Override
    public List<BlogSettingVo> selectBlogSetting(BlogUser blogUser, String settingType) {
        QueryWrapper<BlogSetting> queryWrapper = new QueryWrapper<>();
        List<BlogSettingVo> blogSettingVoList = new ArrayList<>();
        queryWrapper.eq("setting_type", settingType);
        if (settingType.startsWith("1")) {
            queryWrapper.eq("user_id", blogUser.getId());
        }
        List<BlogSetting> blogSettingList = blogSettingDAO.selectList(queryWrapper);
        setBlogSettingVoList(blogSettingVoList, blogSettingList);
        return blogSettingVoList;
    }

    @Override
    public void updateBlogSetting(BlogUser blogUser, BlogSettingVo blogSettingVo) {
        blogSettingVo.setUserId(blogUser.getId());
        blogSettingVo.setSetting(JSON.toJSONString(blogSettingVo));
        blogSettingDAO.updateById(blogSettingVo);
    }

    private void setBlogSettingVoList(List<BlogSettingVo> blogSettingVoList, List<BlogSetting> blogSettingList) {
        for (BlogSetting blogSetting : blogSettingList) {
            BlogSettingVo blogSettingVo = new BlogSettingVo();
            BeanUtils.copyProperties(blogSetting, blogSettingVo);
            if (blogSetting.getType() != 0) {
                if (blogSetting.getSetting() != null) {
                    Map<String, Object> map = JSON.parseObject(blogSetting.getSetting());
                    if (blogSetting.getType() == 1) {
                        blogSettingVo.setValue((String) map.get("value"));
                    } else if (blogSetting.getType() == 2) {
                        blogSettingVo.setNum((Integer) map.get("num"));
                    } else if (blogSetting.getType() == 3) {
                        blogSettingVo.setBool((Boolean) map.get("bool"));
                    } else if (blogSetting.getType() == 4) {
                        blogSettingVo.setValue((String) map.get("value"));
                    } else if (blogSetting.getType() == 5) {
                        blogSettingVo.setNum(1);
                        blogSettingVo.setValueList((List<String>) map.get("valueList"));
                    } else if (blogSetting.getType() == 6) {
                        blogSettingVo.setNum((Integer) map.get("num"));
                        blogSettingVo.setValueList((List<String>) map.get("valueList"));
                    }
                }
            }
            blogSettingVoList.add(blogSettingVo);
        }
    }
}
