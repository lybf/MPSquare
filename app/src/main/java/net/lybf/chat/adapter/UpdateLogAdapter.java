package net.lybf.chat.adapter;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import net.lybf.chat.R;
import net.lybf.chat.system.update;

public class UpdateLogAdapter extends RecyclerView.Adapter<UpdateLogAdapter.ViewHolder>
  {

    private List<update> list=new ArrayList<update>();

    private Context ctx;


    public UpdateLogAdapter(Context ctx){
        this.ctx=ctx;
      }

    public UpdateLogAdapter addItem(update up){
        list.add(up);
        notifyItemInserted(getItemCount());
        return this;
      }

    public UpdateLogAdapter addItem(int index,update up){
        if(index>-1&&index<=getItemCount()){
            list.add(index,up);
            notifyItemInserted(index);
          }
        return this;
      }

    public UpdateLogAdapter removeFirst(){
        if(getItemCount()>1){
            list.remove(0);
            notifyItemRemoved(0);
          }
        return this;
      }

    public UpdateLogAdapter remove(int index){
        if(index>-1&&index<=getItemCount()&&getItemCount()>=1)
          list.remove(index);
        return this;
      }

    public UpdateLogAdapter removeAll(){
        list.clear();
        notifyDataSetChanged();
        return this;
      }

    public update getItem(int index){
        if(index>-1&&index<=getItemCount())
          return list.get(index);
        return null;
      }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup p1,int id){
        ViewHolder v=new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_updatelog,null));
        return v;
      }

    @Override
    public void onBindViewHolder(ViewHolder v,int id){
        final update u=list.get(id);
        v.title.setText(""+u.getTitle());

        final String str="版本号:"+u.getVersionCode()
        +"\n版本名:"+u.getVersionName();
        v.version.setText(""+str);
        v.createdAt.setText(""+u.getCreatedAt());
        v.updatedAt.setText(""+u.getUpdatedAt());
        v.apkfile.setText(""+u.getApkUrl());
        final String content=u.getContent();        
        if(u.getShowType().intValue()==update.TYPE_HTML.intValue()){
            v.content.setText(Html.fromHtml(content));
          }else{
            v.content.setText(content);
          }

        v.content.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                TextView tv=new TextView(ctx);
                tv.setAutoLinkMask(Linkify.ALL);
                tv.setSaveEnabled(true);
                tv.setSelected(true);
                if(u.getShowType().intValue()==update.TYPE_HTML.intValue()){
                    tv.setText(Html.fromHtml(str));
                  }else{
                    tv.setText(str);
                  }
                
                //tv.setAutoLinkMask(Linkify.EMAIL_ADDRESSES|Linkify.PHONE_NUMBERS|Linkify.WEB_URLS);
                tv.setOnClickListener(null);
                new AlertDialog.Builder(ctx)
                .setView(tv)
                .setTitle("内容")
                .setPositiveButton("确定",null)
                .setCancelable(false)
                .show();
              }
          });
      }

    @Override
    public int getItemViewType(int position){
        return super.getItemViewType(position);
      }

    @Override
    public int getItemCount(){
        return list.size();
      }


    public class ViewHolder extends RecyclerView.ViewHolder
      {
        public TextView title;
        public TextView content;
        public TextView createdAt;
        public TextView updatedAt;
        public TextView version;
        public TextView apkfile;
        public Button download;
        public ViewHolder(View v){
            super(v);
            title=(TextView)v.findViewById(R.id.item_updatelog_title);
            content=(TextView)v.findViewById(R.id.item_updatelog_content);
            createdAt=(TextView)v.findViewById(R.id.item_updatelog_createdAt);
            updatedAt=(TextView)v.findViewById(R.id.item_updatelog_updatedAt);
            apkfile=(TextView)v.findViewById(R.id.item_updatelog_apkfile);
            version=(TextView)v.findViewById(R.id.item_updatelog_version);
            download=(Button)v.findViewById(R.id.item_updatelog_download);
            
          }
      }
  }
