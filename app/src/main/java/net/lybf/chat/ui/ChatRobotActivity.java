package net.lybf.chat.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import net.lybf.chat.R;
import net.lybf.chat.adapter.RobotAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.View;
import net.lybf.chat.system.Utils;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.text.TextUtils;
import net.lybf.chat.util.TuLingRobot;
import net.lybf.chat.maps.Robot;
import net.lybf.chat.bmob.MyUser;
import cn.bmob.v3.BmobUser;
import net.lybf.chat.MainApplication;
import net.lybf.chat.system.settings;

public class ChatRobotActivity extends AppCompatActivity
  {

    private RecyclerView mRecyclerView;

    private RobotAdapter adapter;

    private Button send;

    private EditText edit;

    private TuLingRobot robot;

    private MyUser user;

    private MainApplication app;

    private settings set;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        app=new MainApplication();
        set=app.getSettings();
        if(set.isDark()){
            setTheme(R.style.DarkTheme);
          }else{
            setTheme(R.style.LightTheme);
          }
        setContentView(R.layout.activity_about);

        
        setContentView(R.layout.content_robot);
        mRecyclerView=(RecyclerView)findViewById(R.id.content_robot_listview);
        send=(Button) findViewById(R.id.content_robot_send);
        edit=(EditText)findViewById(R.id.content_robot_edittext);

        robot=new TuLingRobot();

        user=new BmobUser().getCurrentUser(MyUser.class);

        adapter=new RobotAdapter(this);
        mRecyclerView.setEnabled(true);
        mRecyclerView.setFocusable(true);



        LinearLayoutManager Manager = new LinearLayoutManager(this);         
        Manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(Manager); 
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        try{
            adapter.setOnItemClickListener(new RobotAdapter.OnItemClickListener(){
                @Override
                public void onClick(View view,int index){

                  }
              });
          }catch(Exception e){
            print(e);
          }

        send.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View p1){
                String str=edit.getText().toString();
                // if(str.length()<=0){
                Robot r=new Robot();
                if(user!=null)
                  r.setName(user.getUsername());
                else
                  r.setName("游客");
                r.setFlag(Robot.FLAG_MYSELF);
                r.setText(str);
                adapter.additem(r);
                int size=adapter.getItemCount();
                mRecyclerView.scrollToPosition(size>=1?size-1:size);
                robot.send(str,new TuLingRobot.TuLingRobotListener(){
                    @Override
                    public void done(Robot result){
                        adapter.additem(result);
                        int size=adapter.getItemCount();
                        mRecyclerView.scrollToPosition(size>=1?size-1:size);
                      }  
                  });
                edit.setText("");

              }   
          }
        );

        edit.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
            /*  Manager.
                int size=adapter.getItemCount();
                mRecyclerView.scrollToPosition(size>=1?size-1:size);
                */
              }
          });
      }


    public void print(Exception e){
        new Utils().print(this.getClass(),e);
      }
  }
