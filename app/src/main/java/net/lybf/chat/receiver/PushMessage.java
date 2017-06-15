package net.lybf.chat.receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.app.PendingIntent;
import android.support.v4.app.NotificationCompat;
import net.lybf.chat.R;
import android.app.Notification;
import android.app.NotificationManager;
import net.lybf.chat.ui.MainActivity;

public class PushMessage extends BroadcastReceiver
  {

    @Override
    public void onReceive(Context context,Intent intent){
        String message = intent.getStringExtra("msg");
        Log.i("MPSquare","收到的推送消息："+message);
        Toast.makeText(context.getApplicationContext(),""+message,Toast.LENGTH_LONG).show();
        Intent i = new Intent();
        i.setClass(context, MainActivity.class);
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
        nm.notify(0,n);

      }

  }
