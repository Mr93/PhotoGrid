package com.example.dendimon.photogrid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.ScatteringByteChannel;
import java.security.PublicKey;


public class MainActivity extends AppCompatActivity {

    private static Uri[] mUrls = null;
    private static String[] strUrls=null;
    private String[] mNames = null;
    private boolean[] mSlect = null;
    private GridView gridView = null;
    private Cursor cc = null;
    private int max = 0;

    private ProgressDialog myProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //It have to be matched with the directory in SDCard
        cc = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        //file[] files = f.listfiles();

        if (cc != null) {
            myProgressDialog = new ProgressDialog(MainActivity.this);
            myProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            myProgressDialog.setMessage(getResources().getString(R.string.pls_wait_txt));
            myProgressDialog.show();

            new Thread() {
                public void run() {
                    try {
                        cc.moveToFirst();
                        mUrls = new Uri[cc.getCount()];
                        strUrls = new String[cc.getCount()];
                        mNames = new String[cc.getCount()];
                        mSlect = new boolean[cc.getCount()];
                        for (int i = 0; i < cc.getCount(); i++) {
                            cc.moveToPosition(i);
                            mUrls[i] = Uri.parse(cc.getString(1));
                            strUrls[i] = cc.getString(1);
                            mNames[i] = cc.getString(3);
                            mSlect[i] = false;
                            Log.d("Select", mSlect.toString());
                        }
                    } catch (Exception e) {

                    }
                    myProgressDialog.dismiss();
                }
            }.start();

            gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(new ImageAdapter1(this));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    if(mSlect[position]){
                        mSlect[position]=false;
                        gridView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                        max=max-1;

                    }else{
                        if(max <2) {
                            mSlect[position] = true;
                            gridView.getChildAt(position).setBackgroundColor(Color.BLUE);
                            max++;
                        }else {
                            Toast.makeText(MainActivity.this,"Max picture picked = 2",Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });

        }

        Button btn = (Button)findViewById(R.id.btnNext);



    }

    public void importImages (View view){
        String[] String_uri = new String[cc.getCount()];

        for(int i =0;i<cc.getCount();i++){
            String_uri[i]=mUrls[i].toString();
        }
        Bundle b = new Bundle();
        b.putStringArray("URI", String_uri);
        b.putStringArray("URL", strUrls);
        b.putStringArray("NAME", mNames);
        b.putBooleanArray("SELECT", mSlect);
        Intent intent = new Intent(this,CustomLayout_2.class);
        intent.putExtras(b);
        intent.putExtra("MAX",cc.getCount());
        startActivity(intent);


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ImageAdapter1 extends BaseAdapter {

        private Context mContext;

        public ImageAdapter1(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return cc.getCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }



        @Override
        public View getView (int position, View convertView, ViewGroup parent){
           View v = convertView;
            LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.image_item,null);
            try{


                ImageView imageView = (ImageView) v.findViewById(R.id.ImageView01);
                Bitmap bmp = decodeURI(mUrls[position].getPath());
                //Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),mUrls[position]); //MediaStrore.Images.Media.getBitmap(c.getContentResolver() , Uri.parse(paths));
                imageView.setImageBitmap(bmp);



            }catch (Exception e){

            }
            return v;


        }

    }


    @Override
    protected void onStart(){
        super.onStart();
    //    FlurryAgent.onStartSession(this,"***");
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


