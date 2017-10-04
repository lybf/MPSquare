package net.lybf.chat.utils;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceInfo
  {
	private Throwable throwable;
	public StackTraceInfo init(Throwable throwable){
		this.throwable=throwable;
		return this;
	  }
	public StackTraceInfo init(Exception exception){
		this.throwable=exception;
		return this;
	  }

	public StringBuilder getMessage(){
        return getMessage(throwable);
	  }

    public static StringBuilder getMessage(Throwable throwable){
        StringBuilder builder=new StringBuilder();     
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while(cause!=null){
            cause.printStackTrace(printWriter);
            cause=cause.getCause();
          }
        printWriter.close();
        builder.append(writer.toString());
        return builder;
      }


	public String toString(){
		return "msg:"+getMessage().toString();
	  }
  }
