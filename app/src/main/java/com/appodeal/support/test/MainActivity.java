package com.appodeal.support.test;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodeal.support.test.mvp.model.Weather;
import com.appodeal.support.test.mvp.presener.WeatherPresenter;
import com.appodeal.support.test.mvp.view.WeatherView;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;
import java.util.Locale;

public class MainActivity extends MvpAppCompatActivity  {

    private int seconds = Common.THIRTY_SECONDS;
    private int time = 0;
    private boolean running;
    private boolean wasRunning;
    private boolean wasShownBanner;
    private TextView timeView;
    private Button clickButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runTimer();
        setContentView(R.layout.activity_main);
        Appodeal.cache(MainActivity.this, Appodeal.NATIVE);
        Appodeal.cache(this, Appodeal.NATIVE);

        Appodeal.setTesting(true);
        Appodeal.initialize(this, Common.APPODEAL_KEY, Appodeal.INTERSTITIAL | Appodeal.BANNER | Appodeal.NATIVE );
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            time = savedInstanceState.getInt("time");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            wasShownBanner = savedInstanceState.getBoolean("wasBannerShown");
        }

        timeView = findViewById(R.id.timer);
        clickButton = findViewById(R.id.button_main);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time <= Common.THIRTY_SECONDS) // in first 30s
                {
                    isTimerStart(false);
                    Toast.makeText(MainActivity.this, "Stopping time!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button listButton = findViewById(R.id.button_weather);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                startActivity(intent);
            }
        });
        showTopBanner();
    }

    private void showTopBanner() {
        if (!wasShownBanner) {
            Appodeal.show(this, Appodeal.BANNER_TOP);

        }
        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int height, boolean isPrecache) {
                Log.d("Appodeal", "onBannerLoaded");
            }

            @Override
            public void onBannerFailedToLoad() {
                Log.d("Appodeal", "onBannerFailedToLoad");
            }

            @Override
            public void onBannerShown() {
                Log.d("Appodeal", "onBannerShown");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wasShownBanner = true;
                        Appodeal.hide(MainActivity.this, Appodeal.BANNER);
                    }
                }, Common.TIME_FOR_BANNER);

            }

            @Override
            public void onBannerClicked() {
                Log.d("Appodeal", "onBannerClicked");
            }
        });
    }

    public void isTimerStart(boolean isStart) {
        running = isStart;
    }

    public void onTimerReset() {
        running = false;
        time = 0;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
        savedInstanceState.putInt("time", time);
        savedInstanceState.putBoolean("wasBannerShown",wasShownBanner);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    public void showInterstitial() {
        if (Appodeal.isLoaded(Appodeal.INTERSTITIAL) && seconds == 0) {
            Appodeal.show(MainActivity.this, Appodeal.INTERSTITIAL);
            seconds = Common.THIRTY_SECONDS;
            Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
                @Override
                public void onInterstitialLoaded(boolean isPrecache) {
                    Log.d("Appodeal", "onInterstitialLoaded");
                }

                @Override
                public void onInterstitialFailedToLoad() {
                    Log.d("Appodeal", "onInterstitialFailedToLoad");
                }

                @Override
                public void onInterstitialShown() {
                    Log.d("Appodeal", "onInterstitialShown");
                    isTimerStart(false);
                }

                @Override
                public void onInterstitialClicked() {
                    Log.d("Appodeal", "onInterstitialClicked");
                }

                @Override
                public void onInterstitialClosed() {
                    Log.d("Appodeal", "onInterstitialClosed");
                    isTimerStart(true);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //Sets the number of seconds on the timer.
    private void runTimer() {
        running = true;
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = time / 3600;
                int minutes = (time % 3600) / 60;
                int secs = time % 60;
                String showTime = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(showTime);
                clickButton.setText(String.valueOf(seconds));
                if (running && seconds != 0) {
                    seconds--;
                    time++;
                }
                showInterstitial();

                handler.postDelayed(this, 1000);
            }
        });
    }

}
