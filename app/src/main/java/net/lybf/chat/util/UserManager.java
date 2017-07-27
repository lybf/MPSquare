package net.lybf.chat.util;
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

    private MainApplication app;

    public UserManager(){
        app=new MainApplication();
        ctx=app.getContext();
      }

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

    public UserManager DownLoadHeader(){
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
                    public void onProgress(Integer p1,long p2){

                      }
                  });

              }

          }

        return this;
      }


    public Bitmap getHead(){
        BmobFile file=use.getIcon();
        final String filename=file==null?"":file.getFilename();
        File f=new File(Paths.USER_PATH+"/"+use.getObjectId()+"/head/"+filename);
        if(!f.exists())
          DownLoadHeader();
        Bitmap mp = null;
        if(f.exists()){
            try{
                mp=Picasso.with(ctx).load(f).get();
              }catch(IOException e){}
          }
        return mp;
      }


    public File getIconFile(){
        BmobFile file=use.getIcon();
        final String filename=file==null?"":file.getFilename();
        File f=new File(Paths.USER_PATH+"/"+use.getObjectId()+"/head/"+filename);
        //if(!f.exists())
        //  DownLoadHeader();
        return f;
      }
    private void print(String e){
        new Utils().print(this.getClass(),e);
      }

  }
