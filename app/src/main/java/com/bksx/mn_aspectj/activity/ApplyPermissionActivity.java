package com.bksx.mn_aspectj.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bksx.mn_aspectj.interfaces.PermissionRequestCallback;
import com.bksx.mn_aspectj.util.PermisionUtils;

/**
 * @Author JoneChen
 * @Date 2021\1\28 0028-16:28
 */
public class ApplyPermissionActivity extends AppCompatActivity {
    public static final String REQUEST_PERMISSONS = "request_permissions";
    public static final String REQUEST_CODE = "request_code";
    public static final int REQUEST_CODE_DEFAULT = -1;
    private static PermissionRequestCallback requestCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent!=null){
            String[] permissionArr = intent.getStringArrayExtra(REQUEST_PERMISSONS);
            int request_code = intent.getIntExtra(REQUEST_CODE,REQUEST_CODE_DEFAULT);
            if (permissionArr == null || request_code == -1 || requestCallback == null){
                this.finish();
                return;
            }
            //开始执行申请流程
            if (PermisionUtils.hasPermissionRequest(this,permissionArr)){
                //全部都是授予状态
                requestCallback.permissionSuccess();
                this.finish();
                return;
            }
            //执行申请权限
            ActivityCompat.requestPermissions(this,permissionArr,request_code);
        }else{
            finish();
        }
    }

    public static void launchActivity(Context context,String[]permissions,int requestCode,PermissionRequestCallback permissionRequestCallback){
        //跳转到ApplyPermissionActivity中
        requestCallback = permissionRequestCallback;
        Bundle bundle = new Bundle();
        bundle.putStringArray(REQUEST_PERMISSONS,permissions);
        bundle.putInt(REQUEST_CODE,requestCode);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(context,ApplyPermissionActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //判断所有是否都申请成功
        if (PermisionUtils.requestPermissionSuccess(grantResults)){
            requestCallback.permissionSuccess();
            this.finish();
            return;
        }

        //用户点击不再提示 返回true
        if (PermisionUtils.shouldShowRequestPermissionRationale(this,permissions)){
            requestCallback.permissionDenied();
            this.finish();
            return;
        }

        //用户点击了拒绝 但不是永久拒绝
        requestCallback.permissionCancled();
        this.finish();
        return;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
