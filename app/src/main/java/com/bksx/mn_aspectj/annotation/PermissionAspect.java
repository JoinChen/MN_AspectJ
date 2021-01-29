package com.bksx.mn_aspectj.annotation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.bksx.mn_aspectj.activity.ApplyPermissionActivity;
import com.bksx.mn_aspectj.interfaces.PermissionRequestCallback;
import com.bksx.mn_aspectj.util.PermisionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author JoneChen
 * @Date 2021\1\28 0028-15:59
 * 织入类
 */
@Aspect
public class PermissionAspect {
    //切入点 @annotation(permission)获取注解的方法的参数
    @Pointcut("execution(@Permission * * (..)) && @annotation(permission)")//被注解标记了的 任何类型 的任何方法 任何参数
    public void getPermission(Permission permission) {
    }

    @Around("getPermission(permission)")//ProceedingJoinPoint只在Around中可以获取 after和before中不能获取
    public void getPermissionPoint(ProceedingJoinPoint proceedingJoinPoint, Permission permission) {

        //获取上下文
        Object aThis = proceedingJoinPoint.getThis();//获取到切入点所在的对象
        Context context = null;
        if (aThis instanceof Context) {
            context = (Context) aThis;
        } else if (aThis instanceof Fragment) {
            context = ((Fragment) aThis).getActivity();
        }

        if (context == null || permission == null || permission.value().length == 0) {
            return;
        }
        String[] permissions = permission.value();
        int requestCode = permission.requestCode();
        //获取到权限
        ApplyPermissionActivity.launchActivity(context, permissions, requestCode, new PermissionRequestCallback() {
            @Override
            public void permissionSuccess() {
                //权限通过后进行方法执行
                try {
                    proceedingJoinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void permissionCancled() {
                PermisionUtils.invokeAnnotation(aThis,PermissionFailed.class);
            }

            @Override
            public void permissionDenied() {
                PermisionUtils.invokeAnnotation(aThis,PermissionDenied.class);
            }
        });
    }
}
