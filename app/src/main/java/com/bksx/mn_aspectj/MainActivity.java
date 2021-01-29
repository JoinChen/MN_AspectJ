package com.bksx.mn_aspectj;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bksx.mn_aspectj.annotation.Permission;
import com.bksx.mn_aspectj.annotation.PermissionDenied;
import com.bksx.mn_aspectj.annotation.PermissionFailed;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initPermissions();
    }

    @Permission(value = Manifest.permission.READ_EXTERNAL_STORAGE, requestCode = 1)
    public void readSDCard() {
        //具体读写操作
        Log.e("MN-------", "222222222222");
    }

    @PermissionFailed(requestCode = 1)
    private void requestPermissionFailed() {
        Toast.makeText(this, "用户拒绝了权限", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(requestCode = 1)
    private void requestPermissionDenied() {
        Toast.makeText(this, "权限申请失败，不再询问", Toast.LENGTH_SHORT).show();
        Uri packageUri = Uri.parse("package:" + "com.bksx.mn_aspectj");
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri);
        startActivity(intent);
    }


    public void click2getsdCard(View view) {
        readSDCard();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Permission(value = Manifest.permission.ACCESS_COARSE_LOCATION, requestCode = 1)
    public void goSet(View view) {
//        Uri packageUri = Uri.parse("package:" + "com.bksx.mn_aspectj");
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri);
//        startActivity(intent);
    }

    /*//调用此方法判断是否拥有权限
    private void initPermissions() {
        //判断是否已经拥有权限
        int i = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //判断是否永久拒绝了权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    //权限申请被永久拒绝
                    Uri packageURI = Uri.parse("package:" + "com.bksx.mn_aspectj");
                    Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageURI);
                    startActivity(intent);
                }else{
                    //请求权限，第二参数权限String数据，第三个参数是请求码便于在onRequestPermissionsResult 方法中根据code进行判断
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                }
            }else{

            }
        } else {
            //拥有权限执行操作
            Toast.makeText(MainActivity.this,"已经拥有权限",Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){//响应Code
            case 1:
                if (grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"未拥有相应权限",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    //拥有权限执行操作
                } else {
                    Toast.makeText(this,"未拥有相应权限",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }*/
}