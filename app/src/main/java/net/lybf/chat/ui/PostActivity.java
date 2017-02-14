package net.lybf.chat.ui;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lybf.chat.util.CommonUtil;
import net.lybf.chat.util.DateTools;
import net.lybf.chat.R;
import net.lybf.chat.bmob.Comment;
import net.lybf.chat.util.BitMapTools;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.bmob.ErrorMessage;
import net.lybf.chat.bmob.Post;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;
import net.lybf.chat.util.Network;
import android.content.Context;
import cn.bmob.v3.datatype.BmobFile;
import java.io.File;
import android.graphics.BitmapFactory;
import android.webkit.DownloadListener;
import cn.bmob.v3.listener.DownloadFileListener;
import android.view.MenuItem;
import net.lybf.chat.system.settings;
import com.squareup.picasso.Picasso;

public class PostActivity extends AppCompatActivity
  {

    private Toolbar bar;

    //网络工具类
    private Network net;

    //Context
    private Context ctx;

    private settings set;

	private String 帖子;

	private MyUser use;

	private DateTools DTools;

	private FloatingActionButton fab;

	private ListView listview;

	private Myadapter adapter;

	private SwipeRefreshLayout refresh;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        set=new settings();
		use=BmobUser.getCurrentUser(MyUser.class);
		if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
			setTheme(R.style.LightTheme);
		  }
        setContentView(R.layout.activity_post_comment);

        bar=(Toolbar)findViewById(R.id.toolbar_comment);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ctx=this;
        Intent i=getIntent();
        Bundle b=i.getBundleExtra("Mydata");
        帖子=(String) b.get("帖子");
        initView();
      }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        read();
        super.onActivityResult(requestCode,resultCode,data);
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
        use=BmobUser.getCurrentUser(MyUser.class);
        DTools=new DateTools();


        try{
            fab=(FloatingActionButton)findViewById(R.id.comment_writeComment);
            fab.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1){
                    if(use!=null)
                      WriteComment();
                    else
                      new AlertDialog.Builder(PostActivity.this)
                      .setTitle("失败")
                      .setMessage("你需要登录后才能发言")
                      .setPositiveButton("登录",new DialogInterface.OnClickListener(){
                          @Override
                          public void onClick(DialogInterface p1,int p2){
                              startActivityForResult(new Intent(PostActivity.this,LoginActivity.class),0);
                            }
                        })
                      .setNegativeButton("关闭",null)
                      .setCancelable(false)
                      .show();
                  }
              }
            );
            listview=(ListView)findViewById(R.id.comment_content);
            listview.setAdapter((adapter=new Myadapter()));
            listview.setFastScrollEnabled(true);

            refresh=(SwipeRefreshLayout)findViewById(R.id.comment_refresh);
            refresh.setProgressViewOffset(false,0,new CommonUtil(this).dip2px(100f));

			refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
                @Override
                public void onRefresh(){
                    i=0;
                    runing.postDelayed(run,1000);
                    加载信息条数+=10;
                    read();
                  } 
              }
            );
            /*
             //设置手势滑动监听器。		

             refresh.setProgressBackgroundColor(0xFFFFFF);
             //设置进度圈的背景色。*/
            refresh.setColorSchemeResources(R.color.orange,R.color.green,R.color.blue); 
            //设置进度动画的颜色。
            //refresh.setRefreshing(true);
            //设置组件的刷洗状态
            refresh.setSize(SwipeRefreshLayout.DEFAULT);
            //设置进度圈的大小，只有两个值：DEFAULT、LARGE

          }catch(Exception e){
            print("初始化失败"+e);
          }

        initTie();

      }



    private void initTie(){
        BmobQuery<Post> tie=new BmobQuery<Post>();
        tie.include("user");
        tie.getObject(帖子,new QueryListener<Post>() {
            @Override
            public void done(Post p1,BmobException p2){
                if(p2==null){
                    print("OK");
                    post=p1;
                    bar.setTitle(""+post.getTitle());
                    user=p1.getUser();
                    mData.add(new Comment());
                    read();
                  }else{
                    if(net.isNetWork()){
                        ErrorMessage error=new ErrorMessage();
                        String s=error.getMessage(p2.getErrorCode());
                        new AlertDialog.Builder(ctx)
                        .setTitle("出错啦")
                        .setMessage(s!=null?s:"未知错误")
                        .setCancelable(false)
                        .setPositiveButton("知道啦",null)
                        .show();
                      }else{
                        new AlertDialog.Builder(ctx)
                        .setTitle("出错啦")
                        .setMessage("网络走丢了")
                        .setPositiveButton("确定",null)
                        .show();
                      }
                  }

              }
          });

      }


    private  Handler runing=new Handler();
    private int i=0;
    private Runnable run=new Runnable(){
        public void run(){
            i++;
            if(i==5&&refresh.isRefreshing()){
                final Snackbar b=Snackbar.make(fab,"啊哦，失败了",Snackbar.LENGTH_LONG);
                b.setAction("确定",new OnClickListener(){
                    @Override
                    public void onClick(View p1){
                        b.dismiss();
                      }
                  }
                );
                b.show();
                refresh.setRefreshing(false);
              }		

            if(refresh.isRefreshing()&&i<=5){
                runing.postDelayed(this,1000);
              }
          }
      };






    private void WriteComment(){

        print("打开评论发送界面成功!!");
        final EditText e=new EditText(this);
        e.setHint("输入你要评论的内容");
        TextInputLayout te=new TextInputLayout(this);
        te.addView(e);
        AlertDialog.Builder a=new AlertDialog.Builder(this);
        a.setTitle("评论");
        a.setView(te);
        a.setPositiveButton("发送",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface p1,int p2){
                String str=e.getText().toString();
                if(str!=null){
                    Comment c=new Comment();
                    c.setUser(use);
                    c.setParent(帖子);
                    c.setMessage(str);
                    c.save(new SaveListener(){
                        @Override
                        public void done(Object p1,BmobException p2){
                            if(p2==null){
                                read();
                              }else if(p2!=null){					
                                String m=null;
                                ErrorMessage msg=new ErrorMessage();
                                m=msg.getMessage(p2.getErrorCode());
                                new AlertDialog.Builder(PostActivity.this)
                                .setTitle("发送失败")
                                .setMessage("发送失败\n状态码："+p2.getErrorCode()+"\n\n错误信息:\n"+m==null?p2.getMessage():m)
                                .setPositiveButton("确定",null)
                                .setCancelable(false)
                                .show();
                              }
                          }
                      }
                    );
                  }
              }
          }
        );
        a.show();
      }








    //用户(只存储发布者用户)
    private MyUser user;
    //评论
    private List<Comment> mData=new ArrayList<Comment>();
    //帖子
    private Post post;

    //头像
    private HashMap<String,Object> icons=new HashMap<String,Object>();
    private int 加载信息条数=50;

    private void read(){

        print("开始扫描评论");
        mData.clear();
		adapter.notifyDataSetChanged();
        final BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.order("-createdAt");
        query.addWhereEqualTo("parent",帖子);
        query.include("user");
        /*   query.include("image");
         query.include("image2");
         query.include("image3");*/
        query.setLimit(加载信息条数);
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        final boolean isCache = query.hasCachedResult(Comment.class);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> p1,BmobException p2){
                if(p2==null){
                    for(Comment me: p1){
                        mData.add(me);
                        adapter.notifyDataSetChanged();
                      }
                    if(refresh.isRefreshing())
                      refresh.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                  }else{
                    加载信息条数-=10;
                    print("错误编码:"+p2.getErrorCode()+"\n错误信息:"+p2.getMessage());
                  }
              }
          }
        );
      }



    private void print(Object p){
        System.out.println(p);
      }





    private class Myadapter extends BaseAdapter
      {
        @Override
        public int getCount(){
            return mData.size();
          }

        @Override
        public Object getItem(int p1){
            return (Comment)mData.get(p1);
          }

        @Override
        public long getItemId(int p1){
            return p1;
          }

        @Override
        public View getView(int p1,View p2,ViewGroup p3){


            if(p1==0){
                View view=LayoutInflater.from(ctx).inflate(R.layout.item_post_user,null);
                if(user.getUsername()!=null)
                  ((TextView)view.findViewById(R.id.User)).setText(""+user.getUsername());
                if(post.getCreatedAt()!=null){
                    SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date datt = null;
                    try{
                        datt=da.parse(post.getCreatedAt());
                      }catch(ParseException e){}
                    ((TextView)view.findViewById(R.id.Time)).setText(DTools.date(datt));
                  }
                if(post.getMessage()!=null)
                  ((TextView)view.findViewById(R.id.Message)).setText(""+post.getMessage());

                BmobFile file=user.getIcon();
                if(file!=null){
                    File f=new File("/sdcard/lybf/MPSquare/.user/"+user.getObjectId()+"/"+file.getFilename());
                    if(!f.getParentFile().exists())
                      f.getParentFile().mkdirs();
                    if(f.exists()){
						try{
							Picasso.with(ctx).load(f.getAbsoluteFile()).into(
							((ImageButton)view.findViewById(R.id.item_post_header)));
						  }catch(Exception e){
							print("解析头像错误:"+e);
						  }
                      }else{
                        file.download(f,new DownloadFileListener(){
                            @Override
                            public void done(String p1,BmobException p2){
                                if(p2==null){
                                    adapter.notifyDataSetChanged();
                                  }else{
                                    print(p2);
                                  }
                              }
                            @Override
                            public void onProgress(Integer p1,long p2){
								print("下载头像中:"+p1);

                              }               
                          }                    
                        );
                      }

                  }
                return view;
              }else{
                View v=LayoutInflater.from(PostActivity.this).inflate(R.layout.item_comment,null);
                Comment m=(Comment)mData.get(p1);
                MyUser usr=m.getUser();
                BmobFile file=usr.getIcon();
                if(file!=null){
                    File f=new File("/sdcard/lybf/MPSquare/.user/"+user.getObjectId()+"/"+file.getFilename());

                    if(!f.getParentFile().exists())
                      f.getParentFile().mkdirs();
                    if(f.exists()){
						try{
							((ImageButton)v.findViewById(R.id.comment_header)).setImageBitmap(
							BitmapFactory.decodeFile(f.getAbsolutePath()));
						  }catch(Exception e){
							print("解析评论头像错误:"+e);
						  }
                      }else{
                        file.download(f,new DownloadFileListener(){
                            @Override
                            public void done(String p1,BmobException p2){
                                if(p2==null){
                                    adapter.notifyDataSetChanged();
                                  }else{
                                    print(p2);
                                  }
                              }
                            @Override
                            public void onProgress(Integer p1,long p2){
								print("下载头像中:"+p1);
                              }               
                          }                    
                        );
                      }
                  }
                ((TextView)v.findViewById(R.id.comment_name)).setText(""+usr.getUsername());
                SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dp = null;
                try{
                    dp=da.parse(""+m.getCreatedAt());
                  }catch(Exception e){}
                ((TextView)v.findViewById(R.id.comment_time)).setText(DTools.date(dp));
                ((TextView)v.findViewById(R.id.comment_content)).setText(""+m.getMessage());
                return v;
              }
          }
      }
  }
	
