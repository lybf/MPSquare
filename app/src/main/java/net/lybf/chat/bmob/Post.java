package net.lybf.chat.bmob;
import cn.bmob.v3.BmobObject;
import java.io.File;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.datatype.BmobPointer;
import net.lybf.chat.bmob.MyUser;
import org.w3c.dom.UserDataHandler;
import net.lybf.chat.bmob.images;

public class Post extends BmobObject
  {
	//标题
	private String title;
	//内容
	private String content;
	//类型
    private String type;
	//图1~图3
	private images image,image2,image3;
	//用户
	private MyUser user;

    public void setType(String p){
		type=p;
      }

    public String getType(){
		return type;
	  }

	public String getMessage(){
		if(content==null)
		  return null;
		return content;
      }

	public void setMessage(String str){
		content=str;
      }




	public String getTitle(){
		if(title==null)
		  return null;
		return title;
      }

	public void setTitle(String str){
		title=str;
      }


	public MyUser getUser(){
		if(user==null)
		  return null;
		return user;
      }

	public void setUser(MyUser str){
		user=str;
      }



	public images getImage(){
        return image;
      }

    public void setImage(images b){
        image=b;
      }


	public images getImage2(){
        return image2;
      }

    public void setImage2(images b){
        image2=b;
      }


    public images getImage3(){
        return image3;
      }

    public void setImage3(images b){
        image3=b;
      }
  }

