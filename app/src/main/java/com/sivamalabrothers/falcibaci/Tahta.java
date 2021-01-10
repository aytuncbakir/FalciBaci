package com.sivamalabrothers.falcibaci;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;


public class Tahta extends AppCompatActivity implements View.OnClickListener {


    Button tamam,nasil,uyari;

    private static final int  CUSTOM_DIALOG_ID1 = 2;

    private AdView adView,adView1;
    LinearLayout reklam_layout,reklam_layout1;
    private InterstitialAd interstitial;
    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/9068045318";
    private static final String REKLAM_ID1 = "ca-app-pub-3183404528711365/8319862438";
    private static final String REKLAM_ID2 = "ca-app-pub-3183404528711365/6441881978";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tahta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Make to run your application only in portrait mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Make to run your application only in LANDSCAPE mode
        //setContentView(R.layout.disable_android_orientation_change);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
       actionBar.setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));

        ilklendirmeleriYap();
        animasyonUygula();
        reklam_yukle();
        reklam_yukle1();
    }

    private void reklam_yukle1(){
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(REKLAM_ID2);

        AdRequest adRequest = new AdRequest.Builder().build();

        interstitial.loadAd(adRequest);

        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (interstitial.isLoaded()) {
                    interstitial.show();
                }
            }
        });
    }



    private void ilklendirmeleriYap(){

        nasil = findViewById(R.id.nasil);
        nasil.setOnClickListener(this);

        uyari = findViewById(R.id.uyari);
        uyari.setOnClickListener(this);

        tamam = findViewById(R.id.tamam);
        tamam.setOnClickListener(this);

    }


    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Slide enterTransition = new Slide();
            enterTransition.setDuration(300);
            enterTransition.setSlideEdge(Gravity.BOTTOM);
            getWindow().setEnterTransition(enterTransition);
        }
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

    private void reklam_yukle(){

        reklam_layout = findViewById(R.id.reklam_layout);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID);

        reklam_layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        if(getScreenHeight()>1200) {
            reklam_layout1 = findViewById(R.id.reklam_layout1);

            adView1 = new AdView(this);
            adView1.setAdSize(AdSize.BANNER);
            adView1.setAdUnitId(REKLAM_ID1);

            reklam_layout1.addView(adView1);

            AdRequest adRequest1 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            adView1.loadAd(adRequest1);
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id){

            case R.id.nasil:
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Tahta.this);
                    Intent intent = new Intent(Tahta.this, Nasil.class);
                    startActivity(intent,options.toBundle());
                }else {
                    Intent intent = new Intent(Tahta.this, Nasil.class);
                    startActivity(intent);
                }
                break;

            case R.id.uyari:
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Tahta.this);
                    Intent intent = new Intent(Tahta.this, Uyari.class);
                    startActivity(intent,options.toBundle());
                }else {
                    Intent intent = new Intent(Tahta.this, Uyari.class);
                    startActivity(intent);
                }
                break;
            case R.id.tamam:
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Tahta.this);
                    Intent intent = new Intent(Tahta.this, FalListe.class);
                    startActivity(intent,options.toBundle());
                }else {
                    Intent intent = new Intent(Tahta.this, FalListe.class);
                    startActivity(intent);
                }
                break;

        }

    }



    public static int getScreenHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }



}

