package net.lybf.chat.bmob;
import cn.bmob.v3.BmobObject;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.bmob.images;
public class Comment extends BmobObject
  {
    //内容
    private String content;
    //图1～图3
    private images  image,image2,image3;
    //父类，所属贴子
    private String parent;
    //用户
    private MyUser user;

    //获取内容
    public String getMessage(){
        if(content!=null)
          return content;
        return null;
      }

    //修改内容(设置内容)
    public void setMessage(String str){
        content=str;
      }

    //获取用户
    public MyUser getUser(){
        return user;
      }

    //设置用户
    public void setUser(MyUser user){
        this.user=user;
      }


    /*
     public Post getPost()
     {
     return post;
     }


     public void setPost(Post p)
     {
     post=p;
     }*/

    //获取所属贴子ID
    public String getParent(){
        if(parent!=null)
          return parent;
        return null;
      }

    //设置所属贴子ID
    public void setParent(String p){
        parent=p;
      }

    //获取图片1
    public images getImage(){
        if(image!=null)
          return image;
        return null;
      }
    //设置图1
    public void setImage(images f){
        image=f;
      }


    public images getImage2(){
        return image2;
      }
    public void setImage2(images f){
        image2=f;
      }


    public images getImage3(){
        return image3;
      }
    public void setImage3(images f){
        image3=f;
      }

  }
