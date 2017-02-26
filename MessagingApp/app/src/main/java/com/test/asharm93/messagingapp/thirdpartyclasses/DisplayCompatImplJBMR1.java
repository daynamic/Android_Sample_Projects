package com.test.asharm93.messagingapp.thirdpartyclasses;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
class DisplayCompatImplJBMR1 extends DisplayCompat.Impl {
    @Override
    void getSize(Display display, Point outSize) {
        display.getSize(outSize);
    }

    @SuppressLint("NewApi") @Override
    void getRealSize(Display display, Point outSize) {
      //  display.getRealSize(outSize);
    }
}