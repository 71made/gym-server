package com.gym.utils.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 71made
 * @Date: 2023/05/04 21:01
 * @ProductName: IntelliJ IDEA
 * @Description: 用于 controller 类/方法中, 表示需要 token 验证
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyToken {
    /**
     * 是否开启验证
     * @return 默认为开启, 返回 true
     */
    boolean required() default true;
}
