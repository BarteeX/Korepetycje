package com.example.monika.korepetycje.GUI;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;

import com.example.monika.korepetycje.R;

/**
 * Created by Monika on 2018-02-25.
 */

public class GuiUtils {
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
