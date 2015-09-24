package com.example.dendimon.photogrid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.nio.channels.ScatteringByteChannel;


public class MainActivity extends AppCompatActivity {


    protected Cursor mCursor;
    protected int columnIndex;
    protected GridView mGridView;
    protected ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get all the images on phone
        String[] projection = {
                MediaStore.Images.Thumbnails._ID,
                MediaStore.Images.Thumbnails.IMAGE_ID

        };

        //MediaStore.getMediaScannerUri()
       // Log.d("Path", MediaStore.Images.Thumbnails.);
        mCursor = getContentResolver().query(MediaStore.getMediaScannerUri(), projection, null, null, MediaStore.Images.Thumbnails.IMAGE_ID + " DESC");

        columnIndex = mCursor.getColumnIndexOrThrow(projection[0]);

        //Get the gridview layout
        mGridView = (GridView)findViewById(R.id.gridView);
        mAdapter = new ImageAdapter(this);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                Toast.makeText(MainActivity.this,"Selected Position: "+position,Toast.LENGTH_SHORT).show();
            }
        });
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

    class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mCursor.getCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        // Convert DP to PX
        // Source: http://stackoverflow.com/a/8490361
        public int dpToPx(int dps) {
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (dps * scale + 0.5f);

            return pixels;
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent){
            ImageView imageView;
            int imageID = 0;

            int wPixel = dpToPx(120);
            int hPixel = dpToPx(120);

            //move cursor to current position
            mCursor.moveToPosition(position);
            //Get the current value for the requested column
            imageID = mCursor.getInt(columnIndex);

            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.image_item,null);
            }else{

            }

            imageView = (ImageView) convertView.findViewById(R.id.imageView);

            imageView.setLayoutParams(new LinearLayout.LayoutParams(wPixel,hPixel));

            //set the content of the image based on the provided URI
            imageView.setImageURI(Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,"" + imageID));

            //image should be cropped towards the center
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            //set padding for images
            imageView.setPadding(8,8,8,8);

            //crop the image to fit within its padding
            imageView.setCropToPadding(true);

            return convertView;
        }


    }



}


