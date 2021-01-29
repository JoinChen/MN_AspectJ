package com.bksx.mn_aspectj.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bksx.mn_aspectj.annotation.PermissionFailed;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author JoneChen
 * @Date 2021\1\28 0028-17:11
 */
public class PermisionUtils {
    /***
     * 判断所有权限是否都给了 如果有一个没给就返回false
     * @param context
     * @param permissons
     * @return
     */
    public static boolean hasPermissionRequest(Context context,String... permissons){
        for (String permission :
                permissons) {
            if (ContextCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    /***
     * 用户永久拒绝了权限
     * @param activity
     * @param permissions
     */
    public static boolean shouldShowRequestPermissionRationale(Activity activity,String... permissions){
        //点击不再提示
        for (String permission:
            permissions ) {
            //当ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)为false时 返回true
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
                return true;
            }
        }
        return false;
    }

    /***
     *
     * @param ganteResult 申请权限后返回的码 没成功-1
     * @return
     */
    public static boolean requestPermissionSuccess(int... ganteResult){
        if (ganteResult == null || ganteResult.length <= 0){
            return false;
        }
        for (int permissionValue : ganteResult
             ) {
            if (permissionValue != PackageManager.PERMISSION_GRANTED){//其中有一个不为0
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param object
     * @param annotationClass
     */
    public static void invokeAnnotation(Object object,Class annotationClass){
        Class<?> objectClass = object.getClass();
        Method[] declaredMethods = objectClass.getDeclaredMethods();
        for (Method method :
                declaredMethods) {
            method.setAccessible(true);
            //判断是否有被annotation注解的方法 @PermissionFailed()等
            boolean annotationPresent = method.isAnnotationPresent(annotationClass);
            if (annotationPresent){
                try {
                    method.invoke(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
