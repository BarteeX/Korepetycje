package com.example.monika.korepetycje.GUI.DialogWindows;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.example.monika.korepetycje.GUI.ArrayAdapters.CounterTimer;
import com.example.monika.korepetycje.R;


public class CounterDialogWindow extends Dialog {
    private CounterTimer timer;

    public CounterDialogWindow(@NonNull Context context, CounterTimer timer) {
        super(context);
        this.timer = timer;
        initComponent();
    }

    private void initComponent() {
        this.setContentView(R.layout.dialog_window_ok);

        TextView counterText = getDialogTextView();
        counterText.setTextSize(55f);

        initCancelButton();
    }

    private void initCancelButton() {
        Button cancelButton = getCancelButton();
        cancelButton.setText(R.string.cancel);
        cancelButton.setOnClickListener(view -> {
            timer.onFinish();
            timer.cancel();
            dismiss();
        });
    }

    public TextView getDialogTextView() {
        return findViewById(R.id.dialog_text);
    }

    private Button getCancelButton() {
        return findViewById(R.id.ok_button);
    }
}
