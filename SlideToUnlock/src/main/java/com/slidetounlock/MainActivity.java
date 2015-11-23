package com.slidetounlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.slidetounlock.widget.R;
import com.slidetounlock.widget.SlideToUnlock;

public class MainActivity extends Activity {

    private SlideToUnlock slideToUnlock, slideToUnlock2, slideToUnlock3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slideToUnlock = (SlideToUnlock) findViewById(R.id.slidetounlock);

        slideToUnlock.setOnUnlockListener(new SlideToUnlock.OnUnlockListener() {
            @Override
            public void onUnlock(int seekBarProgressValue) {
                if (seekBarProgressValue > 80) {
                    slideToUnlock.reset();
                    startActivity(new Intent(MainActivity.this, NewActivity.class));
                }
            }


        });


    }


}
