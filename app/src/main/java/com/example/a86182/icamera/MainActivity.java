package com.example.a86182.icamera;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 *
 * main page
 * @author LB WZW CXW
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        EasyPermissions.PermissionCallbacks {

//    private ImageView ivTest;
    /** 拍照照片路径*/
    private File cameraSavePath;
    /** 照片路径*/
    public static Uri uri;
    /** 获取权限 */
    private String[] permissions =
            {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static String testString = "";

    /**
     *
     * @param uri
     */
    public MainActivity(final Uri uri)
    {
        super();
        this.uri = uri;
    }

    /**
     *
     */
    public MainActivity()
    {
        super();
    }

    /**
     * Instantiate some objects
     * @param bundle
     */
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        final ImageButton camera = findViewById(R.id.camera);
        final Button album = findViewById(R.id.album);
//        ivTest = findViewById(R.id.iv_test);
        gainPermission();
        camera.setOnClickListener(this);
        album.setOnClickListener(this);

        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/"
                + System.currentTimeMillis() + ".jpg");

    }

    /**
     * Judge click the button to perform different actions
     * @param view  Current click object
     */
    @Override
    public void onClick(final View view) {
        final int viewId = view.getId();
        switch (viewId) {
            case R.id.camera:
                goCamera();

                break;
            case R.id.album:
                goPhotoAlbum();
                break;

            default:
                break;
        }
    }

    //获取权限

    /**
     * Apply camera permissions to the user
     */
    private void gainPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }

    }


    //激活相册操作

    /**
     *Activate the album operation
     */
    private void goPhotoAlbum() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
        Toast.makeText(this, "album starts", Toast.LENGTH_SHORT).show();
    }

    //激活相机操作

    /**
     *Activate camera operation
     */
    private void goCamera() {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this,
                    "com.example.a86182.icamera.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        this.startActivityForResult(intent, 1);
        Toast.makeText(this, "camera starts", Toast.LENGTH_SHORT).show();
    }

    /**
     * Same as the parent method
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           final @NonNull String[] permissions,
                                           final @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    //成功打开权限

    /**
     * Same as the parent method
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(final int requestCode, final @NonNull List<String> perms) {

        Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }

    //用户未同意权限

    /**
     * Same as the parent method
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(final int requestCode, final @NonNull List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }

    /**
     * Same as the parent method
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.d("拍照返回图片路径:", photoPath);
            Intent in=new Intent(MainActivity.this,Phote_View_Activity.class);
            in.putExtra("Uri",uri.toString());
            in.putExtra("photopath",photoPath);
            startActivity(in);
//            Glide.with(MainActivity.this).load(photoPath).into(ivTest);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
//            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
//            Glide.with(MainActivity.this).load(photoPath).into(ivTest);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
