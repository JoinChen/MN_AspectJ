package com.bksx.mn_aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author JoneChen
 * @Date 2021\1\28 0028-15:52
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    //哪些权限s
    String[] value();
    //请求码
    int requestCode();
}
