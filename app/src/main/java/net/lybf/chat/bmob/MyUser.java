package net.lybf.chat.bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import java.io.File;

//用户类
public class MyUser extends BmobUser
  {
	//头像
	private BmobFile icon;
	//描述，个性标签
	private String describe;
	//黑名单？
	private boolean BlackName;
	//vip级别
	private Integer vip;
	//管理员
	private boolean op;
	//超级管理员
	private boolean superop;
	
	//获取vip级别
	public Integer getVipLevel(){
		return vip;
	  }
	  
	//是否是管理员
	public boolean isOP(){
		return op;
	  }
	  
	//是否是超级管理员
	public boolean isSuperOp(){
		return superop;
	  }
	  
	//获取头像
	public BmobFile getIcon(){
		return icon;
	  }

	//修改头像
	public void setIcon(BmobFile f){
		icon=f;
	  }

	//获取描述
	public String getdescribe(){
		return describe;
	  }

	//是否是黑名单
	public boolean isBlackName(){
		return BlackName;
	  }




  }
