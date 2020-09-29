package com.my.admin.myfails;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.kobakei.ratethisapp.RateThisApp;
import com.my.admin.myfails.adapter.NotesAdapter;
import com.my.admin.myfails.callbacks.NoteEventListener;
import com.my.admin.myfails.db.NotesDB;
import com.my.admin.myfails.db.NotesDao;
import com.my.admin.myfails.model.Note;
import com.my.admin.myfails.utils.NoteUtils;
import java.util.ArrayList;
import java.util.List;

import static com.my.admin.myfails.EditeNoteActivity.NOTE_EXTRA_Key;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,NoteEventListener {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private NotesDao dao;
    private FloatingActionButton fab;
    private InterstitialAd delete_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("Settings", 0);
        //Set app Theme (main / dark)
        setTheme(preferences.getBoolean("isDark",false) ? android.R.style.ThemeOverlay_Material_Dark_ActionBar : android.R.style.ThemeOverlay_Material_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize AdMob account
        MobileAds.initialize(this,getString(R.string.AdMob_ID));

        //Initialize InterstitialAd
        delete_ad = new InterstitialAd(getApplicationContext());
        delete_ad.setAdUnitId(getString(R.string.Delete_Ad_ID));
        delete_ad.loadAd(new AdRequest.Builder().build());
        delete_ad.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                delete_ad.loadAd(new AdRequest.Builder().build());
            }
        });

        //Show RateThisApp Window
        RateThisApp.onCreate(this);
        RateThisApp.showRateDialogIfNeeded(this);
        RateThisApp.Config config = new RateThisApp.Config(2, 3);
        RateThisApp.init(config);

        recyclerView = findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewNote();
            }
        });

        dao = NotesDB.getInstance(this).notesDao();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void loadNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        List<Note> list = dao.getNotes();
        notes.addAll(list);
        ArrayList<Note> emogies = new ArrayList<>();
        List<Note> list2 = dao.getNotes();
        emogies.addAll(list2);
        this.adapter = new NotesAdapter(this, notes, emogies);
        this.adapter.setListener(this);
        this.recyclerView.setAdapter(adapter);
    }

    private void onAddNewNote() {
        startActivity(new Intent(this, EditeNoteActivity.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.preferences.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent itemIntent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(itemIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_lock) {
            Intent passIntent = new Intent(this,PasswordActivity.class);
            startActivity(passIntent);

        } else if (id == R.id.nav_settings) {
            Intent settIntent = new Intent(this,SettingsActivity.class);
            startActivity(settIntent);

        //ShareButton code
        } else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Download here: https://play.google.com/store/apps/collection/cluster?clp=igNAChkKEzczNTc1MDk5OTM3NTk0OTUzMDAQCBgDEiEKG2NvbS5jb21wYW55LmFkbWluLmZvdW5kZXIzMxABGAMYAQ%3D%3D:S:ANO1ljLJ4OE");
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        //SendButton code
        } else if (id == R.id.nav_send) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Download here: https://play.google.com/store/apps/collection/cluster?clp=igNAChkKEzczNTc1MDk5OTM3NTk0OTUzMDAQCBgDEiEKG2NvbS5jb21wYW55LmFkbWluLmZvdW5kZXIzMxABGAMYAQ%3D%3D:S:ANO1ljLJ4OE");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public void onNoteClick(Note note) {
        Intent edit = new Intent(this, EditeNoteActivity.class);
        edit.putExtra(NOTE_EXTRA_Key, note.getId());
        startActivity(edit);
    }

    @Override
    public void onNoteLongClick(final Note note) {

        //Press long on note to create AlertDialog
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (delete_ad.isLoaded()) {
                            delete_ad.show();
                        }
                        dao.deleteNote(note);
                        loadNotes();
                    }
                })
                .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        String text = note.getNoteText() + "\n Create on :" +
                                NoteUtils.dateFromLong(note.getNoteDate());
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT, text);
                        startActivity(share);
                    }
                })
                .create()
                .show();

    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);

        adapter.setMultiCheckedMode(false);
        adapter.setListener(this);
        fab.setVisibility(View.VISIBLE);
    }
}
