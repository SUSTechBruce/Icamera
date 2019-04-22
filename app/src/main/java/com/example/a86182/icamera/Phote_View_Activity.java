package com.example.a86182.icamera;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Phote_View_Activity extends AppCompatActivity {
    Button share;
    Button edit_2;
    ImageView photo;
    Uri uri;
    /** get button obj */
//    private Button share;
    /** use to judge app in phone or not */
    private PlatformUtil platForMutil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);
        getSupportActionBar().hide();
        init();

        Bundle bundle=getIntent().getExtras();
        String photopath=bundle.getString("photopath");
        uri = Uri.parse(bundle.getString("Uri"));
        Glide.with(Phote_View_Activity.this).load(photopath).into(photo);
        set_listener();

    }
    public void init(){
        platForMutil =  new PlatformUtil();

        photo=findViewById(R.id.photo);
        edit_2 = (Button) findViewById(R.id.edit_2);
        Drawable edit_drawable = getDrawable(R.drawable.edit_2);
        edit_drawable.setBounds(0,0, 150,150);
        edit_2.setCompoundDrawables(null,edit_drawable, null, null);

        share = (Button) findViewById(R.id.share);
        Drawable share_drawable = getDrawable(R.drawable.share);
        share_drawable.setBounds(0,0, 100,150);
        share.setCompoundDrawables(null,null, share_drawable, null);
    }
    public void set_listener(){
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                try {

                    final Context context = getApplicationContext();
                    final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    shareImageToQQ(context,bitmap);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage(),e);
                }

            }
        });
    }


    /**
     *  share to qq
     * @param mcontext The current context
     * @param bitmap the image
     */
    public void shareImageToQQ(final Context mcontext,final Bitmap bitmap) {

        if (platForMutil.judgeInstalled(mcontext,PlatformUtil.PACKAGE_MOBILE_QQ)) {
            try {
                final Uri uriToImage = Uri.parse(MediaStore.Images.Media.insertImage(
                        mcontext.getContentResolver(), bitmap, null, null));
                final Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("image/*");
                // 遍历所有支持发送图片的应用。找到需要的应用
                final ComponentName componentName =
                        new ComponentName("com.tencent.mobileqq",
                                "com.tencent.mobileqq.activity.JumpActivity");
                shareIntent.setComponent(componentName);
                // mContext.startActivity(shareIntent);
                mcontext.startActivity(Intent.createChooser(shareIntent, "Share"));
            } catch (Exception e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
    }
}
