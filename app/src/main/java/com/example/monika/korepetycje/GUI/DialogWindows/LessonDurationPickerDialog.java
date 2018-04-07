package com.example.monika.korepetycje.GUI.DialogWindows;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TimePicker;

import com.example.monika.korepetycje.GUI.ApplicationHelper;
import com.example.monika.korepetycje.database.models.Term;


public class LessonDurationPickerDialog extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, 0, 0, true);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "ANULUJ",
                (dialog1, which) -> dialog1.dismiss()
        );
        return dialog;
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        Term tempTerm = new Term(-999, -999);
        tempTerm.setTimeFrom(0, 0);
        tempTerm.setTimeTo(hours, minutes);
        ApplicationHelper.startSingleTerm(tempTerm, getActivity());
    }
}
