package com.example.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private ListView danhsach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        danhsach = findViewById(R.id.lvlist);

    }

    public void ShowImg(View view){
        if(ContextCompat.checkSelfPermission(MainActivity2.this ,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity2.this ,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , 999);
        }
        List<String> ds = new ArrayList<>();
        String[] projection = {
                MediaStore.Images.ImageColumns._ID ,
                MediaStore.Images.ImageColumns.DISPLAY_NAME ,
                MediaStore.Images.ImageColumns.DATA,
        };

        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI ,
                projection , null , null , null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                ds.add(data);
                cursor.moveToNext();
            }
            cursor.close();
            Log.d("anh", "so luong anh: " + ds.size());
        }

        ImageAdapter imageAdapter = new ImageAdapter(MainActivity2.this , ds);
        danhsach.setAdapter(imageAdapter);
    }

}