package net.lybf.chat.util;
import net.lybf.chat.BuildConfig;

public  final class Debug
  {
    public static final void print(){
        System.out.println();
      }

    public static final void print(Object obj){
        System.out.println(obj);
      }

    public static final void print(String classname,Object msg){
        System.out.println(String.format("\n%s-->:%s\n",classname,msg.toString()));
      }

    public static final void print(java.lang.Class mclass,Object obj){
        System.out.println(String.format("\n%s-->%s\n",mclass.getPackage().getName(),obj));
      }


    public static final void print(java.lang.Class mclass,Exception e){
        String pkg=mclass.getName();
        System.out.println();
        System.out.println("\n\n-----------"+pkg+"---------");
        System.out.println(String.format("\n%s-->\n    %s\n",pkg,new StackTraceMessage().init(e).getMessage().toString()));
        System.out.println("\n-----------"+pkg+"---------\n\n");
        System.out.println();
      }

    public final class Log
      {
        public Log(){
          }
        public  void e(String tag,String msg){
            if(BuildConfig.DEBUG)
              android.util.Log.e(tag,msg);
          }
        public  void w(String tag,String msg){
            if(BuildConfig.DEBUG)
              android.util.Log.w(tag,msg);
          }
      }
  }
