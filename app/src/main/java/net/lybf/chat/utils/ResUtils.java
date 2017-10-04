package net.lybf.chat.utils;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import java.util.Locale;
import android.app.Activity;

public class ResUtils
  {
    /*
     CreatedAt 2017/5/31 23:31
     */
    private Resources resources;

    private Configuration config;

    private DisplayMetrics dm;


    private ResUtils(){
      }

    public ResUtils(Resources res){
        this.resources=res;
      }

    public ResUtils(Context context){
        this.resources=context.getResources();
      }

    public static ResUtils with(Resources resources){
        return new ResUtils(resources);
      }

    public static ResUtils with(Context context){
        return new ResUtils(context);
      }

    public AssetManager getAssets(){
        return resources.getAssets();
      }

    public Resources getResource(Context ctx){
        return ctx.getResources();
      }

    public DisplayMetrics getDisplayMetrics(){
        return resources.getDisplayMetrics();
      }

    public Configuration getConfiguration(){
        return resources.getConfiguration();
      }

    public void setLanguage(Locale locale){
        if(config==null)
          config=getConfiguration();
        if(dm==null)
          dm=getDisplayMetrics();
        config.locale=locale;
        updateConfiguration(config,dm);
      }

    public void updateConfiguration(Configuration config,DisplayMetrics dm){
        resources.updateConfiguration(config,dm);
      }

  }
