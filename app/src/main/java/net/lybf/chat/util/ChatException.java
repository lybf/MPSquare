package net.lybf.chat.util;

public class ChatException extends Exception
  {
    public static final int REFRESH_FAILED=1;
    public static final int COUNT_FAILED=2;
    private int code=0;
    private String msg="其他错误";

    public ChatException(String msg){
        super(msg);
        if(msg!=null)
          this.msg=msg;
      }

    public ChatException(Exception e){
        super(e);
      }
    public ChatException(Exception e,String msg){
        super(msg,e);
        if(msg!=null)
          this.msg=msg;
      }
    public ChatException(Throwable e,String msg){
        super(msg,e);
        if(msg!=null)
          this.msg=msg;
      }
    public ChatException(int code,String message){
        super(message);
        if(code>-1)
          this.code=code;
        if(message!=null)
          this.msg=message;
      }
    public ChatException(Exception e,int code,String message){
        super(message,e);
        if(code>-1)
          this.code=code;
        if(message!=null)
          this.msg=message;
      }

    public int getCode(){
        return code;
      }

    public String getMessage(){
        return msg;
      }

    public String toString(){
        return String.format("code:%s,message:%s",code,msg);
      }
  }
