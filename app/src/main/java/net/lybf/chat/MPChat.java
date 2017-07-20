package net.lybf.chat;
import android.content.Context;

public class MPChat
{
  
  
  static{
    System.loadLibrary("MPChat");
  }
  
  public native String getKey();
  
  public native boolean BmobInit(Context context);
  
}
