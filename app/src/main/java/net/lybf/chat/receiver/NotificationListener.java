package net.lybf.chat.receiver;
import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.AttributeSet;
import android.content.res.TypedArray;
import android.content.Intent;
import android.content.res.Configuration;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import android.os.IBinder;
import android.view.KeyEvent;
import java.io.FileNotFoundException;

public class NotificationListener extends AccessibilityService
  {

    @Override
    public void onStart(Intent intent,int startId){
        super.onStart(intent,startId);
      }

    @Override
    public void onCreate(){
        super.onCreate();
      }

    @Override
    public void onDestroy(){
        super.onDestroy();
      }




    @Override
    public void onTaskRemoved(Intent rootIntent){
        super.onTaskRemoved(rootIntent);
      }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
      }



    @Override
    public void onTrimMemory(int level){
        super.onTrimMemory(level);
      }



    @Override
    public void onLowMemory(){
        super.onLowMemory();
      }




    @Override
    public FileInputStream openFileInput(String name) throws FileNotFoundException{
        return super.openFileInput(name);
      }

    @Override
    public FileOutputStream openFileOutput(String name,int mode) throws FileNotFoundException{
        return super.openFileOutput(name,mode);
      }


    


    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        return super.onStartCommand(intent,flags,startId);
      }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name,int mode,SQLiteDatabase.CursorFactory factory){
        return super.openOrCreateDatabase(name,mode,factory);
      }


    @Override
    public SQLiteDatabase openOrCreateDatabase(String name,int mode,SQLiteDatabase.CursorFactory factory,DatabaseErrorHandler errorHandler){
        return super.openOrCreateDatabase(name,mode,factory,errorHandler);
      }


    @Override
    protected void onServiceConnected(){
        super.onServiceConnected();
      }

    @Override
    public boolean onUnbind(Intent intent){
        return super.onUnbind(intent);
      }

    @Override
    public void onRebind(Intent intent){
        super.onRebind(intent);
      }

    @Override
    protected boolean onGesture(int gestureId){
        return super.onGesture(gestureId);
      }





    @Override
    protected boolean onKeyEvent(KeyEvent event){
        return super.onKeyEvent(event);
      }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event){
      }




    @Override
    public void onInterrupt(){
      }

  }
