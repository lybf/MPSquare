package net.lybf.chat.util;
import java.text.DecimalFormat;

public class FileSize
  {

	public String getSize(float f){
		int i=0;
		while(f>1024){
            f/=1024;
            i++;
		  }
		DecimalFormat de=new DecimalFormat();
		de.applyPattern("0.00");
		String s=de.format(f);
		switch(i){
            case 0:
			  return s+"B";
            case 1:
			  return s+"KB";
            case 2:
			  return s+"MB";
            case 3:
			  return s+"GB";
            case 4:
			  return s+"TB";
            case 5:
			  return s+"PB";
            default:
			  return null;
		  }
	  }
  }
