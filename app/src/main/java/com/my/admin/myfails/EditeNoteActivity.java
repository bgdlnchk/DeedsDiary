package com.my.admin.myfails;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.my.admin.myfails.db.NotesDB;
import com.my.admin.myfails.db.NotesDao;
import com.my.admin.myfails.model.Note;

import java.util.Date;

public class EditeNoteActivity extends AppCompatActivity {

    private EditText inputNote, situation_edit, conclucion_edit , emogies_edit;
    private NotesDao dao;
    private Note temp;
    public static final String NOTE_EXTRA_Key= "note_id";
    private InterstitialAd save_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("Settings", 0);
        //Set application theme (main / night mode)
        setTheme(preferences.getBoolean("isDark",false) ? android.R.style.ThemeOverlay_Material_Dark_ActionBar : android.R.style.ThemeOverlay_Material_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_note);

        inputNote = findViewById(R.id.input_note);
        situation_edit = findViewById(R.id.situation_ed);
        conclucion_edit = findViewById(R.id.consequences_edit);
        emogies_edit = findViewById(R.id.emogi_note);

        //Initialize InterstitialAd
        save_ad= new InterstitialAd(getApplicationContext());
        save_ad.setAdUnitId(getString(R.string.Save_Ad_ID));
        save_ad.loadAd(new AdRequest.Builder().build());
        save_ad.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                save_ad.loadAd(new AdRequest.Builder().build());
            }
        });

        dao = NotesDB.getInstance(this).notesDao();
        if (getIntent().getExtras() != null){
            int id = getIntent().getExtras().getInt(NOTE_EXTRA_Key,0);
            temp = dao.getNoteById(id);
            situation_edit.setText(temp.getNoteText2());
            inputNote.setText(temp.getNoteText());
            conclucion_edit.setText(temp.getNoteText3());
            emogies_edit.setText(temp.getNoteText4());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edite_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note)
            onSaveNote();
        return super.onOptionsItemSelected(item);
    }

    private void onSaveNote() {
        //Show InterstitialAd
        if (save_ad.isLoaded()) {
            save_ad.show();
        }

        String text = inputNote.getText().toString();
        String text2 = situation_edit.getText().toString();
        String text3 = conclucion_edit.getText().toString();
        String text4 = emogies_edit.getText().toString();

        //Check if all TextViews isn't empty
        if(!text.isEmpty() && !text2.isEmpty() && !text3.isEmpty() && !text4.isEmpty()) {
            long date = new Date().getTime();

            //Save note
            if(temp==null){
                temp = new Note(text,text2,text3,text4,date);
                dao.insertNote(temp);
            } else {
                temp.setNoteText(text);
                temp.setNoteText2(text2);
                temp.setNoteText3(text3);
                temp.setNoteText4(text4);
                temp.setNoteDate(date);
                dao.updateNote(temp);
            }
            finish();
        }else {
            Toast.makeText(getApplicationContext(),"Please, full all columns!",Toast.LENGTH_SHORT).show();
        }
    }

}
