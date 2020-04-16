package com.example.someandroidfunc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    ArrayList<Note> noteList;
    LayoutInflater inflater;
    OnNoteListener onNoteListener;

    public NoteAdapter(Context context, ArrayList<Note> noteList, OnNoteListener onNoteListener){
        inflater = LayoutInflater.from(context);
        this.noteList = noteList;
        this.onNoteListener = onNoteListener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_note_card, parent, false);
        NoteViewHolder holder = new NoteViewHolder(view, onNoteListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position){
        Note selectedNote = noteList.get(position);
        holder.setData(selectedNote,position);
    }

    @Override
    public int getItemCount(){
        return noteList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, date;
        OnNoteListener onNoteListener;

        public NoteViewHolder(View itemView, OnNoteListener onNoteListener){
            super(itemView);
            title = itemView.findViewById(R.id.noteTitleText);
            date = itemView.findViewById(R.id.noteDateText);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        public void setData(Note selectedNote, int position){
            this.title.setText(selectedNote.getTitle());
            this.date.setText(selectedNote.getDate());
        }

        @Override
        public void onClick(View v){
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }



    public interface OnNoteListener{
        void onNoteClick(int position);
    }


}
