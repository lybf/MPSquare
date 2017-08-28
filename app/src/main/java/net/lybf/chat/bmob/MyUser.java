package net.lybf.chat.bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

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
    //设备 绑定设备
    private BmobInstallation device;

    //获取vip级别
    public Integer getVipLevel(){
        return this.vip;
      }

    //是否是管理员
    public boolean isOP(){
        return this.op;
      }

    //是否是超级管理员
    public boolean isSuperOp(){
        return this.superop;
      }

    //获取头像
    public BmobFile getIcon(){
        return this.icon;
      }

    //修改头像
    public MyUser setIcon(BmobFile f){
        this.icon=f;
        return this;
      }

    //获取描述
    public String getdescribe(){
        return this.describe;
      }

    //是否是黑名单
    public boolean isBlackName(){
        return this.BlackName;
      }

    public MyUser setDevice(BmobInstallation Installation){
        this.device=Installation;
        return this;
      }

    public BmobInstallation getDevice(){
        return this.device;
      }


  }
