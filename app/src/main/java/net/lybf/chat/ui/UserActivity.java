package net.lybf.chat.ui;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import net.lybf.chat.R;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import android.graphics.BitmapFactory;
import java.io.File;
import android.app.ProgressDialog;
import java.io.OutputStream;
import java.io.FileOutputStream;
import android.view.View;
import android.support.v7.app.AlertDialog;
import android.widget.ImageButton;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.exception.BmobException;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import net.lybf.chat.bmob.MyUser;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import java.security.PrivateKey;
import net.lybf.chat.system.settings;
import org.apache.http.auth.UsernamePasswordCredentials;
import cn.bmob.v3.listener.DownloadFileListener;
import net.lybf.chat.system.Utils;
import android.widget.CheckBox;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.DialogInterface;
import com.gc.materialdesign.widgets.SnackBar;
import android.support.design.widget.Snackbar;
import net.lybf.chat.bmob.ErrorMessage;
import android.view.MenuInflater;
import android.view.Menu;
import net.lybf.chat.system.ActivityResultCode;
import android.support.design.widget.TextInputEditText;
import android.widget.EditText;
import android.support.design.widget.TextInputLayout;

public class UserActivity extends AppCompatActivity
  {

    private settings set;

	private ImageButton UserHeader;

	private CheckBox EmailVerify;

	private Context ctx;

	private boolean UserInfoChange=false;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        set=new settings();
		ctx=this;
		if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
			setTheme(R.style.LightTheme);
		  }
        setContentView(R.layout.activity_user);
        initView();
      }

	@Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_user,menu);
        return true;
      }

	@Override
	public void onBackPressed(){
		if(UserInfoChange)
		  setResult(ActivityResultCode.USER_REFRESH);
		finish();
		super.onBackPressed();
	  }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
			  if(UserInfoChange){
				  use=null;
				  setResult(ActivityResultCode.USER_REFRESH);
				}
              this.finish();
              break;

			case R.id.user_refresh:
			  if(use!=null)
				use.update(new UpdateListener(){
					@Override
					public void done(BmobException p1){
						if(p1==null){
							refreshData();
							Snackbar.make(UserEmail,"刷新成功",Snackbar.LENGTH_SHORT).show();
						  }else{
							ErrorMessage msg=new ErrorMessage();
							new AlertDialog.Builder(ctx).setMessage(msg.getMessage(p1.getErrorCode()))
							.setPositiveButton("确定",null)
							.show();
						  }
					  }
				  });
			  break;
			case R.id.user_logout:
			  new AlertDialog.Builder(ctx)
			  .setTitle("退出登录？")
			  .setMessage("你确定要退出登录？")
			  .setPositiveButton("是",new DialogInterface.OnClickListener(){
				  @Override
				  public void onClick(DialogInterface p1,int p2){

					  use.logOut();
					  use=null;
					  UserInfoChange=true;
					  final Snackbar snackbar = Snackbar.make(UserEmail,"退出成功",Snackbar.LENGTH_LONG); 
					  snackbar.setAction("关闭",new View.OnClickListener() { 
						  @Override public void onClick(View v){ 
							  snackbar.dismiss();
							} 
						}
					  );  
					  snackbar.show();
					  refreshData();
					}
				}
			  )
			  .setNegativeButton("否",null)
			  .setCancelable(false)
			  .show();
			  break;
          }
        return super.onOptionsItemSelected(item);
      }


	/*用户名*/
    private TextView UserName;
    private TextView UserEmail;
    private TextView UserDescribe;
    private Toolbar bar;
    private MyUser use;
    private void initView(){
        use=BmobUser.getCurrentUser(MyUser.class);
		UserHeader=(ImageButton)findViewById(R.id.user_header);

        UserName=(TextView)findViewById(R.id.user_name);
        UserEmail=(TextView)findViewById(R.id.user_email);

		UserDescribe=(TextView)findViewById(R.id.user_describe);

        bar=(Toolbar)findViewById(R.id.toolbar_user);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		EmailVerify=(CheckBox)findViewById(R.id.user_emailVerify);
        refreshData();
      }




	public void refreshData(){
		try{
			if(use!=null){
				UserName.setText(use.getUsername());
				UserEmail.setText(use.getEmail());
				UserDescribe.setText(use.getdescribe());

				if(use.getEmail()!=null&&!use.getEmail().equals("")){
					boolean emailverify = false;
					try{
						emailverify=use.getEmailVerified();
					  }catch(Exception e){
						new Utils().print(this.getClass(),e);
					  }
					if(emailverify){
						EmailVerify.setChecked(true);
						EmailVerify.setEnabled(false);
					  }else{
						EmailVerify.setEnabled(false);
						UserEmail.setOnClickListener(new OnClickListener(){
							@Override
							public void onClick(View p1){
								AlertDialog.Builder dialog=new AlertDialog.Builder(ctx);
								dialog.setMessage("邮箱:"+use.getEmail()+"没有验证成功，是否重新验证？\n验证后享受更好的体验，如:\n邮箱+密码登录\n邮箱重置密码等");
								dialog.setPositiveButton("确定",new DialogInterface.OnClickListener(){
									@Override
									public void onClick(DialogInterface p1,int p2){
										use.requestEmailVerify(use.getEmail(),new UpdateListener(){
											@Override
											public void done(BmobException p1){
												if(p1==null){
													Snackbar.make(UserEmail,"已发送邮件，请查收",Snackbar.LENGTH_SHORT).show();
												  }else{
													ErrorMessage msg=new ErrorMessage();
													new AlertDialog.Builder(ctx).setMessage(msg.getMessage(p1.getErrorCode()))
													.setPositiveButton("确定",null)
													.show();

												  }
											  }
										  });

									  }			
								  });

								dialog.setNegativeButton("关闭",null);
								dialog.show();
							  }
						  });

					  }

				  }else{
					UserEmail.setText("未绑定邮箱，点击绑定");
					UserEmail.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View p1){
							TextInputLayout til=new TextInputLayout(ctx);
							til.setHint("输入您的邮箱");
							final EditText in=new EditText(ctx);
							til.addView(in);
							//in.setHint("输入您的邮箱");
							new AlertDialog.Builder(ctx)
							.setView(til)
							.setPositiveButton("确定",new DialogInterface.OnClickListener(){
								@Override
								public void onClick(DialogInterface p1,int p2){
									use.requestEmailVerify(in.getText().toString(),new UpdateListener(){
										@Override
										public void done(BmobException p1){
											if(p1==null){
												Snackbar.make(UserEmail,"请查收邮件",Snackbar.LENGTH_SHORT).show();
											  }else{
												new AlertDialog.Builder(ctx).setMessage("错误信息:"+new ErrorMessage().getMessage(p1.getErrorCode()))
												.setPositiveButton("确定",null)
												.show();
											  }
										  }  
									  });
								  }
							  }).show();
						  }
					  });

				  }


				BmobFile icon=use.getIcon();
				final String ic=icon.getFilename();
				new Utils().print("图片名:"+ic);
				final File f=new File("/sdcard/lybf/MPSquare/.user/"+use.getObjectId()+"/"+ic);
				new Utils().print("文件路径:"+f.getAbsolutePath());
				if(!f.getParentFile().exists())
				  f.getParentFile().mkdirs();
				if(f.exists()){
					UserHeader.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
				  }else{
					icon.download(f.getAbsoluteFile(),new DownloadFileListener(){
						@Override
						public void done(String p1,BmobException p2){
							if(p2==null){
								UserHeader.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
							  }else{
								System.out. println("下载失败:"+p2);
							  }
						  }
						@Override
						public void onProgress(Integer p1,long p2){
							new Utils().print("下载:"+ic+"到:"+f.getAbsolutePath()+"   进度:"+p1);
						  }   
					  });
				  }
			  }else{
				UserName.setText("未登录");
				UserEmail.setText("");
				UserDescribe.setText("");
				EmailVerify.setChecked(false);
				EmailVerify.setEnabled(false);
				UserHeader.setImageBitmap(		
				BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher
				));
			  }

		  }catch(Exception e){
			new Utils().print("错误。"+e);
		  }
	  }


	public void 设置头像(View v){
        Intent  intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try{
            startActivityForResult(Intent.createChooser(intent,"选择图片"),1000);
          }catch(android.content.ActivityNotFoundException ex){}

      }



    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(data!=null){
            String t=data.getData().getPath();
            File F=new File(t);
            if(F.exists()){}
            //获取图(F);
          }
        super.onActivityResult(requestCode,resultCode,data);
      }


    private void setImage(Bitmap b){
        ImageButton bu=(ImageButton)findViewById(R.id.user_header);
        Drawable bl=new BitmapDrawable(b); 
        bu.setImageDrawable(bl);
      }



    private void upIcon(File f){
        final BmobFile bmobFile=new BmobFile(f);

        MyUser u=BmobUser.getCurrentUser(MyUser.class);
        if(u!=null){
            u.setIcon(bmobFile);
            u.update(new UpdateListener(){
                @Override
                public void done(BmobException p){
                    if(p==null){
						new Utils().print(this.getClass(),"成功");
                      }else{
                        alert("错误","状态码："+p.getErrorCode()+"\n错误信息:\n"+p.getMessage());
                      }
                  }
              }
            );
          }
      }



    private void alert(String st,String str){
        new AlertDialog.Builder(this)
        .setTitle(st)
        .setMessage(str)
        .setCancelable(false)
        .setPositiveButton("关闭",null)
        .show();
      }


    private Bitmap compressImage(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while(baos.toByteArray().length/1024>100){
            new Utils().print(this.getClass(),"压图操作:百分比:"+options+"%");
            //循环判断如果压缩后图片是否大于100kb,大于继续压缩        
            baos.reset();//重置baos即清空baos
            options-=10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG,options,baos);//这里压缩options%，把压缩后的数据存放到baos中

          }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm,null,null);//把ByteArrayInputStream数据生成图片
        return bitmap;
      }

  }
