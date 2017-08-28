package net.lybf.chat.receiver;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.system.Utils;
import net.lybf.chat.ui.MainActivity;

public class PushMessage extends BroadcastReceiver
  {

    @Override
    public void onReceive(Context context,Intent intent){
        MainApplication app=  (MainApplication)context.getApplicationContext();
        app.getLogcat().println(this,"接收到推送信息");
        String action=intent.getAction();
        Bundle bun=intent.getBundleExtra("data");
        Utils.print(this.getClass(),"action:"+action);
        if(action.equals("cn.bmob.push.action.MESSAGE"))
          pushMessage(context,intent.getStringExtra("msg"));
        else if(action.equals("net.lybf.chat.action.push"))
          updateApp(context,bun.getString("title"),bun.getString("message"),null);

      }

    public void pushMessage(Context context,String message){
        Log.i("MPSquare","收到的推送消息："+message);
        Toast.makeText(context.getApplicationContext(),""+message,Toast.LENGTH_LONG).show();
        Intent i = new Intent();
        i.setClass(context,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(context,0,i,0);
        NotificationCompat.Builder mBuilder =new NotificationCompat.Builder(context)
        .setTicker("MPSquare收到消息推送")
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle("消息")
        .setContentText(message)
        .setAutoCancel(true)
        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
        .setContentIntent(pi);
        // 发送通知
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = mBuilder.build();
        nm.notify(message.hashCode(),n);

      }

    private void updateApp(Context context,String title,String text,String ApkFile){
        Intent intent= new Intent();
        intent.setClass(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent Pi=
        PendingIntent.getActivity(context,0,intent,0);
        NotificationCompat.Builder mBuilder =new NotificationCompat.Builder(context)
        .setTicker("应用更新")
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(title)
        .setContentText(text)
        .setAutoCancel(true)
        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
        .setContentIntent(Pi);
        // 发送通知
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification nms= mBuilder.build();
        String str=title+text+ApkFile;
        nm.notify(str.hashCode(),nms);
      }

  }
