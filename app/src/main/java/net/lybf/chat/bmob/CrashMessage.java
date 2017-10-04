package net.lybf.chat.bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobInstallation;

public class CrashMessage extends BmobObject
  {
    //CreatedAt 2017-06-12 22:00
    private MyUser user;

    private String device;

    private String info;

    private String crashMessage;

    private String setps;

    private String installtionId;

    public CrashMessage(){

      }

    public CrashMessage(MyUser user,String device,String info,
    String crashMessage,String setps,String installId){
        this.user=user;
        this.device=device;
        this.info=info;
        this.crashMessage=crashMessage;
        this.setps=setps;
        this.installtionId=installId;
      }

    
    public CrashMessage setUser(MyUser user){
        this.user=user;
        return this;
      }

    public MyUser getUser(){
        return this.user;
      }

    public CrashMessage setDeviceId(String id){
        this.device=id;
        return this;
      }

    public String getDeviceId(){
        return this.device;
      }

    public CrashMessage setDeviceInfo(String info){
        this.info=info;
        return this;
      }

    public String getDeviceInfo(){
        return this.info;
      }


    public CrashMessage setCrashMessage(String msg){
        this.crashMessage=msg;
        return this;
      }

    public String getCrashMessage(){
        return this.crashMessage;
      }

    public CrashMessage setSetps(String setps){
        this.setps=setps;
        return this;
      }

    public String getSetps(){
        return this.setps;
      }

    public CrashMessage setInstalltionId(String id){
        this.installtionId=id;
        return this;
      }

    public String getInstallationId(){
        return this.installtionId;
      }
  }

