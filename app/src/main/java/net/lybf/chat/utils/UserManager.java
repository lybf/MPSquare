package net.lybf.chat.utils;
import android.content.Context;
import android.graphics.Bitmap;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import net.lybf.chat.MainApplication;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.system.Paths;
import net.lybf.chat.system.Utils;

public class UserManager
  {
    //CreatedAt 2017/4/29/2:03
    private MyUser use;
    private Context ctx;



    public UserManager(Context ctx){
        this.ctx=ctx;
      }

    public MyUser getCurrentUser(){
        MyUser user=BmobUser.getCurrentUser(MyUser.class);
        return user;
      }

    public UserManager setUser(MyUser use){
        this.use=use;
        return this;
      }

    public UserManager DownLoadIcon(){
        if(use!=null){
            BmobFile file=use.getIcon();
            final String filename=file==null?"":file.getFilename();
            File f=new File(Paths.USER_PATH+"/"+use.getObjectId()+"/head/"+filename);
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
        BmobFile file=use.getIcon();
        if(file==null)
          return null;
        final String filename=file.getFilename();
        File f=new File(Paths.USER_PATH+"/"+use.getObjectId()+"/head/"+filename);
        if(!f.exists())
          DownLoadIcon();
        Bitmap mp = null;
        if(f.exists()&&f.isFile()){
            try{
                mp=Picasso.with(ctx).load(f).get();
              }catch(IOException e){
                e.printStackTrace();
              }
          }
        return mp;
      }

    public Bitmap getIcon(MyUser user){
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
              }catch(IOException e){
                e.printStackTrace();
              }
          }
        return mp;
      }

    public File getIconFile(){
        BmobFile file=use.getIcon();
        if(file==null)
          return null;
        final String filename=file.getFilename();
        File f=new File(Paths.USER_PATH+"/"+use.getObjectId()+"/head/"+filename);
        if(!f.exists())
          DownLoadIcon();
        return f;
      }

    public File getIconFile(MyUser user){
        BmobFile file=user.getIcon();
        if(file==null)
          return null;
        final String filename=file.getFilename();
        File f=new File(Paths.USER_PATH+"/"+user.getObjectId()+"/head/"+filename);
        if(!f.exists())
          DownLoadIcon(user);
        return f;
      }


    private void print(String e){
        Utils.print(this.getClass(),e);
      }

  }
