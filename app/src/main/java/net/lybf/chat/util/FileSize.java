package net.lybf.chat.util;
import java.text.DecimalFormat;
import android.content.Context;
import net.lybf.chat.MainApplication;
import android.text.format.Formatter;

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
