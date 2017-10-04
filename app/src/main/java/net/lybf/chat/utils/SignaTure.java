package net.lybf.chat.utils;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import net.lybf.chat.MainApplication;
import net.lybf.chat.system.Utils;

public class SignaTure
  {
    private StringBuilder builder;

	private Context ctx;

	private String pkgname;

	private PackageManager manager;

	private PackageInfo packageInfo;

	private String signature;

	private Signature[] signatures;

	private MainApplication mApplication;


	public SignaTure(){
		mApplication=new MainApplication();
		ctx=mApplication.getContext();
	  }

	public SignaTure setPackageName(String packagename){
		pkgname=packagename;
		return this;
	  }

	public String getSignature(){
		builder=new StringBuilder();
		boolean isEmpty = TextUtils.isEmpty(pkgname);
		manager=ctx.getPackageManager();
		if(isEmpty){
			return "应用程序的包名不能为空!";
		  }else{
			try{
				packageInfo=manager.getPackageInfo(pkgname,PackageManager.GET_SIGNATURES);
				signatures=packageInfo.signatures;
				for(Signature signature : signatures){
					builder.append(signature);
				  }
				signature=builder.toString();
			  }catch(Exception e){
				StackTraceInfo stm=new StackTraceInfo();
				stm.init(e);
                Utils.print(this.getClass(),stm.getMessage().toString());
			  }
		  }
		return builder.toString();
	  }
  }
