package net.lybf.chat.utils;
import android.content.Context;
import android.util.Log;
import net.lybf.chat.MainApplication;

public class CommonUtil
  {
    private Context ctx;

    private MainApplication mApplication;

    public CommonUtil(){
        mApplication=new MainApplication();
        ctx=mApplication.getContext();
      }

    public  int dip2px(float dpValue){
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue*scale+0.5f);
      }
    public  int px2dip(float pxValue){
        final float scale =ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale+0.5f);
      }
  }
