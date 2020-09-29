package com.my.admin.myfails;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class PasswordActivity extends AppCompatActivity {

    PatternLockView patternLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("Settings", 0);
        //Set app Theme (main / dark)
        setTheme(preferences.getBoolean("isDark",false) ? android.R.style.ThemeOverlay_Material_Dark_ActionBar : android.R.style.ThemeOverlay_Material_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        patternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("password", PatternLockUtils.patternToString(patternLockView, pattern));
                editor.apply();

                Toast.makeText(PasswordActivity.this, "Password Created!", Toast.LENGTH_SHORT).show();
                patternLockView.clearPattern();

            }

            @Override
            public void onCleared() {

            }
        });
    }
}
