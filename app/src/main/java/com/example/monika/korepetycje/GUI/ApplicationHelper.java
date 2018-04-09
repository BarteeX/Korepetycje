package com.example.monika.korepetycje.GUI;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.monika.korepetycje.GUI.ArrayAdapters.CounterTimer;
import com.example.monika.korepetycje.GUI.Controllers.StudentsList;
import com.example.monika.korepetycje.R;
import com.example.monika.korepetycje.database.models.Term;

import java.util.Map;

public class ApplicationHelper {

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
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

    public static boolean startSingleTerm(Term term, Activity context) {
        Map<String, Integer> timeMap = TimesUtils.getBetween(term.getTimeFrom(), term.getTimeTo());
        int hoursBetween = timeMap.get("hours");
        int minutesBetween = timeMap.get("minutes");
        if (TimesUtils.isTimeCorrect(hoursBetween, minutesBetween)) {
            int millisToSet = TimesUtils.toMillis(hoursBetween, minutesBetween);

            CounterTimer counterTimer = new CounterTimer(millisToSet, TimesUtils.MILLIS_IN_SECOND, context);
            counterTimer.start();
        } else {
            System.out.println("UNABLE TO SET TIMER... \nSOME BUGS TO FIX... \nTIME IS NOT IN TIME..\n");
            System.out.println("HOUR : " + hoursBetween + "\n MINUTES : " + minutesBetween);
            return false;
        }

        return true;
    }

    public static void startMessageIntent(String telephone, Activity context) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:" + Uri.encode(telephone)));
        context.startActivity(smsIntent);
    }

    public static void startStudentListIntent(String filter, Activity context) {
        Intent intent = new Intent(context, StudentsList.class);
        Intent studentIntent = intent.putExtra("filter", filter);
        context.startActivity(studentIntent);
    }

    public static void startStudentListWithQueryIntent(String query, Activity context) {
        Intent intent = new Intent(context, StudentsList.class);
        Intent studentIntent = intent.putExtra("query", query);
        context.startActivity(studentIntent);
    }
}
