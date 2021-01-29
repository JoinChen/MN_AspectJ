package com.bksx.mn_aspectj.annotation;

/**
 * @Author JoneChen
 * @Date 2021\1\28 0028-17:39
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 权限申请被拒绝的标记注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionFailed {
    /**
     * 请求码
     */
    int requestCode();
}
