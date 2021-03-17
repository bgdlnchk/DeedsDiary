package com.my.admin.myfails;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private Switch themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("Settings", 0);
        //Set app Theme (main / dark)
        setTheme(preferences.getBoolean("isDark",false) ? android.R.style.ThemeOverlay_Material_Dark_ActionBar : android.R.style.ThemeOverlay_Material_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final SharedPreferences settingsPrefs = getSharedPreferences("Settings",0);

        themeSwitch = findViewById(R.id.themeSwitch);

        if (settingsPrefs.getBoolean("isDark",false)){
            themeSwitch.setChecked(true);
        }

        //Check if NightMode is on
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(themeSwitch.isChecked()){
                    SharedPreferences.Editor editor = settingsPrefs.edit();
                    editor.putBoolean("isDark",true);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else{
                    SharedPreferences.Editor editor = settingsPrefs.edit();
                    editor.putBoolean("isDark",false);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
