package net.lybf.chat.utils;
import android.content.Context;
import android.graphics.Bitmap;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.system.Paths;
import net.lybf.chat.system.Utils;

public class UserManager
  {
    //CreatedAt 2017/4/29/2:03
    private MyUser use;
    private Context ctx;


    private UserManager(){

      }

    public UserManager(Context ctx){
        this.ctx=ctx;
      }

    public UserManager(Context context,MyUser user){
        this.ctx=context;
        this.use=user;
      }

    public static UserManager with(Context context){
        return new UserManager(context);
      }

    public static UserManager with(Context context,MyUser user){
        return new UserManager(context,user);
      }


    public static MyUser getCurrentUser(){
        MyUser user=BmobUser.getCurrentUser(MyUser.class);
        return user;
      }

    public UserManager setUser(MyUser use){
        this.use=use;
        return this;
      }

    public UserManager DownLoadIcon(){
        DownLoadIcon(this.use);
        return this;
      }

    public UserManager DownLoadIcon(MyUser user,DownloadFileListener listener){
        BmobFile file=user.getIcon();
        if(file!=null){
            File fp=new File(Paths.USER_PATH+"/"+user.getObjectId()+"/head/"+file.getFilename());
            file.download(fp,listener);
          }
        return this;
      }

    public UserManager DownLoadIcon(MyUser user){
        if(user!=null){
            BmobFile file=user.getIcon();
            if(file==null)
              return this;
            final String filename=file.getFilename();
            File f=new File(Paths.USER_PATH+"/"+user.getObjectId()+"/head/"+filename);
            if(!f.exists()){
                file.download(f,new DownloadFileListener(){
                    @Override
                    public void done(String p1,BmobException p2){
                        if(p2==null){
                            print(filename+"  DownloadSuccess");
                          }else{
                            print(filename+"  DownloadError -->"+p2.toString());
                          }
                      }
                    @Override
                    public void onProgress(Integer p1,long p2){}
                  });
              }
          }
        return this;
      }

    public Bitmap getIcon(){
        return getIcon(use);
      }

    public Bitmap getIcon(MyUser user){
        if(user==null)
          return null;
        BmobFile file=user.getIcon();
        if(file==null)
          return null;
        final String filename=file.getFilename();
        File f=new File(Paths.USER_PATH+"/"+user.getObjectId()+"/head/"+filename);
        if(!f.exists())
          DownLoadIcon(user);
        Bitmap mp = null;
        if(f.exists()){
            try{
                mp=Picasso.with(ctx).load(f).get();
              }
            catch(IOException e){
                e.printStackTrace();
              }
          }
        return mp;
      }

    public File getIconFile(){
        return getIconFile(use);
      }

    public File getIconFile(MyUser user){
        if(user==null)
          return null;
        BmobFile file=user.getIcon();
        if(file==null)
          return null;
        final String filename=file.getFilename();
        File f=new File(Paths.USER_PATH+"/"+user.getObjectId()+"/head/"+filename);
        if(!f.exists())
          DownLoadIcon(user);
        return f;
      }


    public boolean isEmailVerified(){
        return isEmailVerified(use);
      }

    /*
     邮箱是否验证过
     */
    public static boolean isEmailVerified(MyUser user){
        boolean verify=false;
        if(!(user.getEmail()==null))
          if(user.getEmailVerified())
            verify=true;
        return verify;
      }

    /*
     以用户名查询用户信息
     */
    public static void queryUserByName(String name,FindListener<MyUser> listener){
        queryUserByKeyValue("username",name,listener);
      }

    /*
     以用户唯一id查询用户信息
     */
    public static  void queryUserById(String id,FindListener<MyUser> listener){
        queryUserByKeyValue("objectid",id,listener);
      }
    
    /*
     以key-value形式查询用户信息
     */
    public static void queryUserByKeyValue(String key,String value,FindListener<MyUser> listener){
        BmobQuery<MyUser> query=new BmobQuery<MyUser>();
        query.addWhereEqualTo(key,value);
        query.findObjects(listener);
      }

    private void print(String e){
        Utils.print(this.getClass(),e);
      }

  }
