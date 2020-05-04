package com.shqip.tv.oni.activities;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.shqip.tv.oni.Config;
import com.shqip.tv.oni.callbacks.CallBackUserAgent;
import com.shqip.tv.oni.databases.DatabaseHandlerFavorite;
import com.shqip.tv.oni.models.Channel;
import com.shqip.tv.oni.rests.ApiInterface;
import com.shqip.tv.oni.rests.RestAdapter;
import com.shqip.tv.oni.utils.Constant;
import com.shqip.tv.oni.utils.NetworkCheck;
import com.squareup.picasso.Picasso;
import com.shqip.tv.R;
import java.util.List;
import es.dmoral.toasty.Toasty;
import io.michaelrocks.paranoid.Obfuscate;
import retrofit2.Call;
import retrofit2.Callback;


@Obfuscate
public class ActivityDetailChannel extends AppCompatActivity {

    String str_category, str_id, str_image, str_name, str_url, str_description, str_channel_type, str_video_id;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String user_agent;
    AppBarLayout appBarLayout;
    ImageView channel_image, ply;
    TextView channel_name, channel_category;
    WebView channel_description;
    Snackbar snackbar;
    View view;
    BroadcastReceiver broadcastReceiver;
    private InterstitialAd interstitialAd;
    DatabaseHandlerFavorite databaseHandler;
    FloatingActionButton floatingActionButton;
    AdView adView;
    int position;
    private Call<CallBackUserAgent> callbackCall = null;
    AlertDialog ald;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        view = findViewById(android.R.id.content);
        position = getIntent().getIntExtra("key", 0);

        if (Config.ENABLE_RTL_MODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }

        //Getting channels user agent for exo
        useragent();
        dialog();


