package net.lybf.chat.maps;
import java.util.ArrayList;
import net.lybf.chat.maps.RobotList;
public class Robot
  {
    //CreatedAt 2017/4/23 16:50
    public static final int FLAG_ROBOT=0;
    public static final int FLAG_MYSELF=1;
    
    private int flag;
    private int code;
    private String text;
    private String url;
    private ArrayList<RobotList> list;


    public Robot setFlag(int flag){
      this.flag=flag;
      return this;
    }
    
    public int getFlag(){
      return this.flag;
    }
    
    public Robot setCode(int code){
        this.code=code;
        return this;
      }

    public int getCode(){
        return this.code;
      }

    public Robot setText(String text){
        this.text=text;
        return this;
      }

    public String getText(){
        return this.text;
      }

    public Robot setUrl(String url){
        this.url=url;
        return this;
      }

    public String getUrl(){
        return this.url;
      }

    public Robot setList(ArrayList<RobotList> list){
        this.list=list;
        return this;
      }

    public ArrayList<RobotList> getList(){
        return this.list;
      }


  }
