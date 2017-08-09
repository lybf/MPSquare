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
        this.type=p;
      }

    public String getType(){
        return this.type;
      }

    public String getMessage(){
        return this.content;
      }

    public void setMessage(String str){
        this.content=str;
      }




    public String getTitle(){
        return this.title;
      }

    public void setTitle(String str){
        this.title=str;
      }


    public MyUser getUser(){
        return this.user;
      }

    public void setUser(MyUser str){
        this.user=str;
      }



    public images getImage(){
        return this.image;
      }

    public void setImage(images b){
        this.image=b;
      }


    public images getImage2(){
        return this.image2;
      }

    public void setImage2(images b){
        this.image2=b;
      }


    public images getImage3(){
        return this.image3;
      }

    public void setImage3(images b){
        this.image3=b;
      }
  }

