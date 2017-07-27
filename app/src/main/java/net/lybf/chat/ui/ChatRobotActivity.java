package net.lybf.chat.ui;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.BmobUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.lybf.chat.MainApplication;
import net.lybf.chat.R;
import net.lybf.chat.activity.MPSActivity;
import net.lybf.chat.adapter.RobotAdapter;
import net.lybf.chat.bmob.MyUser;
import net.lybf.chat.maps.Robot;
import net.lybf.chat.system.Paths;
import net.lybf.chat.system.Utils;
import net.lybf.chat.system.settings;
import net.lybf.chat.util.TuLingRobot;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatRobotActivity extends MPSActivity
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
        app=getMainApplication();
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
        if(user!=null)
          robot.setUserID(user.getObjectId());
        else
          robot.setUserID(null);
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

                String[] order=str.split(" ");
                if(order.length>=1){
                    if(order[0].equals("///")){
                        boolean bool= runOrder(str);
                        if(bool)return;
                      }
                  }


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


    private String format(String str){
        Gson gson3 = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(""+str);
        String format= gson3.toJson(je);
        return format;
      }
    public void print(Exception e){
        Utils.print(this.getClass(),e);
      }



    private String[] ORDER_HELP={
      "欢迎使用MPSquare指令帮助，怎么使用指令？您只需把指令用输入法打出来，然后按[发送]即可，以下是支持的指令",
      "/// [指令]",
      "clearScreen(清除聊天信息)",
      "clearLoginData(清除登录记录)",
      "export(导出聊天记录)",
      "import [文件夹路径](导入MPSquare缓存)",
      "output [文件夹目录](导出MPSquare缓存)",
      "openMenu(打开UI窗口式指令)",
      "closeMenu(关闭UI窗口)",
      "runApp [包名/应用名称](运行App)"
      };

    private boolean runOrder(String orders){
        String[] order=orders.split(" ");
        if(order.length>=1){
            if(order[0].equals("///")){
                switch(order.length){
                    case 2:
                      switch(order[1]){
                          case "help":
                            Robot rt=new Robot();
                            StringBuilder sb=new StringBuilder();
                            for(String string:ORDER_HELP){
                                sb.append(string+"\n");
                              }
                            rt.setFlag(rt.FLAG_ROBOT);
                            rt.setName("系统");
                            rt.setText(sb.toString());
                            adapter.additem(rt);
                            int size=adapter.getItemCount();
                            mRecyclerView.scrollToPosition(size>=1?size-1:size);
                            edit.setText("");
                            return true;//break;

                          case "clearScreen":
                            adapter.clearAll();
                            edit.setText("");
                            return true;//break;

                          case "export":
                            Utils.print(this.getClass(),"startEXPORT");
                            File f=new File(Paths.LOG_ROBOT_CHAT);
                            if(!f.exists())f.mkdirs();
                            String da= new SimpleDateFormat("yyyyMMddHHmm").format(new Date(System.currentTimeMillis()));
                            File fp=new File(f.getAbsolutePath()+"/"+da+".json");
                            FileOutputStream out;
                            JSONArray array=new JSONArray();
                            try{
                                out=new FileOutputStream(fp);
                                ArrayList<Robot> list=adapter.getAllData();
                                for(Robot r:list){
                                    Robot robot=r;
                                    String json=new Gson().toJson(robot);
                                    JSONObject pm=new JSONObject(json);
                                    Utils.print(this.getClass(),pm);
                                    array.put(pm);
                                  }
                                String format=format(array.toString());
                                Utils.print(this.getClass(),format);
                                out.write(format.getBytes());
                                edit.setText("");
                                return true;
                              }catch(Exception e){
                                Utils.print(this.getClass(),e);
                              }

                            return true;// break;

                          case "clearLoginData":

                            return true;//break;

                          case "import":
                            return true;//break;

                          case "output":
                            return true;//break;

                          default:
                            return false;

                        }//switch

                    case 3:
                      if(order[1].equals("runApp")){
                          String app=order[2];
                          PackageManager pm=this.getPackageManager();
                          List<PackageInfo> info= pm.getInstalledPackages(
                          PackageManager.GET_UNINSTALLED_PACKAGES);
                          // pm.getInstalledApplications(pm.GET_UNINSTALLED_PACKAGES);
                          for(PackageInfo in:info){
                              ApplicationInfo infom=in.applicationInfo;
                              if(infom.loadLabel(pm).equals(app)){

                                  try{

                                      Intent intent=new Intent(this,Class.forName(infom.className));
                                      //  intent.setAction(
                                      this.startActivity(intent);
                                    }catch(ClassNotFoundException e){
                                      e.printStackTrace();
                                    }
                                }

                            }
                        }

                      return true;
                    default:
                      return false;
                  }// switch
              }// if
          }// if
        return false;
      }//runOrder
  }