        //Blocked Package names
        boolean PacketCapture = isPackageInstalled("app.greyshirts.sslcapture", getPackageManager());
        boolean HTTPSniffer = isPackageInstalled("com.guoshi.httpcanary", getPackageManager());
        boolean NetCapture = isPackageInstalled("com.minhui.networkcapture", getPackageManager());
        boolean PackageSniffer = isPackageInstalled("com.packagesniffer.frtparlak", getPackageManager());
        boolean PCAPRemote = isPackageInstalled("com.egorovandreyrm.pcapremote", getPackageManager());
        boolean tPacketCapture = isPackageInstalled("jp.co.taosoftware.android.packetcapt", getPackageManager());
        boolean RootSnifferPacket = isPackageInstalled("frtparlak.rootsniffer", getPackageManager());
        boolean NetworkUtilities = isPackageInstalled("com.myprog.netutils", getPackageManager());
        boolean Sniffer = isPackageInstalled("com.minhui.wifianalyzer", getPackageManager());
        if (Sniffer)  // Install
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.notallowed)
                    .setTitle(getResources().getString(R.string.msg_oops))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.MosVidh))
                    .setNegativeButton(getResources().getString(R.string.option_mbyll), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .show();
        }
        if (NetworkUtilities)  // Install
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.notallowed)
                    .setTitle(getResources().getString(R.string.msg_oops))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.MosVidh))
                    .setNegativeButton(getResources().getString(R.string.option_mbyll), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .show();
        }
        if (RootSnifferPacket)  // Install
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.notallowed)
                    .setTitle(getResources().getString(R.string.msg_oops))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.MosVidh))
                    .setNegativeButton(getResources().getString(R.string.option_mbyll), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .show();
        }
        if (tPacketCapture)  // Install
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.notallowed)
                    .setTitle(getResources().getString(R.string.msg_oops))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.MosVidh))
                    .setNegativeButton(getResources().getString(R.string.option_mbyll), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .show();
        }
        if (PCAPRemote)  // Install
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.notallowed)
                    .setTitle(getResources().getString(R.string.msg_oops))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.MosVidh))
                    .setNegativeButton(getResources().getString(R.string.option_mbyll), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .show();
        }
        if (PackageSniffer)  // Install
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.notallowed)
                    .setTitle(getResources().getString(R.string.msg_oops))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.MosVidh))
                    .setNegativeButton(getResources().getString(R.string.option_mbyll), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .show();
        }
        if (NetCapture)  // Install
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.notallowed)
                    .setTitle(getResources().getString(R.string.msg_oops))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.MosVidh))
                    .setNegativeButton(getResources().getString(R.string.option_mbyll), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .show();
        }
        if (HTTPSniffer)  // Install
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.notallowed)
                    .setTitle(getResources().getString(R.string.msg_oops))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.MosVidh))
                    .setNegativeButton(getResources().getString(R.string.option_mbyll), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .show();
        }
        if (PacketCapture)  // Install
        {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.notallowed)
                    .setTitle(getResources().getString(R.string.msg_oops))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.MosVidh))
                    .setNegativeButton(getResources().getString(R.string.option_mbyll), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .show();
        }

        loadBannerAd();
        loadInterstitialAd();

        databaseHandler = new DatabaseHandlerFavorite(getApplicationContext());
        floatingActionButton = (FloatingActionButton) findViewById(R.id.img_fav);

        Intent intent = getIntent();
        if (null != intent) {
            str_category = intent.getStringExtra(Constant.KEY_CHANNEL_CATEGORY);
            str_id = intent.getStringExtra(Constant.KEY_CHANNEL_ID);
            str_name = intent.getStringExtra(Constant.KEY_CHANNEL_NAME);
            str_image = intent.getStringExtra(Constant.KEY_CHANNEL_IMAGE);
            str_url = intent.getStringExtra(Constant.KEY_CHANNEL_URL);
            str_description = intent.getStringExtra(Constant.KEY_CHANNEL_DESCRIPTION);
            str_channel_type = intent.getStringExtra(Constant.KEY_CHANNEL_TYPE);
            str_video_id = intent.getStringExtra(Constant.KEY_VIDEO_ID);
        }

        setupToolbar();

        channel_image = (ImageView) findViewById(R.id.channel_image);
        ply = (ImageView) findViewById(R.id.ply);
        channel_name = (TextView) findViewById(R.id.channel_name);
        channel_category = (TextView) findViewById(R.id.channel_category);
        channel_description = (WebView) findViewById(R.id.channel_description);

        if (Config.ENABLE_RTL_MODE) {
            rtlLayout();
        } else {
            normalLayout();
        }

        addFavorite();

    }

    public void addFavorite() {
        List<Channel> data = databaseHandler.getFavRow(str_id);
        if (data.size() == 0) {
            floatingActionButton.setImageResource(R.drawable.ic_favorite_outline_white);
        } else {
            if (data.get(0).getChannel_id().equals(str_id)) {
                floatingActionButton.setImageResource(R.drawable.ic_favorite_white);
            }
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                List<Channel> data = databaseHandler.getFavRow(str_id);
                if (data.size() == 0) {
                    databaseHandler.AddtoFavorite(new Channel(
                            str_category,
                            str_id,
                            str_name,
                            str_image,
                            str_url,
                            str_description,
                            str_channel_type,
                            str_video_id
                    ));
                    snackbar = Snackbar.make(view, getResources().getString(R.string.favorite_added), Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    floatingActionButton.setImageResource(R.drawable.ic_favorite_white);

                } else {
                    if (data.get(0).getChannel_id().equals(str_id)) {
                        databaseHandler.RemoveFav(new Channel(str_id));
                        snackbar = Snackbar.make(view, getResources().getString(R.string.favorite_removed), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        floatingActionButton.setImageResource(R.drawable.ic_favorite_outline_white);
                    }
                }
            }
        });

    }

    private void dialog() {
        ald = new AlertDialog.Builder(this)
                .setMessage("LOADING...")
                .setCancelable(false)
                .show();
    }


    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageGids(packagename);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("");
        }

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(str_category);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

    }

    public void useragent() {
        ApiInterface apiInterface = RestAdapter.createAPI();
        callbackCall = apiInterface.getUseragent();
        callbackCall.enqueue(new Callback<CallBackUserAgent>() {
            @Override
            public void onResponse(Call<CallBackUserAgent> call, retrofit2.Response<CallBackUserAgent> response) {
                if (response.isSuccessful()) {
                    user_agent = response.body().useragent;
                    Log.d("TEST", response.body().useragent);
                    ald.dismiss();
                }else {
                    Toasty.error(ActivityDetailChannel.this, "ERROR", Toasty.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CallBackUserAgent> call, Throwable t) {
                Toasty.error(ActivityDetailChannel.this, "ERROR_505", Toasty.LENGTH_LONG).show();
                finish();
            }

        });
    }

    public void normalLayout() {

        channel_name.setText(str_name);
        channel_category.setText(str_category);

            Picasso.with(this)
                    .load(Config.ADMIN_PANEL_URL + "/upload/" + str_image)
                    .placeholder(R.drawable.ic_thumbnail)
                    .into(channel_image);


        ply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playthis();
            }
        });

        channel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playthis();
            }
        });

        channel_description.setBackgroundColor(Color.parseColor("#ffffff"));
        channel_description.setFocusableInTouchMode(false);
        channel_description.setFocusable(false);
        channel_description.getSettings().setDefaultTextEncodingName("UTF-8");

        WebSettings webSettings = channel_description.getSettings();
        Resources res = getResources();
        int fontSize = res.getInteger(R.integer.font_size);
        webSettings.setDefaultFontSize(fontSize);

        String mimeType = "text/html; charset=UTF-8";
        String encoding = "utf-8";
        String htmlText = str_description;

        String text = "<html><head>"
                + "<style type=\"text/css\">body{color: #525252;}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";

        channel_description.loadDataWithBaseURL(null, text, mimeType, encoding, null);
    }

    private void playthis() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)) {
            Log.d("HardwareFeatureTest", "Device has a touch screen.");
            if (NetworkCheck.isNetworkAvailable(ActivityDetailChannel.this)) {
                        Intent intent = new Intent(ActivityDetailChannel.this, ActivityPhoneScreen.class);
                        intent.putExtra("url", str_url);
                        intent.putExtra("user_agent", user_agent);
                        intent.putExtra("exo", true);
                        intent.putExtra("img", str_image);
                        intent.putExtra("emri", str_name);
                        startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_required), Toast.LENGTH_SHORT).show();
            }
            }else{
            if (NetworkCheck.isNetworkAvailable(ActivityDetailChannel.this)) {
                    if (str_url != null && str_url.startsWith("rtmp://")) {
                        Intent intent = new Intent(ActivityDetailChannel.this, ActivityRtmpPlayer.class);
                        intent.putExtra("url", str_url);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(ActivityDetailChannel.this, ActivityTvScreen.class);
                        intent.putExtra("url", str_url);
                        intent.putExtra("user_agent", user_agent);
                        intent.putExtra("exo", true);
                        intent.putExtra("img", str_image);
                        intent.putExtra("emri", str_name);
                        startActivity(intent);
                    }

            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_required), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void rtlLayout() {

        channel_name.setText(str_name);
        channel_category.setText(str_category);

            Picasso.with(this)
                    .load(Config.ADMIN_PANEL_URL + "/upload/" + str_image)
                    .placeholder(R.drawable.ic_thumbnail)
                    .into(channel_image);

        channel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)) {
                    Log.d("HardwareFeatureTest", "Device has a touch screen.");
                    if (NetworkCheck.isNetworkAvailable(ActivityDetailChannel.this)) {
                        Intent intent = new Intent(ActivityDetailChannel.this, ActivityPhoneScreen.class);
                        intent.putExtra("url", str_url);
                        intent.putExtra("user_agent", user_agent);
                        intent.putExtra("exo", true);
                        intent.putExtra("img", str_image);
                        intent.putExtra("emri", str_name);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_required), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if (NetworkCheck.isNetworkAvailable(ActivityDetailChannel.this)) {
                        if (str_url != null && str_url.startsWith("rtmp://")) {
                            Intent intent = new Intent(ActivityDetailChannel.this, ActivityRtmpPlayer.class);
                            intent.putExtra("url", str_url);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(ActivityDetailChannel.this, ActivityTvScreen.class);
                            intent.putExtra("url", str_url);
                            intent.putExtra("user_agent", user_agent);
                            intent.putExtra("exo", true);
                            intent.putExtra("img", str_image);
                            intent.putExtra("emri", str_name);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_required), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        channel_description.setBackgroundColor(Color.parseColor("#ffffff"));
        channel_description.setFocusableInTouchMode(false);
        channel_description.setFocusable(false);
        channel_description.getSettings().setDefaultTextEncodingName("UTF-8");

        WebSettings webSettings = channel_description.getSettings();
        Resources res = getResources();
        int fontSize = res.getInteger(R.integer.font_size);
        webSettings.setDefaultFontSize(fontSize);

        String mimeType = "text/html; charset=UTF-8";
        String encoding = "utf-8";
        String htmlText = str_description;

        String text = "<html dir='rtl'><head>"
                + "<style type=\"text/css\">body{color: #525252;}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";

        channel_description.loadDataWithBaseURL(null, text, mimeType, encoding, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.share:

                String news_heading = android.text.Html.fromHtml(getResources().getString(R.string.share_title)).toString();
                String share_text = android.text.Html.fromHtml(getResources().getString(R.string.share_content)).toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, news_heading + "\n\n" + share_text + "\n\n" + "https://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void loadBannerAd() {
        if (Config.ENABLE_ADMOB_BANNER_ADS) {
            adView = (AdView) findViewById(R.id.adView);
            adView.loadAd(new AdRequest.Builder().build());
            adView.setAdListener(new AdListener() {

                @Override
                public void onAdClosed() {
                }

                @Override
                public void onAdFailedToLoad(int error) {
                    adView.setVisibility(View.GONE);
                }

                @Override
                public void onAdLeftApplication() {
                }

                @Override
                public void onAdOpened() {
                }

                @Override
                public void onAdLoaded() {
                    adView.setVisibility(View.VISIBLE);
                }
            });

        } else {
            Log.d("AdMob", "AdMob Banner is Disabled");
        }
    }
    private void loadInterstitialAd() {
        if (Config.ENABLE_ADMOB_INTERSTITIAL_ADS_ON_PLAY_STREAMING) {
            interstitialAd = new InterstitialAd(getApplicationContext());
            interstitialAd.setAdUnitId(getResources().getString(R.string.admob_interstitial_unit_id));
            interstitialAd.loadAd(new AdRequest.Builder().build());
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
        }
    }

    private void showInterstitialAd() {
        if (Config.ENABLE_ADMOB_INTERSTITIAL_ADS_ON_PLAY_STREAMING) {
            if (interstitialAd != null && interstitialAd.isLoaded()) {
                interstitialAd.show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Constant.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Constant.PUSH_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

}
