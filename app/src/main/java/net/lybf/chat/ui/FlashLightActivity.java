package net.lybf.chat.ui;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import net.lybf.chat.R;
import net.lybf.chat.activity.MPSActivity;
import net.lybf.chat.system.Colors;
import net.lybf.chat.utils.BitmapTools;
public class FlashLightActivity extends MPSActivity
  {
    //Created by lybf on 2017/8/2 21:03
    
    //相机
    private static Camera camera=null;

    private static boolean isOpen=false;

    private ImageButton mswitch;

    private static BitmapTools bm;

    private static Context ctx;

    private RadioGroup radiogroup;

    public static int id = 0;

    private FlashLightActivity.Flashlight flashlight;

    private Camera.Parameters par;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        super.autoDark(true);
        setContentView(R.layout.activity_flashlight);
        ctx=this;
        bm=new BitmapTools();
        initView();
      }
      
    private void initView(){
        radiogroup=(RadioGroup)findViewById(R.id.flashlight_radioGroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup view,int id){
                View v=view.findViewById(id);
                if(!v.isPressed()){
                    return;
                  }
                FlashLightActivity.this.id=id;
              }
          });
        mswitch=(ImageButton)findViewById(R.id.flashlight_switchMode);
        closeIcon();
        flashlight=new Flashlight();
        mswitch.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                switch(id){
                    case R.id.flashlight_mode_common:
                      flashlight.common();
                      break;
                    case R.id.flashlight_mode_sos:
                      if(!isOpen){
                          isOpen=true;
                          openIcon();
                          flashlight.sos();
                        }else{
                          closeIcon();
                          isOpen=false;
                          closeFlashLight();
                        }
                      break;

                    default:
                      // flashlight.common();
                      break;
                  }
              }
          });
        init();
      }

    @Override
    protected void onDestroy(){
        if(camera!=null){
            isOpen=false;
            closeFlashLight();
            //camera.release();
          }
        camera=null;
        Toast.makeText(this,"已经为你自动关闭手电筒",Toast.LENGTH_SHORT).show();
        super.onDestroy();
      }

    private void init(){
        camera=Camera.open();
        par=camera.getParameters();
      }

    private class Flashlight
      {
        public void common(){
            if(!isOpen){
                isOpen=true;
                openIcon();
                openFlashLight();
                try{
                    Thread.sleep(100);
                  }catch(InterruptedException e){
                    e.printStackTrace();
                  }
              }else{
                isOpen=false;
                closeIcon();
                closeFlashLight();
                try{
                    Thread.sleep(100);
                  }catch(InterruptedException e){
                    e.printStackTrace();
                  }
              }
          }

        Handler h=new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch(msg.what){
                    case 0:
                      openFlashLight();
                      break;
                    case 1:
                      closeFlashLight();
                      break;
                  }
              }

          };
          
        public void sos(){
            new Thread(new Thread(){
                public boolean check(){
                    if(!isOpen)return true;
                    return false;
                  }
                public void run(){
                    while(isOpen){
                        try{
                            for(int i=0;i<3;i++){
                                if(!isOpen)return;
                                h.sendEmptyMessage(0);
                                if(!isOpen){
                                    h.sendEmptyMessage(1);
                                    return;
                                  }
                                this.sleep(500);
                                h.sendEmptyMessage(1);
                                this.sleep(500);
                              }

                            this.sleep(1500);
                            for(int i=0;i<3;i++){
                                h.sendEmptyMessage(0);
                                if(!isOpen){
                                    h.sendEmptyMessage(1);
                                    return;
                                  }
                                this.sleep(1000);
                                h.sendEmptyMessage(1);
                                this.sleep(1000);
                              }
                            this.sleep(2000);
                          }catch(Exception e){
                            e.printStackTrace();
                          }
                      }
                  }
              }).start();
          }
      }

    private void openIcon(){
        if(getSettings().isDark()){
            Bitmap p= bm.load(ctx.getResources(),R.drawable.ic_flashlight);
            mswitch.setImageBitmap(bm.setColor(Colors.gray,Colors.white,p));
          }else{
            mswitch.setImageBitmap(bm.load(ctx.getResources(),R.drawable.ic_flashlight));
          }
      }

    private void closeIcon(){
        if(getSettings().isDark()){
            Bitmap p=bm.load(ctx.getResources(),R.drawable.ic_flashlight_off);
            mswitch.setImageBitmap(bm.setColor(Colors.gray,Colors.white,p));
          }else{
            mswitch.setImageBitmap(bm.load(ctx.getResources(),R.drawable.ic_flashlight_off));
          }
      }

    private void openFlashLight(){
        if(camera==null)
          camera=camera.open();
        if(par==null)
          par=camera.getParameters();
        par.setFlashMode(par.FLASH_MODE_TORCH);
        camera.setParameters(par);
        camera.startPreview();
      }

    private void closeFlashLight(){
        if(camera==null)
          camera=camera.open();
        if(par==null)
          par=camera.getParameters();
        par.setFlashMode(par.FLASH_MODE_OFF);
        camera.stopPreview();
        camera.release();
        camera=null;
      }


  }

