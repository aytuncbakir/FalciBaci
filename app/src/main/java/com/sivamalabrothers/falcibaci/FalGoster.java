package com.sivamalabrothers.falcibaci;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Locale;

public class FalGoster extends AppCompatActivity implements View.OnClickListener{

    WebView cvs;

    FloatingActionButton fabayarlar;




    ArrayList<Integer> secilenSozcukler;

    ArrayList<String> secilenBasliklar;
    // Justify tag
    String justifyTag = "<html><body style='text-align:justify;'>%s</body></html>";
    // Concatenate your string with the tag to Justify it

    String dataString ;


    String fal_yorumu = "";

    String [] yorumlar ;
    Bundle bundle;
    private AdView adView;
    LinearLayout reklam_layout;
    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/5011597847";

    private InterstitialAd interstitial;
    private static final String REKLAM_ID1 = "ca-app-pub-3183404528711365/7419921724";



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sozler);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));

        Toast.makeText(FalGoster.this, "Neyse halimiz çıksın falımız", Toast.LENGTH_LONG).show();
        initViews();
        setSecilenSozcukler();
        setGetFalYorumu(secilenSozcukler);
        falGoruntule(fal_yorumu);
        animasyonUygula();
        //reklam_yukle1();

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
            secilenBasliklar.clear();
            secilenSozcukler.clear();
            bundle.clear();
            finishAfterTransition();
        }
        else {
            secilenBasliklar.clear();
            secilenSozcukler.clear();
            bundle.clear();
            finish();
        }
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        secilenBasliklar.clear();
        secilenSozcukler.clear();
        bundle.clear();

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

        yorumlar = this.getResources().getStringArray(R.array.fal_yorumlar);
        bundle = getIntent().getExtras();
        secilenSozcukler = new ArrayList<>();
                //(ArrayList<Integer>) bundle.getIntegerArrayList("secilenSozcukler");
        secilenBasliklar = (ArrayList<String>) bundle.getStringArrayList("secilenBasliklar");

        fabayarlar =  findViewById(R.id.fabayarlar);
        fabayarlar.setOnClickListener(this);


    }

    private void setSecilenSozcukler(){

        if(secilenBasliklar.size()>0) {

            String[] sozcukler = this.getResources().getStringArray(R.array.fal_sozcukler);

            for (int i = 0; i < secilenBasliklar.size(); i++) {
                for (int j = 0; i < sozcukler.length; j++) {
                    if (secilenBasliklar.get(i).toString().equals(sozcukler[j])) {
                        secilenSozcukler.add(j);
                        //Toast.makeText(FalGoster.this, "" + j, Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }

        }else{
            Toast.makeText(FalGoster.this, "Listeden giriş yapmalısınız", Toast.LENGTH_LONG).show();
        }
    }

    public void falGoruntule( String fal_yorumu ){

        dataString = String.format(Locale.US, justifyTag, fal_yorumu);

        // Load the data in the web view
        cvs.loadDataWithBaseURL("", dataString, "text/html", "UTF-8", "");


    }

    private void setGetFalYorumu(ArrayList<Integer> secilenSozcukler){

        String s = "";
        String str = "";



        s +=" <br><font color='white'>" +"<center><b> Kahve Falınız </b></center>"+"</font><br>";
        for(int i = 0; i< secilenSozcukler.size(); i++){

            s = s +" <br><font color='white'>"
                    +yorumlar[secilenSozcukler.get(i)] +"</font><br>";


        }
        s = s +"<br><br>";

        fal_yorumu = s ;



    }

    private void fali_paylas(){


        Spanned spanned = Html.fromHtml(fal_yorumu);
        char[] chars = new char[spanned.length()];
        TextUtils.getChars(spanned, 0, spanned.length(), chars, 0);
        String plainText = new String(chars);

        Intent paylas = new Intent(Intent.ACTION_SEND);
        paylas.setType("text/plain");
        paylas.putExtra(Intent.EXTRA_TEXT,plainText);
        startActivity(Intent.createChooser(paylas,"Paylaş"));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){
            case R.id.fabayarlar:
                fali_paylas();
                break;

        }
    }


}
