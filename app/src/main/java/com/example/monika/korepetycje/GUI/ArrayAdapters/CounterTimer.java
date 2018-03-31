package com.example.monika.korepetycje.GUI.ArrayAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import com.example.monika.korepetycje.GUI.DialogWindows.CounterDialogWindow;
import com.example.monika.korepetycje.GUI.TimesUtils;

import java.util.Map;


public class CounterTimer extends CountDownTimer {
    private TextView counterText;
    private CounterDialogWindow dialog;
    private Activity context;

    public CounterTimer(long millisInFuture, long countDownInterval, Activity context) {
        this(millisInFuture, countDownInterval);
        this.context = context;
        this.dialog = new CounterDialogWindow(context,this);
        this.dialog.show();
        this.counterText = dialog.getDialogTextView();
    }

    CounterTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long timeLong) {
        Map<String, Integer> timeMap = TimesUtils.getTime(timeLong);
        counterText.setText(timeMap.get("minutes") + " : " + timeMap.get("seconds"));
    }

    @Override
    public void onFinish() {
        proccedVibrations();
        dialog.dismiss();
    }

    private void proccedVibrations() {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                newVibrations(vibrator);
            } else {
                oldVibrations(vibrator);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void newVibrations(Vibrator vibrator) {
        vibrator.vibrate(VibrationEffect.createOneShot(3000, 100), new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());
    }

    private void oldVibrations(Vibrator vibrator) {
        vibrator.vibrate(3000, new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());
    }
}
