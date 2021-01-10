package com.sivamalabrothers.falcibaci;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Locale;

public class Uyari extends AppCompatActivity implements View.OnClickListener{

    WebView cvs;

    FloatingActionButton fabayarlar;

    // Justify tag
    String justifyTag = "<html><body style='text-align:justify;'>%s</body></html>";
    // Concatenate your string with the tag to Justify it

    String dataString ;


    String nasils = "";

    private InterstitialAd interstitial;
    private static final String REKLAM_ID1 = "ca-app-pub-3183404528711365/9920595003";
    private static  int reklamGoster1 = 1;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nasil);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));

        nasils = getString(R.string.uyari);
        initViews();
        falGoruntule();
        animasyonUygula();
        if(reklamGoster1 % 3 == 0)
                reklam_yukle1();

    }

    private void reklam_yukle1(){
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(REKLAM_ID1);

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


    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Fade enterTransition = new Fade();
            enterTransition.setDuration(1000);
            getWindow().setEnterTransition(enterTransition);

        }
    }

    // geri butonuna basıldığında çalışır
    @Override
    public boolean onSupportNavigateUp(){
        if(Build.VERSION.SDK_INT >= 21) {

            finishAfterTransition();
        }
        else {

            finish();
        }
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();


    }

    @Override
    public void onPause(){
        super.onPause();
        // put your code here...

    }


    public void initViews(){


        cvs=findViewById(R.id.cevsentahtasi);
        cvs.getSettings();
        cvs.setBackgroundColor(Color.TRANSPARENT);

        fabayarlar =  findViewById(R.id.fabayarlar);
        fabayarlar.setOnClickListener(this);


    }



    public void falGoruntule(){

        dataString = String.format(Locale.US, justifyTag, nasils);

        // Load the data in the web view
        cvs.loadDataWithBaseURL("", dataString, "text/html", "UTF-8", "");


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){
            case R.id.fabayarlar:
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Uyari.this);
                    Intent intent = new Intent(Uyari.this, Tahta.class);
                    startActivity(intent,options.toBundle());
                }else {
                    Intent intent = new Intent(Uyari.this, Tahta.class);
                    startActivity(intent);
                }
                break;

        }
    }


}
