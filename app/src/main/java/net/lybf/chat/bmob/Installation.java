package net.lybf.chat.bmob;
import cn.bmob.v3.BmobInstallation;

public class Installation extends BmobInstallation
  {
    private MyUser user;

    public Installation setUser(MyUser user){
        this.user=user;
        return this;
      }

    public MyUser getUser(){
        return this.user;
      }
  }
