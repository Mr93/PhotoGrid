package com.example.dendimon.photogrid;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.nio.channels.ScatteringByteChannel;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static int Result_load_img = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadGallery (View view){
       // Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       // Log.d(TAG, "String intent" + galleryIntent.getData().toString());
       // startActivityForResult(galleryIntent, Result_load_img);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Result_load_img);
    }

    @Override
    protected void onActivityResult (int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == Result_load_img && resultCode == RESULT_OK && data != null) {

            //    Uri selectedImg = data.getData();
          //      String[] filepathcolumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getContentResolver().query(selectedImg, filepathcolumn, null, null, null);

              //  cursor.moveToFirst();

               // int columnIndex = cursor.getColumnIndex(filepathcolumn[0]);

               // imgDecodableString = cursor.getString(columnIndex);

               // cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.imgView);
              //  imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));


                        Uri uri = data.getData();
                imageView.setImageURI(uri);


            } else {
                Toast.makeText(this, "You haven't picked any image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG).show();
        }
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
}
