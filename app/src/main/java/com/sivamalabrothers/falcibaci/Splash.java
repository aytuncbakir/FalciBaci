package com.sivamalabrothers.falcibaci;


import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.Random;


public class Splash extends AppCompatActivity implements Animation.AnimationListener {



    private TextView tv1,tv2;
    private ImageView img;
    private Animation mTextAnim,mTextAnim1;
    private Animation mImgAnim;

    private AdView adView,adView1;
    LinearLayout reklam_layout,reklam_layout1;
    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/7757056978";
    private static final String REKLAM_ID1 = "ca-app-pub-3183404528711365/2335116005";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Make to run your application only in portrait mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Make to run your application only in LANDSCAPE mode
        //setContentView(R.layout.disable_android_orientation_change);
        setContentView(R.layout.splash);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));

        img = findViewById(R.id.img);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        spanText();
        scaleAnimation(tv1,tv2,img);
        reklam_yukle();
    }



    private void scaleAnimation(View view, View view2, View view3){

        mTextAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim_text);
        mTextAnim.setAnimationListener(this);
        tv1.startAnimation(mTextAnim);

        mTextAnim1 = AnimationUtils.loadAnimation(this, R.anim.splash_anim_text);
        mTextAnim1.setAnimationListener(this);
        tv2.startAnimation(mTextAnim1);


        mImgAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim_text);
        mImgAnim.setAnimationListener(this);
        img.startAnimation(mImgAnim);


    }


    private void reklam_yukle(){

        reklam_layout = findViewById(R.id.reklam_layout);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID);

        reklam_layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        reklam_layout1 = findViewById(R.id.reklam_layout1);

        adView1 = new AdView(this);
        adView1.setAdSize(AdSize.BANNER);
        adView1.setAdUnitId(REKLAM_ID1);

        reklam_layout1.addView(adView1);

        AdRequest adRequest1 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView1.loadAd(adRequest1);

    }

    // geri butonuna basıldığında çalışır
    @Override
    public boolean onSupportNavigateUp(){
        if(Build.VERSION.SDK_INT >= 21)
            finishAfterTransition();
        else
            finish();
        return true;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation.equals(mTextAnim)) {

           if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Splash.this);
                Intent intent = new Intent(Splash.this, Tahta.class);
                startActivity(intent,options.toBundle());
            }else {
                Intent intent = new Intent(Splash.this, Tahta.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}


    private void spanText(){


        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fofbb_reg.ttf");
        tv1.setTypeface(font,Typeface.BOLD);
        tv2.setTypeface(font,Typeface.BOLD);


        tv1.setTextSize(24);
        tv2.setTextSize(24);

        //yourTextView.setText(sb);

    }



}
