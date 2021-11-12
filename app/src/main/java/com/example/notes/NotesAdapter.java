package com.example.notes;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private onNoteClicklistener onNoteClicklistener;


    List<Note> notes;

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    interface onNoteClicklistener {
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClicklistener(NotesAdapter.onNoteClicklistener onNoteClicklistener) {
        this.onNoteClicklistener = onNoteClicklistener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDescription.setText(note.getDescription());
        holder.textViewDayOfWeek.setText(Note.getDayAsString(note.getDayOfWeek()));
        holder.textViewDate.setText(note.getDate());

        int colorID;
        int priority = note.getPriority();
        switch (priority) {
            case 1:
                colorID = holder.itemView.getResources().getColor(android.R.color.holo_red_light);
                break;
            case 2:
                colorID = holder.itemView.getResources().getColor(android.R.color.holo_orange_light);
                break;
            default:
                colorID = holder.itemView.getResources().getColor(android.R.color.holo_green_light);
                break;
        }
        holder.textViewTitle.setBackgroundColor(colorID);
        //holder.textViewDescription.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.holo_blue_light));


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewDayOfWeek;

        //***********//
        TextView textViewDate;
        //***********//

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);

            //***********//
            textViewDate = itemView.findViewById(R.id.textViewDate);
            //***********//

            itemView.setOnClickListener(view -> {
                if (onNoteClicklistener != null) {
                    onNoteClicklistener.onNoteClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(view -> {
                if (onNoteClicklistener != null) {
                    onNoteClicklistener.onLongClick(getAdapterPosition());
                }
                return true;
            });
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<Note> getNotes() {
        return notes;
    }
}
