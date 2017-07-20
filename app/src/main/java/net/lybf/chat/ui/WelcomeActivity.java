package net.lybf.chat.ui;
import android.content.Intent;
import android.os.Bundle;
import net.lybf.chat.activity.MPSActivity;
import net.lybf.chat.system.settings;
import android.os.Process;
import net.lybf.chat.activity.MPSActivity;

public class WelcomeActivity extends MPSActivity /*AppCompatActivity*/
  {

   // private settings set;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent in=new Intent(this,MainActivity.class);
        startActivity(in);
        this.finish();
       // Process.killProcess(Process.myPid());
        //System.exit(1);
      }
  }
