package com.my.admin.myfails;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        //Check if user open the app first time.
        //If user open the app first time he (she) will go to CreatePasswordActivity, if not, to InputPasswordActivity
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        String password = preferences.getString("password", "0");
        if (password.equals("0")){
            Intent intent3 = new Intent(getApplicationContext(), CreatePasswordActivity.class);
            startActivity(intent3);
        }else {
            Intent intent = new Intent(getApplicationContext(), InputPasswordActivity.class);
            startActivity(intent);
        }
    }
}
