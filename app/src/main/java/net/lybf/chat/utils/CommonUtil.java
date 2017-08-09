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

    public void print(Object obj){
        System.out.println(obj);
      }
      
    public void print(String classname,Object msg){
        System.out.println(String.format("\n%s.class:%s\n",classname,msg.toString()));
      }
      
    public void print(java.lang.Class mclass,Object obj){
        System.out.println(String.format("\n%s.class :%s\n",mclass.toString(),obj));
      }


    public void logE(String tag,String msg){
        Log.e(tag,msg);

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
