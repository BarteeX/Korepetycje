package com.example.monika.korepetycje.GUI.DialogWindows;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.example.monika.korepetycje.GUI.ApplicationHelper;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Student;
import com.example.monika.korepetycje.database.models.Term;

import java.util.List;


public class StartCounterDialog extends Dialog {
    private Context context;
    private Student student;
    private List<Term> studentTerms;

    public StartCounterDialog(@NonNull Context context, Student student) {
        super(context);
        this.context = context;
        this.student = student;
        this.studentTerms = student.getTerms();
        initComponent();

    }

    private void initComponent() {
        setTitle(R.string.timer_dialog_title);
        setContentView(R.layout.dialog_window_yes_no);

        initDialogText();
        initYesButton();
        initNoButton();
    }

    private void initDialogText() {
        TextView dialogText = findViewById(R.id.dialog_yen_no_text);
        dialogText.setText(R.string.timer_dialog_text);
    }

    private void initYesButton() {
        Button yesButton = findViewById(R.id.yes_button);
        yesButton.setOnClickListener(view -> {
            ApplicationHelper.startSingleTerm(studentTerms.get(0), (Activity) context);
            dismiss();
        });
    }

    private void initNoButton() {
        Button noButton = findViewById(R.id.no_button);
        noButton.setOnClickListener(view -> dismiss());
    }
}
