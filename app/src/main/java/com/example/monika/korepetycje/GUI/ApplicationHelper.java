package com.example.monika.korepetycje.GUI;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class ApplicationHelper {

    public static void hideWindowKeybord(Activity activity) {
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
}
