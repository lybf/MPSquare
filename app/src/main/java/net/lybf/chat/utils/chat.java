package net.lybf.chat.utils;
import android.content.Context;

public class chat
  {
    static{
        System.loadLibrary("chat");
      }

    public native String getKey();
    public native boolean BmobInitialize(Context context);
  }
