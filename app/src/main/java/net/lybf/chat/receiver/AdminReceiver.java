package net.lybf.chat.receiver;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class AdminReceiver extends DeviceAdminReceiver
{
  //CreatedAt 2017/5/12 22:53
  
  private Context ctx;
  
  public AdminReceiver(){
    
  }
  
  public AdminReceiver(Context ctx){
    this.ctx=ctx;
    }

  @Override
  public void onPasswordChanged(Context context,Intent intent){
      // TODO: Implement this method
      super.onPasswordChanged(context,intent);
    }
  
  
  
}
