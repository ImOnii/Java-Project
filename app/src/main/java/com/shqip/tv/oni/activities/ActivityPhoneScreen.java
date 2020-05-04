package com.shqip.tv.oni.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.shqip.tv.R;
import com.shqip.tv.oni.Config;
import com.shqip.tv.oni.adapters.AdapterChannel_exo;
import com.shqip.tv.oni.callbacks.CallbackChannel;
import com.shqip.tv.oni.models.Channel;
import com.shqip.tv.oni.rests.ApiInterface;
import com.shqip.tv.oni.rests.RestAdapter;
import com.shqip.tv.oni.utils.Constant;
import com.shqip.tv.oni.utils.SpacesItemDecoration;
import com.squareup.picasso.Picasso;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.michaelrocks.paranoid.Obfuscate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@Obfuscate
public class ActivityPhoneScreen extends AppCompatActivity {

    private static final String TAG = "ActivityStreamPlayer";
    String emri_kanalit, user_agent;
    private PlayerView playerView;
    private ExoPlayer player;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private DataSource.Factory mediaDataSourceFactory;
    private Handler mainHandler;
    TextView textView;
    TextView calendar_timer;
    TextView emri_kanalit_m;
    ImageView foto_kanalit;
    String channel_image;
    LinearLayout progresi;
    TextClock textClock;
    String url_player;
    InputStream in;
    View mRoot;
    Parcelable recylerViewState;
    RecyclerView recyclerView;
    private Call<CallbackChannel> callbackCall = null;
    private int post_total = 0;
    private AdapterChannel_exo adapterChannel;
    LinearLayout kanalet;
    Button b_list_exo;
    LinearLayout kontroll_exo;
    boolean exo_kontroll;
    Button mbyll;
    int number;
    ViewTreeObserver vto;
    boolean thistime = true;
    ImageView music;



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                );
            }
        }
    }
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_streaming);


        kontroll_exo = findViewById(R.id.kontroll_exo);
        b_list_exo = findViewById(R.id.list_exo);
        progresi = findViewById(R.id.progresi);
        textClock = findViewById(R.id.ora);
        textView = findViewById(R.id.textView14);
        textView = findViewById(R.id.emrik);
        emri_kanalit_m = findViewById(R.id.emri_kanalit);
        foto_kanalit = findViewById(R.id.foto_kanalit);
        calendar_timer = findViewById(R.id.calendar_data);
        recyclerView = findViewById(R.id.kanalet_exo);
        mbyll = findViewById(R.id.mbyll_listen_exo);
        music = findViewById(R.id.music);

        url_player = getIntent().getStringExtra("url");
        emri_kanalit = getIntent().getStringExtra("emri");
        user_agent = getIntent().getStringExtra("user_agent");
        channel_image = getIntent().getStringExtra("img");
        exo_kontroll = getIntent().getBooleanExtra("exo", true);
        number = getIntent().getIntExtra("pos", 0);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int position=sp.getInt("key",0);

        kanalet = findViewById(R.id.kanalet_n);
        if (exo_kontroll) {
            kanalet.setVisibility(View.GONE);
        }else {
            kontroll_exo.setVisibility(View.GONE);
            kanalet.setVisibility(View.VISIBLE);
        }
        mbyll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               kanalet.setVisibility(View.GONE);
               kontroll_exo.setVisibility(View.VISIBLE);
            }
        });
        b_list_exo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(position +3);
                kontroll_exo.setVisibility(View.GONE);
                kanalet.setVisibility(View.VISIBLE);
            }
        });

        recyclerView.setHasFixedSize(true);

        if (Config.ENABLE_GRID_MODE_2) {
            recyclerView.setLayoutManager(new LinearLayoutManager(ActivityPhoneScreen.this, RecyclerView.VERTICAL,false));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(ActivityPhoneScreen.this, Config.GRID_SPAN_COUNT));
            recyclerView.addItemDecoration(new SpacesItemDecoration(Config.GRID_SPAN_COUNT, Constant.SPACE_ITEM_DECORATION, true));
        }


        //set data and list adapter
        adapterChannel = new AdapterChannel_exo(ActivityPhoneScreen.this, recyclerView, new ArrayList<Channel>());
        recyclerView.setAdapter(adapterChannel);

        vto = recyclerView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (recyclerView.getChildCount() > 0 && ActivityPhoneScreen.this.thistime){
                    ActivityPhoneScreen.this.thistime = false;
                    recyclerView.scrollToPosition(number +3);
                }
            }
        });



        // on item list clicked
        adapterChannel.setOnItemClickListener(new AdapterChannel_exo.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Channel obj, int position) {
                player.stop();
                finish();
                Intent intent = new Intent(ActivityPhoneScreen.this, ActivityPhoneScreen.class);
                intent.putExtra("emri", obj.channel_name);
                intent.putExtra("key", position);
                intent.putExtra("pos", position);
                intent.putExtra("exo", false);
                intent.putExtra("img", obj.channel_image);
                intent.putExtra("url", obj.channel_url);
                intent.putExtra("user_agent", user_agent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        // detect when scroll reach bottom
        adapterChannel.setOnLoadMoreListener(new AdapterChannel_exo.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int current_page) {
                if (post_total > adapterChannel.getItemCount() && current_page != 0) {
                    int next_page = current_page + 1;
                    requestAction(next_page);
                } else {
                    adapterChannel.setLoaded();
                }
            }
        });
        requestAction(1);
