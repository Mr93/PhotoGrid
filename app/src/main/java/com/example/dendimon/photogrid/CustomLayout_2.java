package com.example.dendimon.photogrid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.net.URI;

public class CustomLayout_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_custom_layout_2);
        ImageView img_1 = (ImageView)findViewById(R.id.img_2_1_0_1);
        ImageView img_2 = (ImageView)findViewById(R.id.img_2_1_0_2);
      Bundle b =this.getIntent().getExtras();
     //   String[] cName = b.getStringArray("NAME");
      //  String[] cUrl = b.getStringArray("URL");
      //  boolean[] cSelect = b.getBooleanArray("SELECT");
        String[] cString_Uri = b.getStringArray("URI");
       Intent intent = getIntent();
        int cMax = intent.getIntExtra("MAX",0);
        String[] cString_Uri_1 = new String[2];
        for (int i = 0;i<cMax;i++){
            if(cString_Uri[i]!=null){
                Bitmap bmp = decodeURI(cString_Uri[i]);
                img_1.setImageBitmap(bmp);
                cString_Uri[i]=null;
                break;
            }
        }

        for (int i = 0;i<cMax;i++){
            if(cString_Uri[i]!=null){
                Bitmap bmp = decodeURI(cString_Uri[i]);
                img_2.setImageBitmap(bmp);
                cString_Uri[i]=null;
                break;
            }
        }





    }

    public Bitmap decodeURI  (String filePath){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Only scale if we need to
        // (16384 buffer for img processing)
        Boolean scaleByHeight = Math.abs(options.outHeight - 100) >= Math.abs(options.outWidth - 100);
        if(options.outHeight * options.outWidth * 2 >= 16384){
            // Load, scaling to smallest power of 2 that'll get it <= desired dimensions
            double sampleSize = scaleByHeight
                    ? options.outHeight / 200
                    : options.outWidth / 200;
            options.inSampleSize =
                    (int)Math.pow(2d, Math.floor(
                            Math.log(sampleSize)/Math.log(2d)));
        }

        // Do the actual decoding
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[512];
        Bitmap output = BitmapFactory.decodeFile(filePath, options);

        return output;
    }

}
