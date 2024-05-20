package com.blog.file.utils;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @description:
 * @Author: lxk
 * @date 2024/1/12 14:01
 */

public class AppContextHelperCondition implements Condition {
    public AppContextHelperCondition() {
    }

    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !context.getRegistry().containsBeanDefinition("appContextHelper");
    }
}
