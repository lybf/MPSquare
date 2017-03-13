package net.lybf.chat.ui;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.content.Context;
import android.os.Bundle;
import net.lybf.chat.R;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import cn.bmob.v3.BmobQuery;
import net.lybf.chat.bmob.UpdateLog;
import cn.bmob.v3.listener.FindListener;
import java.util.List;
import cn.bmob.v3.exception.BmobException;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.update;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import net.lybf.chat.adapter.UpdateLogAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import net.lybf.chat.util.Network;

public class UpdateLogActivity extends AppCompatActivity
  {

	private Toolbar bar;
	private net.lybf.chat.util.UpdateLog log;

	private RecyclerView mListView;

	private UpdateLogAdapter adapter;

	private Network net;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatelog);
		log=new net.lybf.chat.util.UpdateLog();
		net=new Network(this);

		bar=(Toolbar)findViewById(R.id.updatelog_toolbar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		initView();
		initData();
	  }

	private void initData(){
		if(net.isNetWork()){
			BmobQuery query=new BmobQuery<UpdateLog>();
			query.order("-createdAt");
			query.setLimit(1000);
			query.findObjects(new FindListener<UpdateLog>(){
				@Override
				public void done(List<UpdateLog> p1,BmobException e){
					if(e==null){
						for(UpdateLog l:p1){
							update up=new update();
							up.setApkUrl(l.getApkFile());
							up.setContent(l.getMessage());
							up.setCreatedAt(l.getCreatedAt());
							up.setLevel(l.getLevel());
							up.setShowType(l.getShowType().intValue());
							up.setUpdatedAt(l.getUpdatedAt());
							up.setVersionCode(l.getVersionCode());
							up.setVersionName(l.getVersionName());
							up.setTitle(l.getTile());
							log.addItem(up);
							adapter.addItem(up);
						  }
					  }else{
						print(e);
					  }
				  }
			  });

		  }else{
			try{
				if(log.count()>0){
					List l=log.getAllUpdateLog();
					for(int i=0;i<l.size();i++){
						adapter.addItem((update)l.get(i));
					  }
				  }
			  }catch(Exception e){
				print(e);
			  }
		  }


	  }


	private void initView(){
		mListView=(RecyclerView)findViewById(R.id.updatelog_listview);
		mListView.setAdapter((adapter=new UpdateLogAdapter(this)));
		LinearLayoutManager Manager = new LinearLayoutManager(this); 		
		Manager.setOrientation(LinearLayoutManager.VERTICAL);
		mListView.setLayoutManager(Manager); 
		mListView.setItemAnimator(new DefaultItemAnimator());

	  }
	@Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
		      this.finish();
              break;
		  }
		return true;
	  }
	private void print(Object e){
		new Utils().print(this.getClass(),e);
	  }

  }
