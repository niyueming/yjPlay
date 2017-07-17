package chuangyuan.ycj.videolibrary.video;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import com.google.android.exoplayer2.util.Util;
import chuangyuan.ycj.videolibrary.R;

/**
 * Created by yangc on 2017/2/27.
 * E-Mail:1007181167@qq.com
 * Description： 手动播放播放器
 */
public class ManualPlayer extends GestureVideoPlayer {
    public static final String TAG = "ManualPlayer";
    private boolean isLoad = false;//已经加载
   private ImageButton exoBtn,temptyBtn;
    public ManualPlayer(Activity activity, String url) {
        super(activity, url);
        intiView();
    }
    public ManualPlayer(Activity activity) {
        super(activity);
        intiView();
    }

    private  void intiView(){
        setExoPlayWatermarkImg(R.mipmap.watermark_big);
        exoBtn= (ImageButton) playerView.findViewById(R.id.exo_play);
        temptyBtn=(ImageButton) playerView.findViewById(R.id.exo_play_btn);
        exoBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (exoBtn!=null) {
                    exoBtn.setVisibility(View.GONE);
                    exoBtn.removeCallbacks(this);
                }
            }
        },100);
        temptyBtn.setVisibility(View.VISIBLE);
        temptyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temptyBtn.setVisibility(View.GONE);
                exoBtn.setVisibility(View.VISIBLE);
                isLoad=true;
                createPlayers();
                hslHideView();
                registerReceiverNet();
            }
        });

    }
    @Override
    public void setPlayUri(Uri uri) {
        this.mediaSourceBuilder = new ExoPlayerMediaSourceBuilder(activity.getApplicationContext(), uri);
    }

    @Override
    public void setPlayUri(String firstVideoUri, String secondVideoUri) {
        this.mediaSourceBuilder = new ExoPlayerMediaSourceBuilder(activity.getApplicationContext(), firstVideoUri, secondVideoUri);
    }

    @Override
    public void onResume() {
        if ((Util.SDK_INT <= 23 || player == null) && isLoad) {
            createPlayers();
        } else {
            createPlayersPlay();
        }
    }
}