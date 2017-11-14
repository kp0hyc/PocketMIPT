package com.a132room.kp0hyc.pocketmipt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class FirstBootActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_boot);
    }

    public void onAntiReset(View v)
    {
        Log.d("kp0hyc_log", "On anti reset clicked");
        getSharedPreferences("com.a132room.kp0hyc.pocketmipt", MODE_PRIVATE).edit()
                .putBoolean(getString(R.string.first_boot_bool), false).apply();
        Intent intent = new Intent(this, MainMenuActivity.class);
        //intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void onNextClicked(View v)
    {
        Log.d("kp0hyc_log", "data set: " + ((EditText)findViewById(R.id.dataText)).getText().toString());

        getSharedPreferences("com.a132room.kp0hyc.pocketmipt", MODE_PRIVATE).edit()
                .putString(getString(R.string.data_key), ((EditText)findViewById(R.id.dataText)).getText().toString()).apply();
        getSharedPreferences("com.a132room.kp0hyc.pocketmipt", MODE_PRIVATE).edit()
                .putBoolean(getString(R.string.first_boot_bool), false).apply();
        Intent intent = new Intent(this, MainMenuActivity.class);
        //intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        Log.d("kp0hyc_log", "back pressed");
    }
}
