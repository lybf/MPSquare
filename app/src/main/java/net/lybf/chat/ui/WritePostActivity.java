package net.lybf.chat.ui;
import android.app.Activity;
import android.os.Bundle;
import net.lybf.chat.R;
import android.view.View;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Button;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import cn.bmob.v3.listener.SaveListener;
import android.support.v7.app.AlertDialog;
import cn.bmob.v3.BmobUser;
import net.lybf.chat.bmob.MyUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.datatype.BatchResult;
import java.util.List;
import cn.bmob.v3.BmobObject;
import net.lybf.chat.bmob.Post;
import net.lybf.chat.bmob.ErrorMessage;
import android.support.v7.app.AppCompatActivity;
import com.gc.materialdesign.views.ButtonRectangle;
import android.view.View.OnClickListener;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.Context;
import net.lybf.chat.system.settings;
import net.lybf.chat.system.Utils;
import net.lybf.chat.MainApplication;

public class WritePostActivity extends AppCompatActivity
  {

    /*
     标题
     内容
     */
    private EditText Title, content;
    /*用户*/
    private MyUser use;
    /*工具栏*/
    private Toolbar bar;
    /*Content*/
    private Context ctx;
    /*设置*/
    private settings set;
    /*发送*/
    private ButtonRectangle send;

    private MainApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        app=new MainApplication();
        set=app.set;

        if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
            setTheme(R.style.LightTheme);
          }
        setContentView(R.layout.activity_writepost);
        ctx=this;
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

    private void init(){
        Title=(EditText)findViewById(R.id.post_title);
        bar=(Toolbar)findViewById(R.id.toolbar_sendtie);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        content=(EditText)findViewById(R.id.post_writeContent);
        send=(ButtonRectangle)findViewById(R.id.post_writePost);
        send.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View p1){
                String tit=Title.getText().toString();
                String msg=content.getText().toString();
                sendmessage(tit,msg);
              }
          }
        );    

        use=BmobUser.getCurrentUser(MyUser.class);
      }


    private boolean t=false,t2=false;


    private void printError(String str){
        new AlertDialog.Builder(this)
        .setTitle("错误")
        .setMessage(str)
        .setPositiveButton("确定",null)
        .show();
      }




    private void sendmessage(String title,String msg){
        if(title==null){
            if(msg==null){
                printError("帖子标题不能为空\n"+"内容不能为空");
              }else{
                if(title!=null)
                  printError("帖子内容不能为空");
              }
          }else{
            //    printError("帖子标题不能为空");
          }
        if(title!=null&&msg!=null){

            Post me= new Post();
            me.setMessage(msg);
            me.setUser(use);
            me.setType("0");
            me.setTitle(title);
            me.save(new SaveListener<String>(){
                @Override
                public void done(String p1,BmobException p2){
                    print(p2);
                    if(p2==null){
                        setResult(9999);        
                        Toast.makeText(ctx,"发送成功",Toast.LENGTH_SHORT);
                        finish();
                      }else{
                        ErrorMessage msg=new ErrorMessage();
                        String s=null;
                        s=msg.getMessage(p2.getErrorCode());
                        new AlertDialog.Builder(WritePostActivity.this)
                        .setTitle("发送失败")
                        .setMessage("错误状态码:"+p2.getErrorCode()+"\n错误信息:\n"+s==null?s:p2.getMessage())
                        .setPositiveButton("关闭",null)
                        .setCancelable(false)
                        .show();
                      }
                  }
              }
            );
          }
      }

    private void print(Object o){
        new Utils().print(this.getClass(),o);
      }
  }
