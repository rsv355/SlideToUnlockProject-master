package com.slidetounlock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.readystatesoftware.viewbadger.BadgeView;
import com.slidetounlock.widget.R;
import com.slidetounlock.widget.SlideToUnlock;
import com.slidetounlock.widget.SlideToUnlock2;
import com.slidetounlock.widget.SlideToUnlock3;

public class MainActivity extends Activity {

    private SlideToUnlock slideToUnlock;
    private SlideToUnlock2 slideToUnlock2;
    private SlideToUnlock3 slideToUnlock3;
    BadgeView parentTips;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slideToUnlock = (SlideToUnlock) findViewById(R.id.slidetounlock);

        slideToUnlock2 = (SlideToUnlock2) findViewById(R.id.slidetounlock2);

        slideToUnlock3 = (SlideToUnlock3) findViewById(R.id.slidetounlock3);

        slideToUnlock.setOnUnlockListener(new SlideToUnlock.OnUnlockListener() {
            @Override
            public void onUnlock(int seekBarProgressValue) {
                if (seekBarProgressValue > 80) {
                    slideToUnlock.reset();
                    startActivity(new Intent(MainActivity.this, NewActivity.class));
                }
            }


        });


        parentTips = new BadgeView(MainActivity.this, slideToUnlock);
        parentTips.setBadgeBackgroundColor(Color.parseColor("#00001a"));
        parentTips.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        parentTips.setBadgeMargin(40, 15);
        parentTips.setText("2");
        parentTips.show();



        slideToUnlock2.setOnUnlockListener(new SlideToUnlock2.OnUnlockListener() {
            @Override
            public void onUnlock(int seekBarProgressValue) {
                if (seekBarProgressValue > 80) {
                    slideToUnlock2.reset();
                    startActivity(new Intent(MainActivity.this, NewActivity.class));
                }
            }


        });

        slideToUnlock3.setOnUnlockListener(new SlideToUnlock3.OnUnlockListener() {
            @Override
            public void onUnlock(int seekBarProgressValue) {
                if (seekBarProgressValue > 80) {
                    slideToUnlock3.reset();
                    startActivity(new Intent(MainActivity.this, NewActivity.class));
                }
            }


        });


    }


}
