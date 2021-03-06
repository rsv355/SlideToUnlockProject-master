/*
 * Copyright (c) 2014 Vitali Vasilioglo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package com.slidetounlock.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Custom slider view aka "Slide to unlock".
 *
 * @author Vitali Vasilioglo
 */
public class SlideToUnlock3 extends RelativeLayout {

  private Drawable track;
  private View     background;

  public interface OnUnlockListener {

    /**
     * Called when unlock event occurred.
     */
    void onUnlock(int seekBarProgressValue);
  }

  private OnUnlockListener listener;
  public static SeekBar          seekbar;
  private TextView         label;
  private int              thumbWidth;
  private Context _ctx;

  public SlideToUnlock3(Context context) {
    super(context);


    init(context, null);
  }

  public SlideToUnlock3(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public SlideToUnlock3(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context, attrs);
  }



  public void setOnUnlockListener(OnUnlockListener listener) {
    this.listener = listener;
    this.seekbar.setProgress(0);
  }

  /**
   * Resets slider to initial state.
   */
  public void reset() {
    seekbar.setProgress(0);
  }



  private void init(Context context, AttributeSet attrs) {

  this._ctx = context;

    if (isInEditMode()) {
      return;
    }
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    inflater.inflate(R.layout.slidetounlock_lt3, this, true);

    label = (TextView) findViewById(R.id.slider_label);
    Typeface custom_font = Typeface.createFromAsset(_ctx.getAssets(), "fonts/trench.ttf");
    label.setTypeface(custom_font);


    seekbar = (SeekBar) findViewById(R.id.slider_seekbar);
    background = findViewById(R.id.slider_bg);

    TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SlideToUnlockView);
    String text = attributes.getString(R.styleable.SlideToUnlockView_text);
    Drawable thumb = attributes.getDrawable(R.styleable.SlideToUnlockView_thumb);

    if (thumb == null) {
      thumb = getResources().getDrawable(R.drawable.slidetounlock_thumb);
    }

    track = attributes.getDrawable(R.styleable.SlideToUnlockView_track);
    attributes.recycle();

    thumbWidth = thumb.getIntrinsicWidth();

    if (track != null) {
      background.setBackgroundDrawable(track);
    }

    if (text != null) {
      label.setText(text);
    }
    label.setPadding(thumbWidth, 0, 0, 0);

    int defaultOffset = seekbar.getThumbOffset();

    int col = Color.parseColor("#ff9900");
    seekbar.setThumb(writeOnDrawable(R.drawable.orangecircle_img, "PRINCIPAL",col));

    seekbar.setThumbOffset(defaultOffset);
    seekbar.setMax(101);




    seekbar.setOnTouchListener(new OnTouchListener() {
      private boolean isInvalidMove;



      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {



        switch (motionEvent.getAction()) {

          case MotionEvent.ACTION_DOWN:
            //Finger Touch

            return isInvalidMove = motionEvent.getX() > thumbWidth;

          case MotionEvent.ACTION_MOVE:
            return isInvalidMove;
          case MotionEvent.ACTION_UP:
            //Release
            return isInvalidMove;
        }
        return false;
      }
    });

    seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override
      public void onProgressChanged(SeekBar seekBar1, int progress, boolean fromTouch) {
       // label.setAlpha(1f - progress * 0.02f);

       // seekBar1.setThumb(writeOnDrawable(R.drawable.circle2, "" + progress));
        //int defaultOffset = seekBar1.getThumbOffset();
        //seekBar1.setThumbOffset(defaultOffset);

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {


        ObjectAnimator anim = ObjectAnimator.ofInt(seekBar, "progress", 0);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        anim.start();


        if(!seekBar.isFocusableInTouchMode())
        listener.onUnlock(seekBar.getProgress());

        /*
        if (seekBar.getProgress() < 60) {
          ObjectAnimator anim = ObjectAnimator.ofInt(seekBar, "progress", 0);
          anim.setInterpolator(new AccelerateDecelerateInterpolator());
          anim.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
          anim.start();
        }

        else {
          if (listener != null) {
              listener.onUnlock();
          }
        }
        */

      }
    });
  }


  public  BitmapDrawable writeOnDrawable(int drawableId, String text,int col){

    Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);
/*
    Paint paint1 = new Paint();
    ColorFilter filter = new PorterDuffColorFilter(col, PorterDuff.Mode.SRC_IN);
    paint1.setColorFilter(filter);

    Canvas canvas1 = new Canvas(bm);
    canvas1.drawBitmap(bm, 0, 0, paint1);
*/

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(Color.WHITE);

    paint.setTextSize(30);

    Canvas canvas = new Canvas(bm);

    int cHeight = canvas.getClipBounds().height();
    int cWidth = canvas.getClipBounds().width();
    Rect r = new Rect();
    paint.setTextAlign(Paint.Align.LEFT);
    paint.getTextBounds(text, 0, text.length(), r);

    float x = cWidth / 2f - r.width() / 2f - r.left;
    float y = cHeight / 2f + r.height() / 2f - r.bottom;

    canvas.drawText(text,x,y, paint);


    BitmapDrawable drawable = new BitmapDrawable(getResources(),bm);

    return drawable;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    if (isInEditMode()) {
      return;
    }

    //prevents 9-patch background image from full size stretching
    if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
      background.getLayoutParams().height = seekbar.getHeight() + fromDpToPx(3);
    }

  }

  public int fromDpToPx(int dp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
        getResources().getDisplayMetrics());
  }
}
