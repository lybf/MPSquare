package net.lybf.chat.bmob;
import cn.bmob.v3.BmobObject;

//应用更新类
public class UpdateLog extends BmobObject
  {
	//版本号
	private Number versionCode;
	//版本名
	private String versionName;
	//安装包地址
	private String Apk;
	//标题
	private String title;
	//更新内容
	private String describe;
	/*更新级别如下
	 1:普通升级
	 2:建议升级
	 3:强烈交易升级
	 4:非常强烈滴升级
	 5:强制升级*/
	private Number level;

	private Number showType;
	
	public Number getShowType(){
		return this.showType;
	  }

	//获取版本名
	public String getVersionName(){
		return versionName;
	  }
	 //获取更新级别
	public Number getLevel(){
		return level==-1?0:level;
	  }
	 //获取版本号
	public Number getVersionCode(){
		return versionCode;
	  }
	//获取下载地址
	public String getApkFile(){
		return Apk;
	  }
	//获取标题
	public String getTile(){
		return title;
	  }
	 //获取更新信息
	public String getMessage(){
		return describe;
	  }
  }
