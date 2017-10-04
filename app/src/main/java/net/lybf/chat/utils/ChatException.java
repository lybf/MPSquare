package net.lybf.chat.utils;
import android.util.Log;

public class ChatException extends Exception
  {
    public static final int REFRESH_FAILED=1;
    public static final int COUNT_FAILED=2;

    private int code;

    private String msg;

    private String TAG="ChatException";

    public ChatException(String info){
        super(info);
        if(info!=null)
          this.msg=info;
        this.code=0;
      }


    public ChatException(Exception e){
        super(e);
        this.msg="其他错误";
        this.code=0;
      }


    public ChatException(String info,int code){
        super(info);
        this.msg=info;
        this.code=code;
      }
    

    public ChatException(Exception e,String msg){
        super(msg,e);
        if(msg!=null)
          this.msg=msg;
        this.code=0;
      }

    public ChatException(Throwable e,String msg){
        super(msg,e);
        if(msg!=null)
          this.msg=msg;
        this.code=0;
      }

    public ChatException(Exception e,int code,String message){
        super(message,e);
        if(code>-1)
          this.code=code;
        if(message!=null)
          this.msg=message;
      }

    public int getCode(){
        return this.code;
      }

    public String getMessage(){
        return this.msg;
      }

    public String toString(){
        return String.format("code:%s,error:%s",code,msg);
      }

    @Override
    public void printStackTrace(){
        StringBuilder build=new StringBuilder();
        build.append(new StackTraceInfo().init(this).getMessage().toString());
        Log.e(TAG,build.toString());
      }

  }
