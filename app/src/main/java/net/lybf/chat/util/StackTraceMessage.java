package net.lybf.chat.util;

public class StackTraceMessage
  {
	private Throwable throwable;
    public StackTraceMessage(){
      
    }
    
    public StackTraceMessage(Throwable throwable){
        this.throwable=throwable;
      }
      
    public StackTraceMessage(Exception exception){
        this.throwable=exception;
      }

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
			String method="".format("\n    at %s.%s(%s.%s)",stack.getClassName(),
			stack.getMethodName(),
			stack.getFileName(),
			stack.getLineNumber());
            builder.append(method);
		  }
		return builder;
	  }

    public StringBuilder getMessage(Throwable throwable){
        StringBuilder builder=new StringBuilder();
        builder.append(throwable.toString());
        for(StackTraceElement stack:throwable.getStackTrace()){
            String method="".format("\n    at %s.%s(%s.%s)",stack.getClassName(),
            stack.getMethodName(),
            stack.getFileName(),
            stack.getLineNumber());
            builder.append(method);
          }
        return builder;
      }


	public String toString(){
		return "msg:"+getMessage().toString();
	  }
  }
