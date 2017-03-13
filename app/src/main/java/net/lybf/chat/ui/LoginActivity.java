package net.lybf.chat.ui;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import net.lybf.chat.R;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.text.InputType;
import android.widget.CompoundButton;
import javax.security.auth.PrivateCredentialPermission;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;
import java.io.FileOutputStream;
import org.json.JSONObject;
import java.io.File;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.exception.BmobException;
import android.support.design.widget.TextInputLayout;
import net.lybf.chat.bmob.ErrorMessage;
import com.gc.materialdesign.views.ButtonRectangle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.res.AssetManager;
import java.io.IOException;
import android.widget.RelativeLayout;
import java.util.Random;
import java.io.InputStream;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import net.lybf.chat.system.settings;
import android.support.v7.app.ActionBar;
import net.lybf.chat.system.Utils;
import android.text.TextUtils;
public class LoginActivity extends AppCompatActivity
  {
    private Toolbar bar;
    private EditText Name, Pass;
    private TextInputLayout mtextInputlayout,mname;
    private ButtonRectangle LOGIN;
    private TextView SIGNUP;
    private RelativeLayout root;
    private settings set;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        set=new settings();
        if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
			setTheme(R.style.LightTheme);
		  }

        setContentView(R.layout.activity_login);
        initView();
        if(set.getRandomBackground()){
            try{
                String[] as=this.getAssets().list("RandomBackground");
                int i=new Random().nextInt(as.length);
                Drawable a;
                InputStream in=getAssets().open("RandomBackground/"+as[i]);

                byte[] by=new  byte[in.available()];
                int length=in.read(by);
                a=new BitmapDrawable(BitmapFactory.decodeByteArray(by,0,length));
                root.setBackground(a);
              }catch(Exception e){}
          }
      }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        try{
            switch(item.getItemId()){
                case android.R.id.home:
                  finish();
                  break;
              }
          }catch(Exception e){
            print(e);
          }
        return super.onOptionsItemSelected(item);
      }




    private void initView(){
        try{
            root=(RelativeLayout) findViewById(R.id.login_root_Background);
            bar=(Toolbar)findViewById(R.id.toolbar_login);
            setSupportActionBar(bar);
            ActionBar ab= getSupportActionBar();
            ab. setDisplayHomeAsUpEnabled(true);

            LOGIN=(ButtonRectangle)findViewById(R.id.login);
            SIGNUP=(TextView)findViewById(R.id.login_signup);
            LOGIN.setOnClickListener(new OnClickListener(){
                public void onClick(View v){
                    String name=Name.getText().toString();
                    String pass=Pass.getText().toString();
                    if(TextUtils.isEmpty(pass)){
                        mtextInputlayout.setError("密码不能为空");
                        mtextInputlayout.setErrorEnabled(true);
                      }else if(TextUtils.isEmpty(name)){
                        mtextInputlayout.setErrorEnabled(false);
                        mname.setErrorEnabled(true);
                        mname.setError("用户名不能为空");
                      }else{
                        mname.setErrorEnabled(false);
                        logIn(name,pass);
                      }
                  }
              }
            );

            SIGNUP.setOnClickListener(new OnClickListener(){
                public void onClick(View v){
                    Intent in=new Intent(LoginActivity.this,SignUpActivity.class);
                    startActivity(in);

                  }
              }
            );

            Pass=(EditText)findViewById(R.id.login_password);
            Name=(EditText)findViewById(R.id.login_name);
            mtextInputlayout=(TextInputLayout)findViewById(R.id.tip_login_password);

            mtextInputlayout.setCounterMaxLength(18);
            mtextInputlayout.setCounterEnabled(true);

            mname=(TextInputLayout)findViewById(R.id.tip_login_name);
            mname.setCounterMaxLength(8);
            mname.setCounterEnabled(true);

            ((CheckBox)findViewById(R.id.canseepassword)).setOnCheckedChangeListener(new OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton p1,boolean p2){
                    if(p2)
                      Pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    else
                      Pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                  }
              }
            );

          }catch(Exception e){
            print(e);
          }
      }




    private void logIn(final String name,final String password){
        BmobUser ur=new BmobUser();
        ur.setUsername(name);
        ur.setPassword(password);
        ur.login(new SaveListener<BmobUser>(){
            @Override
            public void done(BmobUser p1,BmobException p){
                if(p==null){
                    new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("登录成功")
                    .setMessage("登录成功，你可以尽情玩耍了")
                    .setPositiveButton("好的",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface p1,int p2){
                            setResult(9999);
                            finish();
                          }
                      }
                    )
                    .setNegativeButton("关闭",null)
                    .setCancelable(false)
                    .show();
                  }else{
                    String m=null;
                    ErrorMessage msg=new ErrorMessage();
                    m=msg.getMessage(p.getErrorCode());
                    new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("登录失败")
                    .setMessage("登录失败\n状态码:"+p.getErrorCode()+"\n\n错误信息:"+m==null?p.getMessage():m)
                    .setPositiveButton("重试",null)
                    .show();
                  }
              }
          }
        );
      }




    private void print(Object o){
		new Utils().print(this.getClass(),o);
      }
  }
