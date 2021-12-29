package com.example.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {



    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Выберите дату")
                .setMessage("для сохранения нажмите ОК")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Return",null)
                .setView(R.layout.dialog)
                .create();
    }
}
