package com.indptechnologies.happybirthdayphotoframes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {



    AdRequest adRequest;
    Timer timer;
    private InterstitialAd interstitial;
    private int i=0;
    private Timer waitTimer = new Timer();;

    private boolean interstitialCanceled = false;
    TextView textView;
    Handler h;
    private ProgressBar progressBar ;
    int progress=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the splash screen
        // Hide The Status Bar
        /*if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }*/
        setContentView(R.layout.spleshscreen);
        // Find the progress bar
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        textView =(TextView)findViewById(R.id.textview);
        final long period = 100;
        timer = new Timer();

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-6443269753862035/2469578379");

        adRequest = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest);

        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (!interstitialCanceled) {
                    waitTimer.cancel();
                    interstitial.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                startApp();
            }

            @Override
            public void onAdClosed() {
                startApp();
                super.onAdClosed();
            }
        });

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100 ms
                if (i < 100) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //   textView.setText(String.valueOf(i)+"%");

                            //String.valueOf(i);
                        }
                    });
                    progressBar.setProgress(i);
                    i++;
                } else {
                    //closing the timer
                    timer.cancel();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    // close this activity
                    EditFrames.count1=0;
                    finish();
                }
            }
        }, 0, period);
    }
/*new Thread(new Runnable() {
    @Override
    public void run() {
        interstitialCanceled = true;
        Splash1.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dowork();
                startApp();
            }
        });

    }
}).start();*/


       /* waitTimer = new Timer();
        waitTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                interstitialCanceled = true;
                Splash1.this.runOnUiThread(new Runnable() {


                    @Override
                    public void run() {

                        dowork();

                        startApp();
                    }
                });
            }
        }, 5000);
    }*/
        /*  new Thread(new Runnable() {
            @Override
            public void run() {

               dowork();
                startApp();
                finish();
            }
        }).start();
        // setProgressValue(progress);
    }
*/






    private void startApp1() {
        for(int progress=0; progress<100; progress+=10){

            try {

                progressBar.setProgress(progress);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            catch (Exception e){

                e.printStackTrace();
                //TtsSpan.TimeBuilder.e.(e.getMessage());

            }

        }

    }

    public void dowork(){
        for (int progress=0; progress<100; progress+=10){

            try {
                Thread.sleep(1000);
                progressBar.setProgress(progress);

            }catch (Exception e){
                e.printStackTrace();

            }
        }
    }

    private void startApp(){

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }



    @Override
    public void onPause() {
        timer.cancel();
        interstitialCanceled = true;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (interstitial.isLoaded()) {
            interstitial.show();
        } else if (interstitialCanceled) {
            startApp();
        }
    }









}


