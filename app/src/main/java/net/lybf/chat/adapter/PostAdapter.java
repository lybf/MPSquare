package net.lybf.chat.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import net.lybf.chat.R;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.bmob.Post;
import net.lybf.chat.system.BmobUtils;
import net.lybf.chat.system.Utils;
import net.lybf.chat.utils.BitmapTools;
import net.lybf.chat.utils.DateTools;
import net.lybf.chat.utils.UserManager;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>
  {

    private OnItemClickListener onClicklistener;

    private OnItemLongClickListener onLongClicklistener;

    public interface OnItemClickListener
      {
        void onClick(View view,int index);
      }

    public interface OnItemLongClickListener
      {
        boolean onLong(View view,int index);
      }

    //帖子
    private static ArrayList<Post> mData =new ArrayList<Post>();

    //用户头像
    private static HashMap<String,Object> userIcon=new HashMap<String,Object>();

    //贴子评论计数
    private static HashMap<String,Object> comments=new HashMap<String,Object>();

    private static Context ctx;

    private static BitmapTools BMT;

    private static DateTools DTL;

    private static UserManager Umanager;

    //贴子总数

    private static int POST_COUNT = 0;

    public PostAdapter(Context ctx){
        this.ctx=ctx;
        this.Umanager=new UserManager(ctx);
        init();
      }

    public PostAdapter addPostCommentCount(String objectId,int i){
        comments.put(objectId,i);
        notifyItemChanged(i);
        return this;
      }

    private void init(){
        BMT=new BitmapTools();
        DTL=new DateTools();
      }

    public PostAdapter clearAll(){
        mData.clear();
        userIcon.clear();
        comments.clear();
        System.gc();
        return this;
      }


    public PostAdapter addItem(final int position,Post post){
        if(position<=-1)
          return this;
        mData.add(position,post);
        //    notifyItemInserted(position);
        final MyUser user=post.getUser();
        File file=Umanager.getIconFile(user);
        if(file!=null){
            userIcon.put(user.getObjectId(),file);
            notifyItemChanged(position-1);
          }
        return this;
      }

    public PostAdapter additem(Post post){
        int j=count();
        for(int i=0;i<j;i++){
            Post p=mData.get(i);
            if(post.getObjectId().equals(p.getObjectId())){
                Date dt=DTL.String2Date(post.getUpdatedAt(),BmobUtils.BMOB_DATE_TYPE);
                Date dt2=DTL.String2Date(p.getUpdatedAt(),BmobUtils.BMOB_DATE_TYPE);
                if(dt.getTime()-dt2.getTime()>0){
                    mData.set(i,post);
                  }
                mData.set(i,post);
              }
          }
        mData.add(post);
        //notifyItemChanged(mData.size()-1);
        //  notifyItemInserted(mData.size()-1);
        final MyUser user=post.getUser();
        File file=Umanager.getIconFile(user);
        if(file!=null){
            userIcon.put(user.getObjectId(),file);
            notifyItemChanged((int)getItemCount()-1);
          }
        return this;
      }


    public PostAdapter removeItem(int position){
        mData.remove(position);
        notifyItemRemoved(position);
        return this;
      }

    public PostAdapter updateitem(int i,Post hm){
        if(mData.get(i)!=hm){
            mData.set(i,hm);
            notifyItemChanged(i);
          }
        return this;
      }

    public PostAdapter updateComments(int i,String postid,int num){
        comments.put(postid,num);
        notifyItemChanged(i);
        return this;
      }

    public PostAdapter updatePostCount(int count){
        this.POST_COUNT=count;
        return this;
      }

    public int getPostCount(){
        return this.POST_COUNT;
      }

    public Post getPost(int i){
        Post hm = null;
        try{
            hm=mData.get(i);
          }catch(Exception e){
            Utils.print(this.getClass(),e);
          }
        return hm;
      }


    public PostAdapter sort(){
        return this;
      }

    public PostAdapter insert(int index){
        notifyItemInserted(index);
        return this;
      }

    public PostAdapter removed(int index){
        Post post=mData.get(index);
        comments.remove(post.getObjectId());
        mData.remove(index);
        notifyItemRemoved(index);
        return this;
      }

    public PostAdapter setOnItemClickListener(OnItemClickListener listener){
        this.onClicklistener=listener;
        return this;
      }

    public PostAdapter setItemOnLongClickListener(OnItemLongClickListener listener){
        this.onLongClicklistener=listener;
        return this;
      }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){    
        if(viewType==ViewHolder.TYPE_POST)
          return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_main_post,viewGroup,false),
          ViewHolder.TYPE_POST);
        else
          return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_main_post_end,viewGroup,false),
          ViewHolder.TYPE_END);
      }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int p2){
        if(count()==0)
          return;
        if(viewHolder.TYPE_POST==viewHolder.type&&p2!=POST_COUNT&&p2<mData.size()){
            final Post m=mData.get(p2);

            MyUser user=m.getUser();
            if(viewHolder.name!=null)
              viewHolder.name.setText(""+user.getUsername());
            String ct =""+comments.get(m.getObjectId());
            if(viewHolder.lookAllComments!=null)
              viewHolder.lookAllComments.setText(!ct.equals("")||!ct.equals("null")||ct.equals("0")?"共"+ct+"条评论":"");
            SimpleDateFormat dat=new SimpleDateFormat(BmobUtils.BMOB_DATE_TYPE);

            Date d = null;
            try{
                d=dat.parse(""+m.getCreatedAt());
              }catch(Exception e){
                print("dateFormat:"+e);
              }
            if(viewHolder.date!=null)
              viewHolder.date.setText(new DateTools().date(d));

            if(viewHolder.title!=null)
              viewHolder.title.setText(""+m.getTitle());
            TextView t=viewHolder.message;

            if(viewHolder.hader!=null)
              Picasso.with(ctx).load((File)userIcon.get(user.getObjectId())).into(viewHolder.hader);
            if(t!=null)
              t.setText(""+m.getMessage());
            if(onClicklistener!=null){
                if(t!=null)
                  t.setOnClickListener(new OnClickListener(){
                      @Override
                      public void onClick(View p1){
                          onClicklistener.onClick(p1,p2);
                        }
                    });
                if(viewHolder.go!=null)
                  viewHolder.go.setOnClickListener(new OnClickListener(){
                      @Override
                      public void onClick(View p1){
                          onClicklistener.onClick(p1,p2);
                        }
                    });
              }

            //
            if(onLongClicklistener!=null){
                if(t!=null)
                  t.setOnLongClickListener(new OnLongClickListener(){
                      @Override
                      public boolean onLongClick(View p1){
                          return onLongClicklistener.onLong(p1,p2);
                        }
                    });
                if(viewHolder.go!=null)
                  viewHolder.go.setOnLongClickListener(new OnLongClickListener(){
                      @Override
                      public boolean onLongClick(View p1){
                          return onLongClicklistener.onLong(p1,p2);
                        }
                    });
              }

          }else{
            if((p2)==POST_COUNT&&POST_COUNT!=0){
                if(viewHolder.lastPosition!=null)
                  viewHolder.lastPosition.setText("已经到达地球底端了啦，什么也没有了");
              }else{
                if(POST_COUNT==0&&viewHolder.lastPosition==null){
                    if(viewHolder.lastPosition!=null)
                      viewHolder.lastPosition.setText("到底了？");
                  }else
                if(viewHolder.lastPosition!=null)
                  viewHolder.lastPosition.setText("");
                //    viewHolder.lastPosition.setHeight(0
              }
          }
      }


    @Override
    public int getItemCount(){
        if(mData.size()==0)
          return 0;
        else
          return mData.size()+1;
      }


    @Override
    public int getItemViewType(int position){
        if(position<count()){
            return ViewHolder.TYPE_POST;
          }else
          return ViewHolder.TYPE_END;
      }


    public int count(){
        return mData.size();
      }

    private void print(Object... obj){
        for(Object o:obj){
            System.out.println("PostAdapter.class:"+o+"\n");
          }
      }

    public  class ViewHolder extends RecyclerView.ViewHolder
      {
        public static final int TYPE_POST=0;
        public static final int TYPE_END=1;
        public TextView name; 
        private TextView date;
        private TextView title;
        private TextView message;
        private TextView lookAllComments;
        public RelativeLayout go;

        //头像
        public ImageButton hader;

        public int type;


        //  

        private TextView lastPosition;

        public ViewHolder(View view,int type){
            super(view);
            this.type=type;
            if(type==TYPE_POST){
                title=(TextView)view.findViewById(R.id.item_post_title);
                message=(TextView)view.findViewById(R.id.item_post_content);
                date=(TextView)view.findViewById(R.id.item_post_time);
                name=(TextView)view.findViewById(R.id.item_post_name);
                lookAllComments=(TextView)view.findViewById(R.id.item_post_lookAllComments);
                go=(RelativeLayout)view.findViewById(R.id.item_post_go);
                hader=(ImageButton)view.findViewById(R.id.item_post_header);
              }else{
                lastPosition=(TextView)view.findViewById(R.id.item_main_post_end_content);
              }
          }
      }
  }


