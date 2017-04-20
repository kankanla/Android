package com.kankanla.m0417b;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kankanla on 2017/04/18.
 */

public class PlayerService extends IntentService {

    private MediaPlayer mediaPlayer;
    private List<Uri> Filelist;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PlayerService() {
        super("name");
        mediaPlayer = new MediaPlayer();
        Filelist = new ArrayList<Uri>();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        System.out.println("----------------onStartCommand-------------------------");
        System.out.println(intent);
        System.out.println(flags);
        System.out.println(startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("----------------onCreate-------------------------");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String uri = intent.getStringExtra("uri");
            Uri u = Uri.parse(uri);
            Filelist.add(u);

            System.out.println("sssssssssssssssssssssssssssss");
            for (Uri uri1 : Filelist) {
                System.out.println(uri1.toString());
            }
            System.out.println("sssssssssssssssssssssssssssss");

            if (mediaPlayer.isPlaying()) {
                while(mediaPlayer.isPlaying()){
                    System.out.println("matu");
                }
            } else {
                ppp();
            }
        }
    }

    private void ppp() {
        try {
//            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(getApplicationContext(), Filelist.remove(0));
//            mediaPlayer.prepareAsync();
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (Filelist.isEmpty()) {
                        mediaPlayer.release();
                    } else {
                        ppp();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        Toast.makeText(this, "Server OnDestroy", Toast.LENGTH_SHORT).show();
    }
}
