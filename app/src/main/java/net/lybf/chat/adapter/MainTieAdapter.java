package net.lybf.chat.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
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
import net.lybf.chat.util.BitmapTools;
import net.lybf.chat.util.DateTools;

public class MainTieAdapter extends RecyclerView.Adapter<MainTieAdapter.ViewHolder>
  {

    private OnItemClickListener onClicklistener;
    private OnItemLongClickListener onLongClicklistener;
    public interface OnItemClickListener
      {
        public void onClick(View view,int index);
      }

    public interface OnItemLongClickListener
      {
        public void onLong(View view,int index);
      }

    //帖子
    private ArrayList<Post> mData =new ArrayList<Post>();
    //用户头像
    private HashMap<String,Object> userIcon=new HashMap<String,Object>();
    //帖子计数
    private HashMap<String,Object> comments=new HashMap<String,Object>();
    private Context ctx;

    private BitmapTools BMT;

    private DateTools DTL;

    public MainTieAdapter(Context ctx){
        this.ctx=ctx;
        init();
      }

    public MainTieAdapter addPostCommentCount(String objectId,int i){
        comments.put(objectId,i);
        return this;
      }

    private void init(){
        BMT=new BitmapTools();
        DTL=new DateTools();
      }

    public MainTieAdapter clearAll(){
        mData.clear();
        userIcon.clear();
        comments.clear();
        return this;
      }


    public MainTieAdapter addItem(final int position,Post post){
        if(position<=-1)
          return this;
        mData.add(position,post);
        notifyItemInserted(position);
        final MyUser user=post.getUser();
        BmobFile icon=user.getIcon();
        String ic=icon.getFilename();
        final File f=new File("/sdcard/lybf/MPSquare/.user/"+user.getObjectId()+"/head/"+icon.getFilename());
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
                        notifyItemChanged(position-1);
                      }else{
                        System.out. println(p2);
                      }
                  }
                @Override
                public void onProgress(Integer p1,long p2){
                    if(p1==100){
                        userIcon.put(user.getObjectId(),f);
                        int i=0;
                        i=(position>=1?position-1:position);
                        notifyItemChanged(i);
                      }
                  }   
              });
          }

        return this;
      }

    public MainTieAdapter additem(Post post){
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
        notifyItemInserted(mData.size()-1);
        final MyUser user=post.getUser();
        BmobFile icon=user.getIcon();
        String ic=icon.getFilename();
        final File f=new File("/sdcard/lybf/MPSquare/.user/"+user.getObjectId()+"/head/"+icon.getFilename());
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
                        notifyItemChanged((int)getItemCount()-1);
                      }else{
                        System.out. println(p2);
                      }
                  }
                @Override
                public void onProgress(Integer p1,long p2){
                    if(p1==100){
                        userIcon.put(user.getObjectId(),f);
                        notifyItemChanged(getItemCount()-1);
                      }
                  }   
              });
          }

        return this;
      }


    public MainTieAdapter removeItem(int position){
        mData.remove(position);
        notifyItemRemoved(position);
        return this;
      }

    public MainTieAdapter updateitem(int i,Post hm){
        if(mData.get(i)!=hm){
            mData.set(i,hm);
            notifyItemChanged(i);
          }
        return this;
      }

    public MainTieAdapter updateComments(int i,String postid,int num){
        comments.put(postid,num);
        notifyItemChanged(i);
        return this;
      }


    public Post getPost(int i){
        Post hm = null;
        try{
            hm=mData.get(i);
          }catch(Exception e){}
        return hm;
      }


    public MainTieAdapter sort(){
        return this;
      }

    public MainTieAdapter insert(int index){
        notifyItemInserted(index);
        return this;
      }

    public MainTieAdapter removed(int index){
        notifyItemRemoved(index);
        return this;
      }

    public MainTieAdapter setOnItemClickListener(OnItemClickListener listener){
        this.onClicklistener=listener;
        return this;
      }
    public MainTieAdapter setItemOnLongClickListener(OnItemLongClickListener listener){
        this.onLongClicklistener=listener;
        return this;
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
    public void onBindViewHolder(ViewHolder viewHolder,final int p2){
        final Post m=(Post)mData.get(p2);

        MyUser user=m.getUser();
        viewHolder.name.setText(""+user.getUsername());
        String ct =""+comments.get(m.getObjectId());
        viewHolder.lookAllComments.setText(!ct.equals("")||!ct.equals("null")||ct.equals("0")?"共"+ct+"条评论":"");
        SimpleDateFormat dat=new SimpleDateFormat(BmobUtils.BMOB_DATE_TYPE);

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
        if(onClicklistener!=null){
            t.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1){
                    onClicklistener.onClick(p1,p2);
                  }
              });

            viewHolder.go.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1){
                    onClicklistener.onClick(p1,p2);
                  }
              });
          }
      }


    @Override
    public int getItemCount(){
        return mData.size();
      }

    public int count(){
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
         名称，时间，标题，内容，查看评论
         */

        public TextView name,date,title,message,lookAllComments;
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
            hader=(ImageButton)view.findViewById(R.id.item_post_header);

          }
      }
  }


