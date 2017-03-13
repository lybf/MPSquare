package net.lybf.chat.system;
import java.io.File;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class update
  {
	public static final Number TYPE_HTML=0;
	public static final Number TYPE_TEXT=1;
	private Number showType;
	private String content;
	private String apkurl;
	private Number versionCode;
	private String versionName;
	private Number level;

	private String createdAt;

	private String updatedAt;

	private String title;

	public update setTitle(String title){
		this.title=title;
		return this;
	  }
	
	public String getTitle(){
		return this.title;
	  }

	public update setShowType(Number i){
		this.showType=i;
		return this;
	  }

	public Number getShowType(){
		return this.showType;
	  }

	public update setContent(String content){
		this.content=content;
		return this;
	  }

	public String getContent(){
		return this.content;
	  }

	public update setApkUrl(String url){
		this.apkurl=url;
		return this;
	  }

	public String getApkUrl(){
		return this.apkurl;
	  }

	public update setVersionCode(Number num){
		this.versionCode=num;
		return this;
	  }

	public Number getVersionCode(){
		return this.versionCode;
	  }

	public update setVersionName(String name){
		this.versionName=name;
		return this;
	  }

	public String getVersionName(){
		return this.versionName;
	  }

	public update setLevel(Number num){
		this.level=num;
		return this;
	  }

	public Number getLevel(){
		return this.level;
	  }


	public update setCreatedAt(String created){
		this.createdAt=created;
		return this;
	  }
	public String getCreatedAt(){
		return this.createdAt;
	  }



	public update setUpdatedAt(String update){
		this.updatedAt=update;
		return this;
	  }
	public String getUpdatedAt(){
		return this.updatedAt;
	  }

  }
