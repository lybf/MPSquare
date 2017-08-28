package net.lybf.chat.bmob;
import java.util.HashMap;
import java.util.Map;
public class ErrorMessage
  {
    //BmobSDK错误码列表
    private static Map<String,String> msg=new HashMap<String,String>();

    public ErrorMessage(){
        init();
      }
    
    private void init(){
        msg.put("9001","Application Id为空，请初始化.");
        msg.put("9002","解析返回数据出错");
        msg.put("9003","上传文件出错");
        msg.put("9004","文件上传失败");
        msg.put("9005","批量操作只支持最多50条");
        msg.put("9006","objectId为空");
        msg.put("9007","文件大小超过10M");
        msg.put("9008","上传文件不存在");
        msg.put("9009","没有缓存数据");
        msg.put("9010","网络超时");
        msg.put("9011","BmobUser类不支持批量操作");
        msg.put("9012","上下文为空");
        msg.put("9013","BmobObject（数据表名称）格式不正确");
        msg.put("9014","第三方账号授权失败");
        msg.put("9015","其他错误");
        msg.put("9016","无网络连接，请检查您的手机网络.");
        msg.put("9017","第三方登录错误");
        msg.put("9018","参数不能为空");
        msg.put("9019","格式不正确：手机号码、邮箱地址、验证码");
        msg.put("202","用户名已经存在");
        msg.put("203","邮箱已经存在");
        msg.put("204","必须提供一个邮箱地址");
        msg.put("205","没有找到此邮件的用户/没有找到此用户名的用户");
        msg.put("206","登录用户才能修改自己的信息。");
        msg.put("207","验证码错误");
        msg.put("208","authData不正确/authData已经绑定了其他用户账户");
        msg.put("209","该手机号码已经存在");
        msg.put("210","旧密码不正确");
        msg.put("301","邮箱格式不正确？");
        msg.put("302","不允许SDK创建表");
        //VIP付费限制相关错误码
        msg.put("1002","应用能创建的表数已达到限制");
        msg.put("1003","该表的行数已达到限制");
        msg.put("1004","该表的列数已达到限制");
        msg.put("1005","每月api请求数量已达到限制");
        msg.put("1006","该应用能创建定时任务数已达到限制");
        msg.put("1007","该应用能创建云端逻辑数已达到限制");
        msg.put("1500","你上传的文件大小已超出限制");
      }

    //获取错误信息
    public String getMessage(int ErrorCode){
        if(ErrorCode>-1)
          return msg.get(String.valueOf(ErrorCode));
        else
          return null;
      }
  }

















