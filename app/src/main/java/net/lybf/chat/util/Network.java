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

	public boolean isConnected(){
		ConnectivityManager cm=(ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=cm.getActiveNetworkInfo();
		if(info!=null)
		  return info.isConnected();
		else
		  return false;
	  }
	  
	  
	public boolean isConnectedOrConnecting(){
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo!=null&&netInfo.isConnectedOrConnecting())
		  return true;
		else
		  return false;
	  }
  }
