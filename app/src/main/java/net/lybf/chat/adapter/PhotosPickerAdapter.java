package net.lybf.chat.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.LruCache;
import java.io.File;
import java.util.Map;
import net.lybf.chat.R;

public class PhotosPickerAdapter extends RecyclerView.Adapter<PhotosPickerAdapter.ViewHolder>{
      
   
   private Context ctx;
   private LruCache cache;
   
   public PhotosPickerAdapter(Context ctx){
      ctx=ctx;
      //cache=new LinkedHashMap<String, Bitmap>();
   }
   
   public PhotosPickerAdapter(Context ctx,File[] photos){
      ctx=ctx;
   }
   public void addItem(int position,Map map){
         //   mData.add(position,map);
         notifyItemInserted(position);
         }

      public void removeItem(int position){
        // mData.remove(position);
         notifyItemRemoved(position);
         }

      //创建新View，被LayoutManager所调用
      @Override
      public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
         View view = LayoutInflater.from(ctx).inflate(R.layout.item_main_post,viewGroup,false);
         ViewHolder vh = new ViewHolder(view);
         return vh;
         }
      //将数据与界面进行绑定的操作
      @Override
      public void onBindViewHolder(ViewHolder viewHolder,int p2){


         }
      //  viewHolder.mTextView.setText(datas[position]);
      //获取数据的数量
      @Override
      public int getItemCount(){
         return 0;
         }

      //自定义的ViewHolder，持有每个Item的的所有界面元素
      public  class ViewHolder extends RecyclerView.ViewHolder{
            public ImageView hader;
            public ViewHolder(View view){
               super(view);
               }
         }

   }
