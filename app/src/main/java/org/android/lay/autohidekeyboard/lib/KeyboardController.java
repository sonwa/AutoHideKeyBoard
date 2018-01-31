package org.android.lay.autohidekeyboard.lib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by LAY on 2018/1/30.
 */

public class KeyboardController {


    private static KeyboardController keyboardController;
    private Activity mActivity;


    public static KeyboardController
    attach(Activity activity) {
        if (activity == null) {
            throw new NullPointerException("activity is not existed !");
        }

        if (keyboardController == null) {
            keyboardController = new KeyboardController(activity);
        }

        return keyboardController;
    }

    public void capture(MotionEvent ev) {
        if (!(ev.getAction() == MotionEvent.ACTION_DOWN)) return;
        handleTouchEvent(mActivity, ev);
    }

    private KeyboardController(Activity activity) {
        mActivity = activity;
    }

    private boolean handleTouchEvent(Activity activity, MotionEvent event) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) return false;
        if (!(currentFocus instanceof EditText)) {
            hideKeyBoard(currentFocus);
            return false;
        }

        Rect rect = new Rect();
        int[] locationXY = new int[2];
        currentFocus.getLocationOnScreen(locationXY);
        rect.set(locationXY[0], locationXY[1], locationXY[0] + currentFocus.getWidth(), locationXY[1] + currentFocus.getHeight());
        if (rect.contains((int) event.getRawX(), (int) event.getRawY())) return false;
        hideKeyBoard(currentFocus);
        return false;
    }

    private void hideKeyBoard(View currentFocus) {
        if (currentFocus != null) {
            hide(currentFocus);
            currentFocus.clearFocus();
        }

    }

    public boolean hide(@NonNull View view) {
        try {
            InputMethodManager imm = (InputMethodManager) view.getContext()
                    .getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hide(@NonNull Activity activity) {
        return hide(activity.getWindow().getCurrentFocus());
    }


}
