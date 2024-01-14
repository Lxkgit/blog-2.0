package com.blog.file.utils;

import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/12 14:00
 */

@Conditional({AppContextHelperCondition.class})
@Order(0)
@Component
public class AppContextHelper extends AppContext {
    public AppContextHelper() {
    }
}

