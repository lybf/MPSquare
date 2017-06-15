package net.lybf.chat.system;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import java.lang.reflect.Method;
import net.lybf.chat.receiver.AdminReceiver;

public class AdminManager
  {
    //CreatedAt 2017/5/12 23:20
    private DevicePolicyManager dpm;

    private ComponentName mDeviceAdminSample;

    private Context ctx;

    public AdminManager(Context ctx){
        this.ctx=ctx;
        dpm=(DevicePolicyManager) ctx.getSystemService(ctx.DEVICE_POLICY_SERVICE);
        mDeviceAdminSample=new ComponentName(ctx,AdminReceiver.class);
      }

    public DevicePolicyManager getDevicePolicyManager(){
        return dpm;
      }

    public ComponentName getComponetName(){
        return mDeviceAdminSample;
      }

    public Intent startAdmin(){
        Intent intent = null;
        if(!isOpen()){
            intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"开启后就可以全面管理手机啦");
            
            Class css=ctx.getClass();
            Method method;
            try{
                method=css.getDeclaredMethod("startActivity",css);
                method.setAccessible(true);
                method.invoke(intent);
              }catch(Exception e){
                Utils.print(this.getClass(),e);
              }
          }
        return intent;
      }

    public AdminManager removeAdmin(){
        dpm.removeActiveAdmin(mDeviceAdminSample);
        return this;
      }


    /**
     * 检测用户是否开启了超级管理员
     */
    private boolean isOpen(){
        return dpm.isAdminActive(mDeviceAdminSample);
      }

    public AdminManager lock(){
        dpm.lockNow();//锁屏
        return this;
      }



    public AdminManager wipeExternalStorage(){
        dpm.wipeData(dpm.WIPE_EXTERNAL_STORAGE);
        return this;
      }

    public AdminManager wipeResetProtectionData(){
        dpm.wipeData(dpm.WIPE_RESET_PROTECTION_DATA);
        return this;
      }


  }
