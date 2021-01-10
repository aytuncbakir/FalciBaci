package com.sivamalabrothers.falcibaci;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class FalListe extends AppCompatActivity  implements View.OnClickListener{


    EditText textBox;
    TextView text;
    ListView list;
    FloatingActionButton fabayarlar;

    ArrayAdapter adapter;

    ArrayList<Integer> secilenSozcukler;
    private static  int reklamGoster = 1;

    ArrayList<String> secilenBasliklar;
    private InterstitialAd interstitial;
    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/7314203849";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fal_liste);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Make to run your application only in portrait mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Make to run your application only in LANDSCAPE mode
        //setContentView(R.layout.disable_android_orientation_change);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        ilklendirmeleriYap();
        secilenBasliklar.clear();
        secilenSozcukler.clear();
        if(reklamGoster % 6 == 0)
                reklam_yukle1();

        animasyonUygula();

    }

    private void reklam_yukle1(){
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(REKLAM_ID);

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

    @Override
    public void onResume(){
        super.onResume();
        secilenBasliklar.clear();
        secilenSozcukler.clear();

    }

    private void ilklendirmeleriYap(){

        secilenSozcukler = new ArrayList<>();
        secilenBasliklar = new ArrayList<>();

        fabayarlar =  findViewById(R.id.fabayarlar);
        fabayarlar.setOnClickListener(this);


        textBox=findViewById(R.id.textBox);
        text=findViewById(R.id.text);
        list=findViewById(R.id.list);

        String [] sozcukler = this.getResources().getStringArray(R.array.fal_sozcukler);

        adapter=new ArrayAdapter(this,R.layout.list_item,R.id.text,sozcukler);
        list.setAdapter(adapter);


        textBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = String.valueOf(list.getItemAtPosition(position));
                checkForRepeatationAndAdd( position,  text);
                Toast.makeText(FalListe.this, text+" eklendi.",Toast.LENGTH_LONG).show();
            }
        });

    }


    private void checkForRepeatationAndAdd(int position, String text){

        if(secilenBasliklar.size() == 0){
            secilenSozcukler.add(position);
            secilenBasliklar.add(text);
        }
        Boolean esitMi = false;
        for(int i = 0; i <secilenBasliklar.size(); i++){
            if(((secilenBasliklar.get(i).toString()).equals(text))){
                esitMi = true;
                break;
            }
        }

        if(!esitMi)
            secilenBasliklar.add(text);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){
            case R.id.fabayarlar:
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FalListe.this);
                    Intent intent = new Intent(FalListe.this, FalGoster.class);
                    intent.putExtra("secilenSozcukler", secilenSozcukler);
                    intent.putExtra("secilenBasliklar", secilenBasliklar);
                    startActivity(intent,options.toBundle());
                }else {
                    Intent intent = new Intent(FalListe.this, FalGoster.class);
                    intent.putExtra("secilenSozcukler", secilenSozcukler);
                    intent.putExtra("secilenSozcukler", secilenBasliklar);
                    startActivity(intent);
                }
                break;

        }
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



    public static int getScreenHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }




}

