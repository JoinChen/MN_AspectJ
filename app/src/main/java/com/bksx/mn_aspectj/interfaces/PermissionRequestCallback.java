package com.bksx.mn_aspectj.interfaces;

/**
 * @Author JoneChen
 * @Date 2021\1\28 0028-16:47
 */
public interface PermissionRequestCallback {
    //框架层回调结果回调给开发者的接口回调对象

    /***
     * 申请回调成功
     */
    void permissionSuccess();

    /***
     * 申请回调失败，点击拒绝
     */
    void permissionCancled();

    /***
     * 申请权限失败，点击不再询问
     */
    void permissionDenied();
}
