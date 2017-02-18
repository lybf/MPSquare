package net.lybf.chat.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.lybf.chat.util.DateTools;
import net.lybf.chat.R;
import net.lybf.chat.util.BitMapTools;
import net.lybf.chat.ui.PostActivity;
import net.lybf.chat.bmob.Post;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.bmob.images;
import cn.bmob.v3.datatype.BmobFile;
import java.io.File;
import android.graphics.BitmapFactory;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.exception.BmobException;
import android.widget.RelativeLayout;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import android.support.v7.widget.LinearLayoutManager;

public class MainTieAdapter extends RecyclerView.Adapter<MainTieAdapter.ViewHolder>
  {

    //帖子
	private ArrayList<Post> mData =new ArrayList<Post>();
    //用户头像
	private HashMap<String,Object> userIcon=new HashMap<String,Object>();
    //帖子计数
	private HashMap<String,Object> comments=new HashMap<String,Object>();
	private Context ctx;

    private BitMapTools BMT;

	private RecyclerView mRecyclerview;
	public MainTieAdapter(Context ctx){
		this.ctx=ctx;
		init();
	  }

	public void addPostCommentCount(String objectId,int i){
		comments.put(objectId,i);
	  }

    private void init(){
        BMT=new BitMapTools();
      }

    public void clearAll(){
        mData.clear();
		userIcon.clear();
		comments.clear();
      }


    public void addItem(final int position,Post post){
        final MyUser user=post.getUser();
        BmobFile icon=user.getIcon();
        String ic=icon.getFilename();
        final File f=new File("/sdcard/lybf/MPSquare/.user/"+user.getObjectId()+"/"+icon.getFilename());
        if(!f.getParentFile().exists())
          f.getParentFile().mkdirs();
        if(f.exists()){
            userIcon.put(user.getObjectId(),f);
          }else{
            icon.download(f,new DownloadFileListener(){
                @Override
                public void done(String p1,BmobException p2){
                    if(p2==null){
                        userIcon.put(user.getObjectId(),f);
                        notifyItemChanged(position);
                      }else{
                        System.out. println(p2);
                      }
                  }
                @Override
                public void onProgress(Integer p1,long p2){
                  }   
              });
          }

        mData.add(position,post);
        notifyItemInserted(position);
      }

    public void additem(Post post){

        final MyUser user=post.getUser();
        BmobFile icon=user.getIcon();
        String ic=icon.getFilename();
        final File f=new File("/sdcard/lybf/MPSquare/.user/"+user.getObjectId()+"/"+icon.getFilename());
        if(!f.getParentFile().exists())
          f.getParentFile().mkdirs();
        if(f.exists()){
            userIcon.put(user.getObjectId(),f);
          }else{
            icon.download(f,new DownloadFileListener(){
                @Override
                public void done(String p1,BmobException p2){
                    if(p2==null){
                        userIcon.put(user.getObjectId(),f);
                        notifyItemChanged((int)getItemCount());
                      }else{
                        System.out. println(p2);
                      }
                  }
                @Override
                public void onProgress(Integer p1,long p2){
                  }   
              });
		  }
		mData.add(post);
		//notifyItemChanged(mData.size()-1);
		notifyItemInserted(mData.size()-1);
      }


    public void removeItem(int position){
        mData.remove(position);
        notifyItemRemoved(position);
      }

    public void updateitem(int i,Post hm){
		if(mData.get(i)!=hm){
			mData.set(i,hm);
			notifyItemChanged(i);
		  }
      }

    public void updateComments(int i,String postid,int num){
        comments.put(postid,num);
        notifyItemChanged(i);
      }


    public Post getPost(int i){
        Post hm = null;
        try{
            hm=mData.get(i);
          }catch(Exception e){}
        return hm;
      }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
	    View view= LayoutInflater.from(ctx).inflate(R.layout.item_main_post,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
      }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,int p2){
        final Post m=(Post)mData.get(p2);

        MyUser user=m.getUser();
        viewHolder.name.setText(""+user.getUsername());
		String ct =""+comments.get(m.getObjectId());
		viewHolder.comments.setText(""+ct);
		viewHolder.lookAllComments.setText(!ct.equals("")||!ct.equals("null")?"查看全部(共"+ct+"条)":"");
        SimpleDateFormat dat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d = null;
        try{
            d=dat.parse(""+m.getCreatedAt());
          }catch(Exception e){
			print("dateFormat:"+e);
		  }
        viewHolder.date.setText(new DateTools().date(d));
        viewHolder.title.setText(""+m.getTitle());
        TextView t=viewHolder.message;

		Picasso.with(ctx).load((File)userIcon.get(user.getObjectId())).into(viewHolder.hader);
		t.setText(""+m.getMessage());
        t.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View p1){
                Intent i=new Intent(ctx,PostActivity.class);
                Bundle bu=new Bundle();
                bu.putString("帖子",m.getObjectId());
                i.putExtra("Mydata",bu);
                ctx.startActivity(i);
              }
          });

		viewHolder.go.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View p1){
                Intent i=new Intent(ctx,PostActivity.class);
                Bundle bu=new Bundle();
                bu.putString("帖子",m.getObjectId());
                i.putExtra("Mydata",bu);
                ctx.startActivity(i);
              }
          });
      }


    @Override
    public int getItemCount(){
        return mData.size();
      }

	private void print(Object... obj){
		for(Object o:obj){
			System.out.println("MainTieAdapter.class:"+o+"\n");
		  }
	  }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public  class ViewHolder extends RecyclerView.ViewHolder
      {
		/*
		 名称，时间，标题，内容，评论数，查看评论
		 */

        public TextView name,date,title,message,comments,lookAllComments;
		public RelativeLayout go;
        public ImageButton hader;
        public ViewHolder(View view){
            super(view);
            title=(TextView)view.findViewById(R.id.item_post_title);
            message=(TextView)view.findViewById(R.id.item_post_content);
			date=(TextView)view.findViewById(R.id.item_post_time);
            name=(TextView)view.findViewById(R.id.item_post_name);
			lookAllComments=(TextView)view.findViewById(R.id.item_post_lookAllComments);
			go=(RelativeLayout)view.findViewById(R.id.item_post_go);
			comments=(TextView)view.findViewById(R.id.item_post_comment_number);
            hader=(ImageButton)view.findViewById(R.id.item_post_header);
          }
      }
  }


