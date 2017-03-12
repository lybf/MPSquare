package net.lybf.chat.util;
import android.view.View;
import android.text.TextUtils;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import net.lybf.chat.MainApplication;

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
				StackTraceMessage stm=new StackTraceMessage();
				stm.init(e);
				new CommonUtil().print(this.getClass(),stm.getMessage().toString());
			  }
		  }
		return builder.toString();
	  }
  }
