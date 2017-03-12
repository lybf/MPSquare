package net.lybf.chat.util;

public class StackTraceMessage
  {
	private Throwable throwable;
	public StackTraceMessage init(Throwable throwable){
		this.throwable=throwable;
		return this;
	  }
	public StackTraceMessage init(Exception exception){
		this.throwable=exception;
		return this;
	  }

	public StringBuilder getMessage(){
		StringBuilder builder=new StringBuilder();
		builder.append(throwable.toString());
		for(StackTraceElement stack:throwable.getStackTrace()){
			builder.append("\n    at "+stack.getClassName()
			+"."+stack.getMethodName()
			+"("+stack.getFileName()
			+"."+stack.getLineNumber()+")");
		  }
		return builder;
	  }

	public String toString(){
		return "msg:"+getMessage();
	  }
  }
