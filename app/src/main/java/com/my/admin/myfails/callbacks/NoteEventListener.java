package com.my.admin.myfails.callbacks;

import com.my.admin.myfails.model.Note;

/**
 * Created by admin on 07.09.2018.
 */

public interface NoteEventListener {

    void onNoteClick(Note note);

    void onNoteLongClick(Note note);

}
