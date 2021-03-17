package com.my.admin.myfails.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.my.admin.myfails.model.Note;

/**
 * Created by admin on 05.09.2018.
 */

@Database(entities = Note.class, version = 1)
public abstract class NotesDB extends RoomDatabase{
    public abstract NotesDao notesDao();

    public static final String DATABASE_NAME = "notesDb";
    private static NotesDB instance;

    public static NotesDB getInstance(Context context){
        if (instance == null)
            instance = Room.databaseBuilder(context,NotesDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }

}
