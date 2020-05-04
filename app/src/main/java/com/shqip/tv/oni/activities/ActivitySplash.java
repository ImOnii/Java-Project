package com.shqip.tv.oni.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.shqip.tv.oni.Config;
import com.shqip.tv.R;

import io.michaelrocks.paranoid.Obfuscate;


@Obfuscate
public class ActivitySplash extends AppCompatActivity {

    Boolean isCancelled = false;
    private ProgressBar progressBar;
    Handler handler;
    boolean validate = true;
    AlertDialog alds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


//        showPiracyActivityIfNeeded(ActivitySplash.this);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        handler = new Handler();
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                                    Intent intent = new Intent(ActivitySplash.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                    }, Config.SPLASH_TIME);
                }
            });

    }

//
//    public void showPiracyActivityIfNeeded(final Activity activity) {
//        if (!BuildConfig.DEBUG) {
//            new PiracyChecker(activity)
//                    .enableSigningCertificates("Here Goes My Certificate To Add more security so it can't be resigned by decompiling the apk")
//                    .callback(new PiracyCheckerCallback() {
//                        @Override
//                        public void doNotAllow(PiracyCheckerError piracyCheckerError, PirateApp pirateApp) {
//                            finish();
//                            validate = false;
//                        }
//                        @Override
//                        public void allow() {
//                            validate = true;
//                        }
//                    })
//                    .start();
//        }
//    }

    private void dialogthis() {
        alds = new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Aplikacioni esht i pirateruar!")
                .setPositiveButton("Mbyll", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("MY URL TO MY ORIGINAL APK AFTER DETECTING THAT THE APP HAS BEEN DECOMPILED")));
                    }
                })
                .setCancelable(false)
                .show();
    }

}
