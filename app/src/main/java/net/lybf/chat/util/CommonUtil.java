package net.lybf.chat.util;
import android.content.Context;

public class CommonUtil
  {
	private Context ctx;
	public CommonUtil(Context c){
		ctx=c;
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
