package net.lybf.chat.services;
import android.media.MediaPlayer;

/*
 @author:lybf
 @date:2017-10-14 01:13
 */
public interface MusicListener
  {

    public void onSeekComplete(MediaPlayer mediaPlayer);


    public void onCompletion(MediaPlayer mediaPlayer);

  }
