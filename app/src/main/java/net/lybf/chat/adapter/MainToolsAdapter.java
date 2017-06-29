package net.lybf.chat.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import net.lybf.chat.R;
import net.lybf.chat.maps.MainTools;
import net.lybf.chat.util.BitmapTools;
import net.lybf.chat.util.DateTools;

public class MainToolsAdapter extends RecyclerView.Adapter<MainToolsAdapter.ViewHolder>
  {
    //CreatedAt 2017/4/22 12:34
    private ArrayList<MainTools> views=new ArrayList<MainTools>();
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

    public MainToolsAdapter(Context ctx){
        this.ctx=ctx;
        init();
      }

    private void init(){
        BMT=new BitmapTools();
        DTL=new DateTools();
      }

    public MainToolsAdapter clearAll(){
        return this;
      }


    public MainToolsAdapter addItem(final int position,MainTools data){
        this.views.add(position,data);
        notifyItemInserted(position);
        return this;
      }

    public MainToolsAdapter additem(MainTools data){
        views.add(data);
        notifyItemInserted(count());
        return this;
      }


    public MainToolsAdapter removeItem(int position){
        notifyItemRemoved(position);
        return this;
      }

    public MainToolsAdapter updateitem(int i,MainTools hm){
        views.set(i,hm);
        notifyItemChanged(i);
        return this;
      }

    public MainToolsAdapter insert(int index){
        notifyItemInserted(index);
        return this;
      }

    public MainToolsAdapter removed(int index){
        notifyItemRemoved(index);
        return this;
      }

    public MainToolsAdapter setOnItemClickListener(OnItemClickListener listener){
        this.onClicklistener=listener;
        return this;
      }
    public MainToolsAdapter setItemOnLongClickListener(OnItemLongClickListener listener){
        this.onLongClicklistener=listener;
        return this;
      }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
        View view= LayoutInflater.from(ctx).inflate(R.layout.item_main_tools,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
      }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int p2){
        MainTools too=getItemData(p2);
        (viewHolder.hader).setImageBitmap(too.getBitmap());
        viewHolder.describe.setText(""+too.getDescribe());
        if(onClicklistener!=null){
            viewHolder.root.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1){
                    onClicklistener.onClick(viewHolder.root,p2);
                  }
              });
              
            viewHolder.describe.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1){
                    onClicklistener.onClick(viewHolder.root,p2);
                  }
              });
            viewHolder.hader.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1){
                    onClicklistener.onClick(viewHolder.root,p2);
                  }
              });
          }
      }

    public MainTools getItemData(int position){
        if(position>-1&&position<=position){
            return views.get(position);
          }
        return null;
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
            System.out.println("MainToolsAdapter.class:"+o+"\n");
          }
      }

    public  class ViewHolder extends RecyclerView.ViewHolder
      {
        public View root;

        public TextView describe;

        public ImageView hader;
        public ViewHolder(View view){
            super(view);
            this.root=view;
            describe=(TextView)view.findViewById(R.id.item_main_tools_describe);
            hader=(ImageView)view.findViewById(R.id.item_main_tools_Image);
          }
      }
  }


