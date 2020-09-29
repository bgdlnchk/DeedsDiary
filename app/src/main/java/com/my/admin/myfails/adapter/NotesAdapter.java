package com.my.admin.myfails.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.my.admin.myfails.R;
import com.my.admin.myfails.callbacks.NoteEventListener;
import com.my.admin.myfails.model.Note;
import com.my.admin.myfails.utils.NoteUtils;

import java.util.ArrayList;

/**
 * Created by admin on 03.09.2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {

    private Context context;
    private ArrayList<Note> notes, emogies;
    private NoteEventListener listener;
    private boolean multiCheckedMode = false;

    public NotesAdapter(Context context, ArrayList<Note> notes, ArrayList<Note> emogies) {
        this.context = context;
        this.notes = notes;
        this.emogies = emogies;
    }

    @Override
    public NoteHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.note_layout, parent, false);
        return new NoteHolder(v);
    }

    @Override
    public void onBindViewHolder( NoteHolder holder, int position) {
        final Note note = getNote(position);
        if(note != null) {
            holder.noteText.setText(note.getNoteText());
            holder.emogiesText.setText(note.getNoteText4());
            holder.noteDate.setText(NoteUtils.dateFromLong(note.getNoteDate()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNoteClick(note);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onNoteLongClick(note);
                    return false;
                }
            });

            if (multiCheckedMode){
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.checkBox.setChecked(note.isChecked());
            } else holder.checkBox.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private Note getNote(int position) {
        return notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView noteText, noteDate, emogiesText;
        CheckBox checkBox;

        public NoteHolder(View itemView) {
            super(itemView);
            emogiesText = itemView.findViewById(R.id.emogies_text);
            noteDate = itemView.findViewById(R.id.note_date);
            noteText = itemView.findViewById(R.id.note_text);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public void setListener(NoteEventListener listener) {
        this.listener = listener;
    }

    public void setMultiCheckedMode(boolean multiCheckedMode) {
        this.multiCheckedMode = multiCheckedMode;
        notifyDataSetChanged();
    }

}
