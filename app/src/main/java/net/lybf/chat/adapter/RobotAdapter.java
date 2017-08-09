
package net.lybf.chat.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import net.lybf.chat.R;
import net.lybf.chat.maps.Robot;
import net.lybf.chat.utils.BitmapTools;
import net.lybf.chat.utils.DateTools;
import net.lybf.chat.widget.CircleImageView;
import net.lybf.chat.utils.UserManager;
import net.lybf.chat.bmob.MyUser;
import cn.bmob.v3.BmobUser;
import java.io.File;
import net.lybf.chat.system.Paths;
import com.squareup.picasso.Picasso;
import net.lybf.chat.system.Utils;
import cn.bmob.v3.datatype.BmobFile;

public class RobotAdapter extends RecyclerView.Adapter<RobotAdapter.ViewHolder>
  {
    //CreatedAt 2017/4/24 00:10
    private ArrayList<Robot> views=new ArrayList<Robot>();

    private OnItemClickListener onClicklistener;

    private OnItemLongClickListener onLongClicklistener;

    public interface OnItemClickListener
      {
        void onClick(View view,int index);
      }

    public interface OnItemLongClickListener
      {
        void onLong(View view,int index);
      }



    private Context ctx;

    private BitmapTools BMT;

    private DateTools DTL;

    private MyUser user;
    public RobotAdapter(Context ctx){
        this.ctx=ctx;
        init();
        user=BmobUser.getCurrentUser(MyUser.class);
      }

    private void init(){
        BMT=new BitmapTools();
        DTL=new DateTools();
      }

    public RobotAdapter clearAll(){
        views.clear();
        notifyDataSetChanged();
        return this;
      }


    public RobotAdapter addItem(final int position,Robot data){
        this.views.add(position,data);
        notifyItemInserted(position);
        return this;
      }

    public RobotAdapter additem(Robot data){
        views.add(data);
        notifyItemInserted(count());
        return this;
      }


    public RobotAdapter removeItem(int position){
        views.remove(position);
        notifyItemRemoved(position);
        return this;
      }

    public RobotAdapter updateitem(int i,Robot hm){
        views.set(i,hm);
        notifyItemChanged(i);
        return this;
      }

    public RobotAdapter insert(int index){
        notifyItemInserted(index);
        return this;
      }

    public RobotAdapter removed(int index){
        notifyItemRemoved(index);
        return this;
      }

    public RobotAdapter setOnItemClickListener(OnItemClickListener listener){
        this.onClicklistener=listener;
        return this;
      }

    public RobotAdapter setItemOnLongClickListener(OnItemLongClickListener listener){
        this.onLongClicklistener=listener;
        return this;
      }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
        View view=null;
        int code = 0;
        if(viewType==Robot.FLAG_ROBOT){
            code=Robot.FLAG_ROBOT;
            view=LayoutInflater.from(ctx).inflate(R.layout.item_robot_robot,viewGroup,false);
          }else{
            if(viewType==Robot.FLAG_MYSELF){
                view=LayoutInflater.from(ctx).inflate(R.layout.item_robot_myself,viewGroup,false);
                code=Robot.FLAG_MYSELF;
              }
          }
        ViewHolder vh = new ViewHolder(view,code);
        return vh;
      }
    //将数据与界面进行绑定的操作

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int p2){
        Robot too=getItemData(p2);
        viewHolder.content.setText(""+too.getText());
        viewHolder.name.setText(""+too.getName());
        if(viewHolder.flag==viewHolder.FLAG_MYSELF){
            UserManager manager=new UserManager(ctx);
            File file=manager.getIconFile(user);
            if(file!=null)
              Picasso.with(ctx).load(file).into(viewHolder.header);
            else
              Picasso.with(ctx).load(R.drawable.ic_launcher).into(viewHolder.header);
          }
        if(onClicklistener!=null){
            viewHolder.root.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1){
                    onClicklistener.onClick(viewHolder.root,p2);
                  }
              });
          }
      }

    public Robot getItemData(int position){
        Robot chat=views.get(position);
        return chat;
      }

    public ArrayList<Robot> getAllData(){
        return views;
      }

    @Override
    public int getItemViewType(int position){
        Robot ro= views.get(position);
        return ro.getFlag();
      }


    @Override
    public int getItemCount(){
        return views.size();
      }

    public int count(){
        return views.size();
      }

    private void print(Object... obj){
        for(Object o:obj){
            Utils.print(this.getClass(),o);
          }
      }

    public  class ViewHolder extends RecyclerView.ViewHolder
      {
        public static final int FLAG_ROBOT=Robot.FLAG_ROBOT;

        public static final int FLAG_MYSELF=Robot.FLAG_MYSELF;

        public int flag;

        public View root;

        public TextView name;

        public CircleImageView header;

        public TextView content;

        public ViewHolder(View view,int flag){
            super(view);
            this.root=view;
            this.flag=flag;
            if(flag==FLAG_ROBOT){
                header=(CircleImageView)view.findViewById(R.id.item_robot_robot_header);
                name=(TextView)view.findViewById(R.id.item_robot_robot_name);
                content=(TextView)view.findViewById(R.id.item_robot_robot_content);
              }else
            if(flag==FLAG_MYSELF){
                header=(CircleImageView)view.findViewById(R.id.item_robot_myself_header);
                name=(TextView)view.findViewById(R.id.item_robot_myself_name);
                content=(TextView)view.findViewById(R.id.item_robot_myself_content);
              }
          }
      }
  }


