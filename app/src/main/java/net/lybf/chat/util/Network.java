package net.lybf.chat.util;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

public class Network
  {
	Context ctx;
	public Network(Context c){
		ctx=c;
	  }
	public boolean isNetWork(){
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo!=null&&netInfo.isConnectedOrConnecting())
		  return true;
		else
		  return false;
	  }
  }
