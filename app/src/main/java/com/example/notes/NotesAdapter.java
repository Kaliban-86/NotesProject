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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDescription.setText(note.getDescription());
        holder.textViewDate.setText(note.getDate());
        holder.textViewDateOfCompletion.setText(note.getDayOfCompletion()+ "-" + note.getMonthOfCompletion()+ "-"+ note.getYearOfCompletion());

        int colorID;
        int priority = note.getPriority();
        switch (priority) {
            case 1:
                colorID = holder.itemView.getResources().getColor(R.color.red);
                break;
            case 2:
                colorID = holder.itemView.getResources().getColor(R.color.blue);
                break;
            default:
                colorID = holder.itemView.getResources().getColor(R.color.green);
                break;
        }
        holder.textViewTitle.setBackgroundColor(colorID);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewDate;
        TextView textViewDateOfCompletion;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewDateOfCompletion = itemView.findViewById(R.id.textViewDateOfCompletion);

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
