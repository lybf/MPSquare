package net.lybf.chat.util;

public class ChatException extends Exception
  {
	private int REFRESH_FAILED=1;
	private int COUT_FAILED=2;
	private int code;
	private String msg;

	public ChatException(int code,String message){
		super(message);
		this.code=code;
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
