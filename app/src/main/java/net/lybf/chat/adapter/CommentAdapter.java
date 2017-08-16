package net.lybf.chat.adapter;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.bmob.Comment;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.bmob.Post;
import net.lybf.chat.system.BmobUtils;
import net.lybf.chat.system.Paths;
import net.lybf.chat.system.Utils;
import net.lybf.chat.utils.DateTools;
import net.lybf.chat.widget.CircleImageView;
import net.lybf.chat.utils.UserManager;
import android.graphics.Bitmap;
import net.lybf.chat.utils.CommentCount;
import java.text.ParseException;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>
  {
    //Created by lybf on 2017/3/13
    private Post post;

    private List<Comment> comment=new ArrayList<Comment>();

    private Context ctx;

    private MyUser user;

    private static final int TYPE_POST = 0;

    private static final int TYPE_COMMENT = 1;

    private static final int TYPE_SPLIT=2;

    //评论总数
    public static int CommentCount;
    public CommentAdapter(Context ctx){
        this.ctx=ctx;
      }

    public CommentAdapter(Context ctx,Post post){
        this.ctx=ctx;
        if(post!=null){
            this.post=post;
            this.user=post.getUser();
          }
      }


    public CommentAdapter setPost(Post post){
        this.post=post;
        this.user=post.getUser();
        notifyItemInserted(0);
        return this;
      }

    public CommentAdapter addComment(Comment comment){
        this.comment.add(comment);
        notifyItemChanged(count()>2?count()-2:count());
        return this;
      }

    public CommentAdapter addComment(int insert,Comment comment){
        this.comment.add(insert,comment);
        notifyItemInserted(insert);
        return this;
      }

    public CommentAdapter removeEnd(){
        this.comment.remove(count()-2);
        notifyItemChanged(count()-2);
        return this;
      }

    public CommentAdapter removeFirst(){
        this.comment.remove(0);
        notifyItemChanged(0);
        return this;
      }

    public CommentAdapter remove(int index){
        this.comment.remove(index);
        notifyItemChanged(index);
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
        return this;
      }

    public Comment getComment(int index){
        if(index>commentCount()||index<0)
          return null;
        return this.comment.get(index);  
      }

    public int count(){
        int count=0;
        if(post!=null)
          count+=2;
        count+=this.comment.size();
        if(post==null)count=0;
        return count;
      }

    public int commentCount(){
        return count()-2;
      }

    @Override
    public int getItemViewType(int position){
        if(position==0)
          return TYPE_POST;
        else if(position==1)
          return TYPE_SPLIT;
        else
          return TYPE_COMMENT;
      }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup p1,int p2){
        ViewHolder v = null;
        if(p2==TYPE_POST){
            v=new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_post_user,null),TYPE_POST);
          }else if(p2==TYPE_COMMENT){
            v=new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_comment,null),TYPE_COMMENT);
          }else if(p2==TYPE_SPLIT){
            v=new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_post_split_allcomment,null),TYPE_SPLIT);
          }
        return v;
      }

    @Override
    public void onBindViewHolder(final ViewHolder v,final int index){
        //   try{
        if(index==0){
            if(user.getUsername()!=null)
              v.name.setText(""+user.getUsername());
            if(post.getCreatedAt()!=null){
                SimpleDateFormat da=new SimpleDateFormat(BmobUtils.BMOB_DATE_TYPE);
                Date datt = null;
                try{
                    datt=da.parse(post.getCreatedAt());
                  }catch(ParseException e){}
                v.time.setText(""+new DateTools().date(datt));
              }
            if(post.getMessage()!=null)
              v.content.setText(""+post.getMessage());

            UserManager bm=new UserManager(ctx);
            File file= bm.getIconFile(user);
            if(file!=null){
                Picasso.with(ctx).load(file).into(v.header);
              }else{
                Picasso.with(ctx).load(R.drawable.ic_launcher).into(v.header);
              }
          }else if(v.type==TYPE_SPLIT){
            new CommentCount().setPost(post).count(new CommentCount.CommentCountListener(){
                @Override
                public void done(int i,BmobException e){
                    if(e==null){
                        v.count.setText("全部评论("+(CommentCount=i)+")");
                      }else{
                      }
                  }           
              });

          }else if(v.type==TYPE_COMMENT){
            Comment m=comment.get(index-2);
            MyUser usr=m.getUser();
            UserManager bm=new UserManager(ctx);
            File file= bm.getIconFile(usr);
            if(file!=null){
                Picasso.with(ctx).load(file).into(v.header);
              }else{
                Picasso.with(ctx).load(R.drawable.ic_launcher).into(v.header);
              }          
            v.name.setText(""+usr.getUsername());
            SimpleDateFormat da=new SimpleDateFormat(BmobUtils.BMOB_DATE_TYPE);
            Date dp = null;
            try{
                dp=da.parse(""+m.getCreatedAt());
              }catch(ParseException e){}
            v.time.setText(""+new DateTools().date(dp));
            v.content.setText(""+m.getMessage());
          }
        //   notifyItemChanged(index);
        // }catch(Exception e){  }
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

        //评论数
        public TextView count;

        public TextView name;
        public TextView content;
        public CircleImageView header;
        public TextView time;
        public int type;

        public ViewHolder(View view,int type){
            super(view);
            this.type=type;
            if(type==TYPE_COMMENT||type==TYPE_POST){
                name=(TextView) view.findViewById(R.id.comment_name);
                content=(TextView)view.findViewById(R.id.comment_content);
                header=(CircleImageView)view.findViewById(R.id.comment_header);
                time=(TextView)view.findViewById(R.id.comment_time);
              }else if(type==TYPE_SPLIT){
                count=(TextView)view.findViewById(R.id.item_post_split_allcomment_count);
              }
          }
      }
  }
