package com.slidetounlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.slidetounlock.widget.R;
import com.slidetounlock.widget.SlideToUnlock;

public class MainActivity extends Activity implements SlideToUnlock.OnUnlockListener {

  private SlideToUnlock slideToUnlock,slideToUnlock2,slideToUnlock3;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    slideToUnlock = (SlideToUnlock) findViewById(R.id.slidetounlock);
    slideToUnlock2 = (SlideToUnlock) findViewById(R.id.slidetounlock2);
    slideToUnlock3 = (SlideToUnlock) findViewById(R.id.slidetounlock3);


    slideToUnlock.setOnUnlockListener(this);
    slideToUnlock2.setOnUnlockListener(this);
    slideToUnlock3.setOnUnlockListener(this);
  }


  @Override
  public void onUnlock() {

    slideToUnlock.reset();

    startActivity(new Intent(MainActivity.this, NewActivity.class));

  }

}
