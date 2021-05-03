package com.example.bhavdeepsinghassignment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class alaramReciever extends BroadcastReceiver {
    static  int count=0;
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder n=new NotificationCompat.Builder(context,"notify");
        n.setSmallIcon(R.drawable.ic_baseline_add_alarm_24);
        n.setContentTitle(intent.getStringExtra("head"));
        n.setContentText(intent.getStringExtra("desc"));
        n.setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat nm= NotificationManagerCompat.from(context);
        n.setAutoCancel(true);
        int repeat=intent.getIntExtra("repeat",1);
        int repeatDuration=intent.getIntExtra("repeatDuration",1);

        for(int i=1;i<=repeat;i++)
        {
            try
            {
                MediaPlayer mMediaPlayer = MediaPlayer.create(context, R.raw.windows_reminder);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.start();
                nm.notify(200,n.build());
                Log.e("TAG", "onReceive: "+repeat );
                Thread.sleep(repeatDuration*1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }
}
