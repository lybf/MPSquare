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

public class UserActivity extends AppCompatActivity
  {

    private settings set;

	private ImageButton UserHeader;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        set=new settings();
		if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
			setTheme(R.style.LightTheme);
		  }
        setContentView(R.layout.activity_user);
        initView();
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
        refreshData();
      }




	public void refreshData(){
		try{
			if(use!=null){
				UserName.setText(use.getUsername());
				UserEmail.setText(use.getEmail());
				UserDescribe.setText(use.getdescribe());
			  }

			BmobFile icon=use.getIcon();
			final String ic=icon.getFilename();
			print("图片名:"+ic);
			final File f=new File("/sdcard/lybf/MPSquare/.user/"+use.getObjectId()+"/"+ic);
			print("文件路径:"+f.getAbsolutePath());
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
						print("下载:"+ic+"到:"+f.getAbsolutePath()+"   进度:"+p1);
					  }   
				  });
			  }
		  }catch(Exception e){
			print("错误。"+e);
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
                        print("成功");
                      }else{
                        alert("错误","状态码："+p.getErrorCode()+"\n错误信息:\n"+p.getMessage());
                      }
                  }
              }
            );
          }
      }


    private void print(Object o){
        System.out.println(o);
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
            print("压图操作:百分比:"+options+"%");
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
