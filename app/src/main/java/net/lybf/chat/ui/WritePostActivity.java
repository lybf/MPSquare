package net.lybf.chat.ui;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.gc.materialdesign.views.ButtonRectangle;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.activity.MPSActivity;
import net.lybf.chat.bmob.ErrorMessage;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.bmob.Post;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;

public class WritePostActivity extends MPSActivity
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
        app=getMainApplication();
        set=app.getSettings();

        if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
            setTheme(R.style.LightTheme);
          }
        setContentView(R.layout.activity_writepost);
        this.ctx=this;
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



    private void printError(String str){
        new AlertDialog.Builder(this)
        .setTitle("错误")
        .setMessage(str)
        .setPositiveButton("确定",null)
        .show();
      }




    private void sendmessage(String title,String msg){
        if(TextUtils.isEmpty(title)&!TextUtils.isEmpty(msg)&&title.equals("")&&msg.equals("")){
            printError("标题不能为空\n"+"内容不能为空");
            return;
          }else  if(TextUtils.isEmpty(msg)&&msg.equals("")){
            printError("内容不能为空");
            return;
          }else if(TextUtils.isEmpty(title)){
            printError("标题不能为空");
            return;
          }
        if(title!=null&&msg!=null&&title.length()>0&&msg.length()>0){
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
                        Utils.print(this.getClass(),p2.getErrorCode());
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
        Utils.print(this.getClass(),o);
      }
  }
