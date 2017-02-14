package net.lybf.chat.ui;
import android.support.v7.app.AppCompatActivity;
import cn.bmob.v3.BmobUser;
import android.os.Bundle;
import net.lybf.chat.R;
import cn.bmob.v3.exception.BmobException;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import net.lybf.chat.bmob.ErrorMessage;
import cn.bmob.v3.listener.SaveListener;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import com.gc.materialdesign.views.ButtonRectangle;
import android.view.View.OnClickListener;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import android.support.design.widget.TextInputLayout;
import android.widget.Toast;
import android.support.design.widget.Snackbar;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.system.settings;

public class SignUpActivity extends AppCompatActivity
  {

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
        setContentView(R.layout.activity_signup);
        init();
      }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
              finish();
              break;
          }
        return super.onOptionsItemSelected(item);
      }





    private Toolbar bar;
    /*
     name,email,Password,aginEnterPassword
     */
    private EditText e,e2,e3,e4;
    private ButtonRectangle reg;
    private TextInputLayout Name,Email,Pa,Apa;
    private void init(){

        Name=(TextInputLayout)findViewById(R.id.tip_sign_name);
        Name.setCounterMaxLength(10);
        Name.setCounterEnabled(true);
        Email=(TextInputLayout)findViewById(R.id.tip_sign_email);

        Pa=(TextInputLayout)findViewById(R.id.tip_sign_password);
        Pa.setCounterMaxLength(18);
        Pa.setCounterEnabled(true);

        Apa=(TextInputLayout)findViewById(R.id.tip_sign_aginInputPassword);
        Apa.setCounterMaxLength(18);
        Apa.setCounterEnabled(true);

        bar=(Toolbar)findViewById(R.id.toolbar_sign);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        e=(EditText)findViewById(R.id.sign_name);
        e2=(EditText)findViewById(R.id.sign_email);
        e3=(EditText)findViewById(R.id.sign_Password);
        e4=(EditText)findViewById(R.id.sign_aginInpuPassword);

        reg=(ButtonRectangle)findViewById(R.id.sign_register);
        print(reg);
        print("\n\n"+reg==null?"空指针":true);
        reg.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View p1){
                print("doRegister");
                doRegister();
              }
          }
        );
      }


    private boolean isEmail(String str){
        Pattern p=Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
        Matcher	m= p.matcher(str);
        if(m.find())
          return true;
        else
          return false;
      }


    private void doRegister(){
        print("startRegister");
        String name=""+e.getText().toString();
        String email=""+e2.getText().toString();
        String pass=""+e3.getText().toString();
        String agin=""+e4.getText().toString();

        boolean NAME= false,EMAIL= false,password = false,password2=false;
        if(name.length()<2){
            Name.setError("名称不可小于2个字符");
            Name.setErrorEnabled(true);
          }else{
            NAME=true;
            Name.setErrorEnabled(false);
          }


        if(isEmail(email)){
            EMAIL=true;
            Email.setErrorEnabled(false);
          }else{
            Email.setError("不是正确的邮箱");
            Email.setErrorEnabled(true);
          }

        if(pass!=null&&pass.length()>0){
            if(pass.length()>6){
                password=true;
                Pa.setErrorEnabled(false);
                if((""+pass).equals(agin)){
                    password2=true;
                    Pa.setErrorEnabled(false);
                    Apa.setErrorEnabled(false);
                  }else{
                    Apa.setError("两次输入的密码不一致");
                    Apa.setErrorEnabled(true);}
              }else{
                Pa.setError("密码长度少于5个字符");
                Pa.setErrorEnabled(true);}
          }else{
            Pa.setError("密码不能为空");
            Pa.setErrorEnabled(true);
          }

        if((NAME==EMAIL==password==password2)!=false)
          signup(name,email,pass);

      }


    private void signup(String name,String email,String password){
        BmobUser ur=new BmobUser();
        ur.setUsername(name);
        ur.setEmail(email);
        ur.setPassword(password);
        ur.signUp(new SaveListener<MyUser>(){

            @Override
            public void done(MyUser p1,BmobException p2){
                print(p2);
                if(p2==null){
                    Snackbar.make(reg,"注册成功，赶紧登录吧",Snackbar.LENGTH_SHORT).show();

                  }else{
                    String m=null;
                    ErrorMessage msg=new ErrorMessage();
                    m=msg.getMessage(p2.getErrorCode());		
                    new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("注册失败")
                    .setMessage("注册失败\n状态码:"+p2.getErrorCode()+"\n\n错误信息:"+m==null?p2.getMessage():m)
                    .setPositiveButton("重试",null)
                    .setCancelable(false)
                    .show();
                  }
              }
          }
        );
      }




    private void print(Object o){
        System.out.println(o);
      }
  }
