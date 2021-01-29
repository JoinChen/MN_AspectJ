package com.bksx.mn_aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author JoneChen
 * @Date 2021\1\28 0028-17:42
 */

/**
 * 权限申请被永久拒绝的标记注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionDenied {
    /**
     * 请求码
     */
    int requestCode();
}
