package com.a132room.kp0hyc.pocketmipt;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class MainMenuActivity extends AppCompatActivity {

    public static final int PHOTO_SELECT_RESULT = 1;
    public static final int READ_STORAGE_PERMISSION = 2;

    LinearLayout ll = null;
    OnImageClickedListener onImageClickedListener = new OnImageClickedListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSharedPreferences("com.a132room.kp0hyc.pocketmipt", MODE_PRIVATE).getBoolean(getString(R.string.first_boot_bool),
                                                                                            true))
        {
            Intent intent = new Intent(this, FirstBootActivity.class);
            //intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main_menu);

        ll = (LinearLayout) findViewById(R.id.imagesListLayout);
    }

    public void onReset(View v)
    {
        Log.d("kp0hyc_log", "On reset clicked");
        getSharedPreferences("com.a132room.kp0hyc.pocketmipt", MODE_PRIVATE).edit().remove(getString(R.string.first_boot_bool))
                .apply();
        Intent intent = new Intent(this, MainMenuActivity.class);
        //intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void showFiles(File f)
    {
        for (File file: f.listFiles())
        {
            if (file.isFile() && file.getName().endsWith(".jpg"))
            {
                Log.d("kp0hyc_log", file.getPath());
                TextView tv = new TextView(this);
                tv.setText(file.getPath());
                tv.setTag(file.getPath());
                ll.addView(tv);
                tv.setOnClickListener(onImageClickedListener);
            }
            else if(file.isDirectory())
                showFiles(file);
        }
    }

    public void onFindImagesClicked (View v)
    {
        if (ll.getChildCount() > 0)
            ll.removeAllViewsInLayout();
        //File f = Environment.getExternalStorageDirectory();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION);
                return;
            }

        }
        /*if (f.canRead())
            Log.d("kp0hyc_log", "can read");
        else
            Log.d("kp0hyc_log", "can not read");*/
        showFiles(Environment.getExternalStorageDirectory());


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    onFindImagesClicked(null);

                }
                return;
            }
        }
    }

    public void onSelectImageClicked(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), PHOTO_SELECT_RESULT);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_SELECT_RESULT) {
                Uri selectedImageUri = data.getData();
                Log.d("kp0hyc_log", selectedImageUri.getPath());
                Intent intent = new Intent(this, ImageViewActivity.class);
                intent.putExtra("image uri", data.getData().toString());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        Log.d("kp0hyc_log", "back pressed");
    }

    public class OnImageClickedListener implements View.OnClickListener
    {

        @Override
        public void onClick(View view) {
            String s = (String) view.getTag();
            Log.d("kp0hyc_log", s);
            Intent intent = new Intent(view.getContext(), ImageViewActivity.class);
            intent.putExtra("full path", s);
            startActivity(intent);
        }
    }
}
