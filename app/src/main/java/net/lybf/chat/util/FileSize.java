package net.lybf.chat.util;
import android.content.Context;
import android.text.format.Formatter;
import net.lybf.chat.MainApplication;

public class FileSize
  {

	private Context ctx;
	public FileSize(){
	  ctx=new MainApplication().getContext();
	}
	public String getFileSize(long f){
		 return Formatter. formatFileSize(ctx,f);
	  }
  }
