package org.android.lay.autohidekeyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import org.android.lay.autohidekeyboard.lib.KeyboardController;

public class TestActivity extends AppCompatActivity {

    private KeyboardController mKeyboardController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mKeyboardController = KeyboardController.attach(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mKeyboardController.capture(ev);
        return super.dispatchTouchEvent(ev);
    }
}