//        End of recyclerView

        textView.setText("Time:");
        textClock.setFormat12Hour("HH:mm");


        if (url_player.contains(".mp4") || url_player.contains(".mov") ){
            progresi.setVisibility(View.VISIBLE);
            b_list_exo.setVisibility(View.GONE);
        }

        if (url_player.contains(".mp3")){
            music.setVisibility(View.VISIBLE);
        }

        emri_kanalit_m.setText(emri_kanalit);
        textView.setText("Loading "+emri_kanalit+"...");

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        calendar_timer.setText("Date: "+currentDate);

        Picasso.with(ActivityPhoneScreen.this)
                .load(Config.ADMIN_PANEL_URL + "/upload/" + channel_image.replace(" ", "%20"))
                .into(foto_kanalit);

        mediaDataSourceFactory = buildDataSourceFactory(true);

        mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        RenderersFactory renderersFactory = new DefaultRenderersFactory(ActivityPhoneScreen.this);

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, trackSelector, loadControl);

        LayoutInflater inflate = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mRoot = inflate.inflate(R.layout.custom_controls, null);

        playerView = findViewById(R.id.exoPlayerView);
        playerView.setPlayer(player);
        playerView.setUseController(true);
        playerView.setControllerShowTimeoutMs(4000);
        playerView.setControllerAutoShow(true);
        playerView.setControllerHideOnTouch(true);
        playerView.requestFocus();

        Uri uri = Uri.parse(url_player);

        MediaSource mediaSource = buildMediaSource(uri, null);

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
                Log.d(TAG, "onTimelineChanged: ");

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.d(TAG, "onTracksChanged: " + trackGroups.length);
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.d(TAG, "onLoadingChanged: " + isLoading);

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.d(TAG, "onPlayerStateChanged: " + playWhenReady);
                if (playbackState == PlaybackStateCompat.STATE_PLAYING) {
                    textView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.e(TAG, "onPlayerError: ", error);
                player.stop();
                errorDialog();
            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                Log.d(TAG, "onPositionDiscontinuity: true");
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        Log.d("INFO", "ActivityVideoPlayer");

    }

    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri)
                : Util.inferContentType("." + overrideExtension);
        switch (type) {
            case C.TYPE_SS:
                return new SsMediaSource.Factory(new DefaultSsChunkSource.Factory(mediaDataSourceFactory), buildDataSourceFactory(false)).createMediaSource(uri);
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(new DefaultDashChunkSource.Factory(mediaDataSourceFactory), buildDataSourceFactory(false)).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    public DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(user_agent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recylerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
        finish();
    }

    public void errorDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.stat_sys_upload_done)
                .setTitle(getResources().getString(R.string.mx_title))
                .setCancelable(false)
                .setMessage(getResources().getString(R.string.mx_msg))
                .setNegativeButton(getResources().getString(R.string.option_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }

    private void requestListPostApi(final int page_no) {
        ApiInterface apiInterface = RestAdapter.createAPI();
        callbackCall = apiInterface.getPostByPage(page_no, Config.LOAD_MORE_2);
        callbackCall.enqueue(new Callback<CallbackChannel>() {
            @Override
            public void onResponse(Call<CallbackChannel> call, Response<CallbackChannel> response) {
                CallbackChannel resp = response.body();
                if (resp != null && resp.status.equals("ok")) {
                    post_total = resp.count_total;
                    displayApiResult(resp.posts);
                } else {
                    onFailRequest(page_no);
                }
            }

            @Override
            public void onFailure(Call<CallbackChannel> call, Throwable t) {
                if (!call.isCanceled()) onFailRequest(page_no);
            }

        });
    }
    private void onFailRequest(int page_no) {
        adapterChannel.setLoaded();
    }



    private void displayApiResult(final List<Channel> channels) {
        adapterChannel.insertData(channels);
        if (channels.size() == 0) {
            Toast.makeText(this, "Did not find any DATA", Toast.LENGTH_LONG).show();
        }
    }

    private void requestAction(final int page_no) {
        if (page_no == 1) {
            //do nothing
        } else {
            //load more
            adapterChannel.setLoading();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestListPostApi(page_no);
            }
        }, Constant.DELAY_TIME);
    }

}