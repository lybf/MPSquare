package net.lybf.chat.bmob;
import cn.bmob.v3.BmobObject;

public class CrashError extends BmobObject
{
  //CreatedAt 2017-06-12 22:00
  private MyUser user;
  
  private String deviceId;
  
  private String deviceInfo;
  
  private String crashMessage;
  
  public CrashError setUser(MyUser user){
    this.user=user;
    return this;
  }
  
  public MyUser getUser(){
    return this.user;
  }
  
  public CrashError setDeviceId(String id){
    this.deviceId=id;
    return this;
  }
  
  public String getDeviceId(){
    return this.deviceId;
    }
    
  public CrashError setDeviceInfo(String info){
      this.deviceInfo=info;
      return this;
    }

  public String getDeviceInfo(){
      return this.deviceInfo;
    }
    

    public CrashError setCrashMessage(String msg){
        this.crashMessage=msg;
        return this;
      }

    public String getCrashMessage(){
        return this.crashMessage;
      }
}

