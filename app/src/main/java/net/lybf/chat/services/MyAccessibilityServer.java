package net.lybf.chat.services;
import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MyAccessibilityServer extends AccessibilityService
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
    public FileInputStream openFileInput(String name)throws FileNotFoundException{
        return super.openFileInput(name);
      }

    @Override
    public FileOutputStream openFileOutput(String name,int mode) 
      throws FileNotFoundException{
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
