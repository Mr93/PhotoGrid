package com.example.dendimon.photogrid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.net.URI;

public class CustomLayout_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_2);
      Bundle b =this.getIntent().getExtras();
        String[] cName = b.getStringArray("NAME");
        String[] cUrl = b.getStringArray("URL");
        boolean[] cSelect = b.getBooleanArray("SELECT");
        String[] cString_Uri = b.getStringArray("URI");
       Intent intent = getIntent();
        int cMax = intent.getIntExtra("MAX",0);
        String[] cString_Uri_1 = new String[2];
        for (int i = 0;i<cMax;i++){

        }




    }

}
