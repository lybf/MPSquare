package net.lybf.chat.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import net.lybf.chat.bmob.Post;
import android.content.Context;
import net.lybf.chat.bmob.Comment;
import java.util.List;
import java.util.ArrayList;
import net.lybf.chat.MainApplication;
import java.text.SimpleDateFormat;
import android.widget.TextView;
import net.lybf.chat.R;
import android.widget.ImageButton;
import cn.bmob.v3.datatype.BmobFile;
import android.view.LayoutInflater;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.util.CommonUtil;
import net.lybf.chat.util.DateTools;
import java.io.File;
import com.squareup.picasso.Picasso;
import net.lybf.chat.system.Utils;
import android.graphics.BitmapFactory;
import net.lybf.chat.widget.CircleImageView;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.exception.BmobException;
import java.util.Date;
import net.lybf.chat.system.Paths;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>
  {
	//Created at 2017/3/13
	private Post post;

	private List<Object> comment=new ArrayList<Object>();

	private Context ctx;

	private MainApplication mApplication;

	private MyUser user;

	private static final int TYPE_POST = 0;

	private static final int TYPE_COMMENT = 1;
	public CommentAdapter(){
		init();
		if(ctx==null){
			this.ctx=mApplication.getContext();
		  }else{
			this.ctx=ctx;
		  }
	  }
	public CommentAdapter(Context ctx){
		init();
		if(ctx==null){
			this.ctx=mApplication.getContext();
		  }else{
			this.ctx=ctx;
		  }
	  }

	public CommentAdapter(Context ctx,Post post){
		init();
		if(ctx==null){
			this.ctx=mApplication.getContext();
		  }else{
			this.ctx=ctx;
		  }
		if(post!=null){
			this.comment.add(post);
			this.post=post;
			this.user=post.getUser();
		  }
	  }

	private void init(){
		mApplication=new MainApplication();
	  }

	public CommentAdapter setPost(Post post){
		this.comment.add(post);
		this.post=post;
		this.user=post.getUser();
		notifyItemInserted(0);
		return this;
	  }

	public CommentAdapter addComment(Comment comment){
		this.comment.add(comment);
		notifyItemInserted(count());
		return this;
	  }

	public CommentAdapter addComment(int insert,Comment comment){
		this.comment.add(insert,comment);
		notifyItemInserted(insert);
		return this;
	  }

	public CommentAdapter remove(){
		if(count()>0){
			this.comment.remove(count());
			notifyItemChanged(count());
		  }
		return this;
	  }

	public CommentAdapter removeFirst(){
		if(count()>1){
			this.comment.remove(1);
			notifyItemChanged(1);
		  }
		return this;
	  }

	public CommentAdapter remove(int index){
		if(index<count()||index>-1){
			if(count()>1&&index>1){
				index-=1;
			  }
			this.comment.remove(index);
			notifyItemChanged(index);
		  }
		return this;
	  }

	public CommentAdapter remove(Comment comment){
		if(comment!=null){
			this.comment.remove(comment);
		  }
		return this;
	  }

	public CommentAdapter removeAll(){
		this.comment.clear();
		this.comment.add(post);
		return this;
	  }

	public Comment getComment(int index){
		if(index<count()&&index>-1){
			int in=index;
			if(count()>1){
				in-=1;
			  }
			return (Comment)this.comment.get(in);
		  }
		return null;
	  }

	public int count(){
		return this.comment.size();
	  }

	@Override
	public int getItemViewType(int position){
		if(position==0)
		  return TYPE_POST;
		else
		  return TYPE_COMMENT;
	  }

	@Override
	public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup p1,int p2){
		ViewHolder v;
		if(p2==TYPE_POST){
			v=new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_post_user,null),0);
		  }else{
			v=new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_comment,null),1);
		  }
		return v;
	  }

	@Override
	public void onBindViewHolder(CommentAdapter.ViewHolder v,final int p2){

		if(p2==0){
			if(user.getUsername()!=null)
			  v.name.setText(""+user.getUsername());
			if(post.getCreatedAt()!=null){
				SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date datt = null;
				try{
					datt=(Date) da.parse(post.getCreatedAt());
				  }catch(Exception e){
					print(e);
				  }
				v.time.setText(""+new DateTools().date(datt));
			  }
			if(post.getMessage()!=null)
			  v.content.setText(""+post.getMessage());

			BmobFile file=user.getIcon();
			if(file!=null){
				File f=new File(Paths.USER_PATH+"/"+user.getObjectId()+"/"+file.getFilename());
				if(!f.getParentFile().exists())
				  f.getParentFile().mkdirs();
				if(f.exists()){
					try{
						Picasso.with(ctx).load(f.getAbsoluteFile()).into(
						v.header);
					  }catch(Exception e){
						print("解析头像错误:"+e);
					  }
				  }else{
					file.download(f,new DownloadFileListener(){
						@Override
						public void done(String p1,BmobException p){
							if(p==null){
								notifyItemChanged(p2);
							  }else{
								print(p);
							  }
						  }
						@Override
						public void onProgress(Integer p1,long p2){
							print("下载头像中:"+p1);

						  }               
					  });
				  }

			  }else{
				Picasso.with(ctx).load(R.drawable.ic_launcher).into(v.header);
			  }
		  }else{
			if(v.type==0);
			//v=new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_comment,null),1);
			Comment m=(Comment)comment.get(p2);
			MyUser usr=m.getUser();
			BmobFile file=usr.getIcon();
			if(file!=null){
				File f=new File(Paths.USER_PATH+"/"+user.getObjectId()+"/"+file.getFilename());

				if(!f.getParentFile().exists())
				  f.getParentFile().mkdirs();
				if(f.exists()){
					try{
						v.header.setImageBitmap(
						BitmapFactory.decodeFile(f.getAbsolutePath()));
					  }catch(Exception e){
						print("解析评论头像错误:"+e);
					  }
				  }else{
					file.download(f,new DownloadFileListener(){
						@Override
						public void done(String p1,BmobException p){
							if(p==null){
								notifyItemChanged(p2);
							  }else{
								print(p);
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
			v.name.setText(""+usr.getUsername());
			SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dp = null;
			try{
				dp=(Date) da.parse(""+m.getCreatedAt());
			  }catch(Exception e){
				print(e);
			  }
			v.time.setText(""+new DateTools().date(dp));
			v.content.setText(""+m.getMessage());
		  }
	  }

	private void print(Object e){
		new Utils().print(this.getClass(),e);
	  }


	@Override
	public int getItemCount(){
		if(post!=null){
			return count();
		  }
		return 0;
	  }


	public class ViewHolder extends RecyclerView.ViewHolder
	  {

		public TextView name;
		public TextView content;
		public CircleImageView header;
		public TextView time;
		public int type;

		public ViewHolder(View view,int type){
			super(view);
			this.type=type;
			name=(TextView) view.findViewById(R.id.comment_name);
			content=(TextView)view.findViewById(R.id.comment_content);
			header=(CircleImageView)view.findViewById(R.id.comment_header);
			time=(TextView)view.findViewById(R.id.comment_time);
		  }
	  }
  }
