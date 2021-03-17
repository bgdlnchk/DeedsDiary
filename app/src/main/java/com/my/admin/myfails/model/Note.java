package com.my.admin.myfails.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by admin on 03.09.2018.
 */

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "text")
    private String noteText;
    @ColumnInfo(name = "text2")
    private String noteText2;
    @ColumnInfo(name = "text3")
    private String noteText3;
    @ColumnInfo(name = "text4")
    private String noteText4;
    @ColumnInfo(name = "date")
    private long noteDate;

    @Ignore
    private boolean checked = false;

    public Note() {
    }

    public Note(String noteText, String noteText2, String noteText3, String noteText4, long noteDate) {
        this.noteText = noteText;
        this.noteText2 = noteText2;
        this.noteText3 = noteText3;
        this.noteText4 = noteText4;
        this.noteDate = noteDate;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteText2() {
        return noteText2;
    }

    public void setNoteText2(String noteText2) {
        this.noteText2 = noteText2;
    }

    public String getNoteText3() {
        return noteText3;
    }

    public void setNoteText3(String noteText3) {
        this.noteText3 = noteText3;
    }

    public String getNoteText4() {
        return noteText4;
    }

    public void setNoteText4(String noteText4) {
        this.noteText4 = noteText4;
    }

    public long getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(long noteDate) {
        this.noteDate = noteDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked(){return checked;}

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", noteDate=" + noteDate +
                '}';
    }
}
