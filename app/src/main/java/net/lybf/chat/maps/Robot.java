package net.lybf.chat.maps;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import net.lybf.chat.util.ChatException;
//import net.lybf.chat.maps.RobotList;
public class Robot
  {
    //CreatedAt 2017/4/23 16:50
    public static final int FLAG_ROBOT=0;
    
    public static final int FLAG_MYSELF=1;

    private int flag;
    
    private int code;
    
    private String text;
    
    private String url;
    
    private String name;
    
    private List<RobotList> list;

    public Robot setName(String name){
        this.name=name;
        return this;
      }

    public String getName(){
        return this.name;
      }

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

    public Robot setList(List<RobotList> list){
        this.list=list;
        return this;
      }

    public List<RobotList> getList(){
        return this.list;
      }



    public Robot setValue(String value,Object obj)throws Exception{
        Class css=this.getClass();
        Field var=css.getDeclaredField(value);
        Class c= obj.getClass();
        boolean is=var.getType().equals(c);
        if(is){
            var.set(this,obj);
          }else{
            StringBuffer sb=new StringBuffer();
            sb.append("类型不符合\n");
            sb.append("要修改的值为:");
            sb.append(var.getType().toString());
            sb.append(" 传入值为:");
            sb.append(obj.getClass());
            throw new ChatException(sb.toString(),10000);
          }
        return this;
      }


    public Object getValue(String name)throws Exception{
        Class css=this.getClass();
        Field var=css.getDeclaredField(name);
        Object object=var.get(this);
        return object;
      }
    
    public class RobotList
      {
        //CreatedAt 2017/4/23 13:43
        private int flag;
        //文章标题
        private String article;
        //来源
        private String source;
        //图片
        private String icon;
        //信息
        private String info;
        //名称
        private String name;
        //链接
        private String detailurl;

        public RobotList setFlag(int flag){
            this.flag=flag;
            return this;
          }

        public int getFlag(){
            return this.flag;
          }

        public RobotList setArticle(String str){
            this.article=str;
            return this;
          }

        public String getArticle(){
            return this.article;
          }

        public RobotList setSource(String source){
            this.source=source;
            return this;
          }

        public String getSource(){
            return this.source;
          }

        public RobotList setIcon(String url){
            this.icon=url;
            return this;
          }

        public String getIcon(){
            return this.icon;
          }

        public RobotList setInfo(String info){
            this.info=info;
            return this;
          }

        public String getInfo(){
            return this.info;
          }

        public RobotList setName(String name){
            this.name=name;
            return this;
          }

        public String getName(){
            return this.name;
          }

        public RobotList setDetailUrl(String url){
            this.detailurl=url;
            return this;
          }

        public String getDetailUrl(){
            return this.detailurl;
          }
      }

  }
