package bepp.com.bepp.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import bepp.com.bepp.R;

/**
 * Created by charlie on 05/12/17.
 */

public class AlarmSoundService  extends Service {
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Start media player
        mediaPlayer = MediaPlayer.create(this, R.raw.bip);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);//set looping true to run it infinitely
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //On destory stop and release the media player
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }
}
