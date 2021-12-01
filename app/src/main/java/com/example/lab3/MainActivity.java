     package com.example.lab3;

import android.Manifest;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

     public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv);

    }

    public void showContact(View view){
        if(ContextCompat.checkSelfPermission(this , Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED){

            Uri uri = Uri.parse("content://contacts/people");
             list = new ArrayList<>();
            CursorLoader cursorLoader = new CursorLoader(this , uri , null , null , null , null);
            Cursor cursor = cursorLoader.loadInBackground();
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    String infor = "";
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    infor = id + "--" +name;
                    list.add(infor);
                    cursor.moveToNext();
                }
                cursor.close();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , list);
                listView.setAdapter(adapter);
            }

        }else{
            ActivityCompat.requestPermissions(this ,
                    new String[]{Manifest.permission.READ_CONTACTS} , 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000){
            if(ContextCompat.checkSelfPermission(this ,
                    Manifest.permission.READ_CONTACTS) ==
                    PackageManager.PERMISSION_GRANTED){

            }else{
                Toast.makeText(this , "Khong The Truy Cap Neu Ban Khong Cap Quyen" , Toast.LENGTH_SHORT).show();
            }
        }
    }

   public void cActi(View view){
        Intent intent = new Intent(MainActivity.this , MainActivity2.class);
        startActivity(intent);
   }

}