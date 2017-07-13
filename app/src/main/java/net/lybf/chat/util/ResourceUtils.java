package net.lybf.chat.util;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import java.util.Locale;
import android.app.Activity;

public class ResourceUtils
  {
    /*
     CreatedAt 2017/5/31 23:31
     */
    private Resources resources;

    public ResourceUtils(Resources res){
        this.resources=res;
      }

    public ResourceUtils(Context context){
        this.resources=context.getResources();
      }

    public ResourceUtils(Activity activity){
        this.resources=activity.getResources();
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
        Configuration config=getConfiguration();
        DisplayMetrics dm=getDisplayMetrics();
        config.locale=locale;
        if(locale!=null&&dm!=null&&config!=null)
          updateConfiguration(config,dm);
      }

    public void updateConfiguration(Configuration config,DisplayMetrics dm){
        resources.updateConfiguration(config,dm);
      }

  }
