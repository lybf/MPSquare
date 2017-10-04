package net.lybf.chat.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.LruCache;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.lybf.chat.R;

public class PhotosPickerAdapter extends RecyclerView.Adapter<PhotosPickerAdapter.ViewHolder>
  {


    private Context ctx;
    //缓存
    private LruCache cache;
    //图片
    private List<File> list;

    public PhotosPickerAdapter(Context ctx){
        this. ctx=ctx;
        this.list=new ArrayList<File>();
        //cache=new LinkedHashMap<String, Bitmap>();
      }

    public PhotosPickerAdapter(Context ctx,List<File> photos){
        this.ctx=ctx;
        this.list=photos;
      }

    public void addItem(int position,File file){
        list.add(position,file);
        notifyItemInserted(position);
      }

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
      }

    public void clear(){
        list.clear();
      }
    
    public File getFile(int position){
        return list.get(position);
      }

    public List<File> getList(){
        return this.list;
      }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_main_post,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
      }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,int p2){


      }

    @Override
    public int getItemCount(){
        return 0;
      }

    public  class ViewHolder extends RecyclerView.ViewHolder
      {
        public ImageView image;
        public ViewHolder(View view){
            super(view);
          }
      }

  }
