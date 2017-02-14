package net.lybf.chat.bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class images extends BmobObject
  {
	//图片文件
	private BmobFile file;
	//md5值
	private String md5;
	//名称
	private String name;

	//获取图片文件
	public BmobFile getImage(){
		return file;
	  }

	//修改图片文件
	public void setImage(BmobFile file){
		this.file=file;
	  }

	//获取md5值
	public String getMD5(){
		return md5;
	  }

	//修改md5值
	public void setMD5(String md5){
		this.md5=md5;
	  }

	//修改名称
	public void setName(String name){
		this.name=name;
	  }

	//获取名称
	public String getName(){
		return name;
	  }
  }
