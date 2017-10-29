package net.lybf.chat.services;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import android.media.MediaPlayer;
import java.io.File;
import java.io.IOException;
import net.lybf.chat.system.Utils;
import net.lybf.chat.utils.Logcat;
import net.lybf.chat.utils.StackTraceInfo;
import java.util.List;
import android.widget.SeekBar.OnSeekBarChangeListener;
import net.lybf.chat.system.Paths;
import android.os.AsyncTask;

/*
 @author:lybf
 @date:2017-10-13 23:45
 MediaPlayerServer
 */
public class MusicPlayer extends Service
  {


    private static MediaPlayer mediaPlayer=new MediaPlayer();

    private static MusicListener musicListener;

    private List<File> musicList;
    public void setMusicListener(MusicListener listener,int sleep){
        this.musicListener=listener;
      }

    private void initListener(){
      
        if(mediaPlayer==null||musicListener==null)
          return;
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer){
                MusicPlayer.this.musicListener.onCompletion(mediaPlayer);
              }
          });

        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener(){
            @Override
            public void onSeekComplete(MediaPlayer mediaPlayer){
                MusicPlayer.this.musicListener.onSeekComplete(mediaPlayer);
              }
          });
      }

    public MediaPlayer getMusicPlayer(){
        return this.mediaPlayer;
      }

    public void addToList(List<File> list){
        this.musicList=list;
      }

    public void setDataSource(File file){
        setDataSource(file.getAbsolutePath());
      }

    public void setDataSource(String path){
        try{
            mediaPlayer.prepare();
            mediaPlayer.setDataSource(path);
          }
        catch(Exception e){
            error(e);
          }
      }


    public void prepare(){
        try{
            mediaPlayer.prepare();
          }
        catch(Exception e){
          }
      }

    public void start(){
        mediaPlayer.start();
      }

    public void pause(){
        mediaPlayer.pause();
      }

    public void stop(){
        mediaPlayer.stop();
      }
    public void seekTo(int seek){
        mediaPlayer.seekTo(seek);
      }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
      }



    @Override
    public IBinder onBind(Intent intent){
        return new MusicBinder();
      }

    public class MusicBinder extends Binder
      {
        public MusicPlayer getServer(){
            return MusicPlayer.this;
          }
      }


    private void error(Exception e){
        Logcat.getInstance().println(new File(Paths.LOGCHAT_CRASH+"/MusicPlayer.logcat"),this,StackTraceInfo.getMessage(e));     
      }
  }
