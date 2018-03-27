package com.example.monika.korepetycje.GUI;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.monika.korepetycje.R;

public class ApplicationHelper {

    public static void hideWindowKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static void showMessageDialog(String message, Activity context) {
        Dialog dialog = new Dialog(context);
        dialog.setTitle("BLĄD");
        dialog.setContentView(R.layout.dialog_window_ok);

        TextView text = dialog.findViewById(R.id.dialog_text);

        text.setText(message);

        Button okButton = dialog.findViewById(R.id.ok_button);
        okButton.setOnClickListener(view2 -> dialog.dismiss());

        dialog.show();

    }
}
