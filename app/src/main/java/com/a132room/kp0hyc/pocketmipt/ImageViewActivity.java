package com.a132room.kp0hyc.pocketmipt;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        ImageView iv = new ImageView(this);
        if (getIntent().hasExtra("image uri"))
            iv.setImageURI(Uri.parse(getIntent().getStringExtra("image uri")));
        else
            iv.setImageBitmap(BitmapFactory.decodeFile((new File(getIntent().getStringExtra("full path"))).getAbsolutePath()));
        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.ImageLayout);
        cl.addView(iv);
        Log.d("kp0hyc_log", "added");
    }
}
