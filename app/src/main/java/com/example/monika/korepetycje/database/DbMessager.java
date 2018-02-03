package com.example.monika.korepetycje.database;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Monika on 2018-01-27.
 */

public class DbMessager {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
