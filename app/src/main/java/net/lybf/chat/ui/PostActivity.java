package net.lybf.chat.ui;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import net.lybf.chat.activity.MPSActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import java.util.List;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.adapter.CommentAdapter;
import net.lybf.chat.bmob.Comment;
import net.lybf.chat.bmob.ErrorMessage;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.bmob.Post;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;
import net.lybf.chat.utils.CommonUtil;
import net.lybf.chat.utils.DateTools;
import net.lybf.chat.utils.Network;
import net.lybf.chat.system.BmobUtils;
import java.util.Date;
import cn.bmob.v3.datatype.BmobDate;
import com.gc.materialdesign.widgets.ProgressDialog;

public class PostActivity extends MPSActivity
  {

    private Toolbar bar;

    //网络工具类
    private Network net;

    //Context
    private Context ctx;

    private settings set;

    private String postId;

    private MyUser use;

    private DateTools DTools;

    private FloatingActionButton fab;

    private SwipeRefreshLayout refresh;

    private RecyclerView listview;

    private CommentAdapter adapter;

    private MainApplication app;

    // private int load;

    private LinearLayoutManager linearLayoutManager;

    private ProgressDialog progress;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        app=getMainApplication();
        set=app.getSettings();

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
        postId=(String) b.get("帖子");
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
            listview=(RecyclerView)findViewById(R.id.comment_content);
            listview.setAdapter((adapter=new CommentAdapter(this)));
            linearLayoutManager=new LinearLayoutManager(this);         
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            listview.setLayoutManager(linearLayoutManager); 
            listview.setItemAnimator(new DefaultItemAnimator());

            listview.setOnScrollListener(new RecyclerView.OnScrollListener(){
                private int lastPosition=0;
                public void onScrolled(RecyclerView recyclerView,int dx,int dy){
                    super.onScrolled(recyclerView,dx,dy);
                  }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView,int newState){
                    super.onScrollStateChanged(recyclerView,newState);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    Utils.print(this.getClass(),"lastPosition:"+lastItemPosition);
                    int cn=adapter.CommentCount;
                    if(lastItemPosition==adapter.count()-1&&lastItemPosition!=lastPosition){
                        //当滑动到最后一个评论时(评论数>0)，加载剩余评论(10条)
                        if(progress==null)
                          PostActivity.this.progress=new ProgressDialog(ctx,"加载评论中..");
                        if(adapter.commentCount()==cn){
                            final Snackbar s= Snackbar.make(bar,"已经到达地球底端了啦",Snackbar.LENGTH_SHORT);
                            s.setAction("确定",new OnClickListener(){
                                public void onClick(View v){
                                    s.dismiss();
                                  }
                              }).show();
                          }else if(!progress.isShowing()){
                            PostActivity.this. progress.show();
                            readMore();
                          }
                      }
                      lastPosition=lastItemPosition;
                  }

              });
            refresh=(SwipeRefreshLayout)findViewById(R.id.comment_refresh);
            refresh.setProgressViewOffset(false,0,new CommonUtil().dip2px(100f));
            refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
                @Override
                public void onRefresh(){
                    i=0;
                    runing.postDelayed(run,1000);
                    read();
                  } 
              }
            );
            refresh.setColorSchemeResources(R.color.orange,R.color.green,R.color.blue); 
            refresh.setSize(SwipeRefreshLayout.DEFAULT);
          }catch(Exception e){
            print("初始化失败"+e);
          }
        initTie();
      }



    private void initTie(){
        BmobQuery<Post> tie=new BmobQuery<Post>();
        tie.include("user");
        tie.getObject(postId,new QueryListener<Post>() {
            @Override
            public void done(Post p1,BmobException p2){
                if(p2==null){
                    print("OK");
                    post=p1;
                    bar.setTitle(""+post.getTitle());
                    user=p1.getUser();
                    adapter.setPost(post);
                    read();
                  }else{
                    if(net.isConnectedOrConnecting()){
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
                    c.setPost(post);
                    c.setMessage(str);
                    c.save(new SaveListener<String>(){
                        @Override
                        public void done(String p1,BmobException p2){
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
    //帖子
    private Post post;

    private void readMore(){
        Comment last=adapter.getComment(adapter.commentCount()-1);
        if(last==null){
          return;
        }
        String date = null;
        if(last!=null){
            date=last.getCreatedAt();
          }
        Date dat=DateTools.getDate(date,BmobUtils.BMOB_DATE_TYPE);
        final BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.order("-createdAt");
        query.addWhereEqualTo("post",post);
        query.addWhereLessThan("createdAt",new BmobDate(dat));
        query.include("user,image,image2,image3");
        query.setLimit(10);
        boolean has=query.hasCachedResult(Comment.class);
        if(has)
          query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        else
          query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> p1,BmobException p2){
                if(p2==null){
                    for(Comment me: p1){
                        adapter.addComment(me);
                      }
                    adapter.notifyDataSetChanged();
                    if(progress.isShowing()){
                        progress.dismiss();
                        final Snackbar snack=Snackbar.make(bar,"本次更新了:"+p1.size()+"条评论",Snackbar.LENGTH_SHORT);
                        snack.setAction("确定",new OnClickListener(){
                            public void onClick(View v){
                                snack.dismiss();
                              }
                          }).show();
                      }
                  }else{
                    print("错误编码:"+p2.getErrorCode()+"\n错误信息:"+p2.getMessage());
                  }
              }
          }
        );
      }


    private void read(){
        print("开始扫描评论");
        final BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.order("-createdAt");
        query.addWhereEqualTo("post",post);

        query.include("user,image,image2,image3");
        query.setLimit(10);
        boolean has=query.hasCachedResult(Comment.class);
        if(has)
          query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        else
          query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> p1,BmobException p2){
                if(p2==null){
                    adapter.removeAll();
                    adapter.notifyDataSetChanged();
                    int p = 0;
                    for(Comment me: p1){
                        p++;
                        Utils.print(this.getClass(),"评论 "+p);
                        adapter.addComment(me);
                      }
                    adapter.notifyDataSetChanged();
                    if(refresh.isRefreshing())
                      refresh.setRefreshing(false);
                  }else{
                    //加载信息条数-=10;
                    print("错误编码:"+p2.getErrorCode()+"\n错误信息:"+p2.getMessage());
                  }
              }
          }
        );
      }



    private void print(Object p){
        Utils.print(this.getClass(),p);
      }
  }
    
