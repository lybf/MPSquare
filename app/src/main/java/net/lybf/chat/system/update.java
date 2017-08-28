package net.lybf.chat.system;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

public class update
  {
    public static final Number TYPE_HTML=0;
    public static final Number TYPE_TEXT=1;

    private String objectid;
    private Number showType;
    private String content;
    private String apkurl;
    private Number versionCode;
    private String versionName;
    private Number level;

    private String createdAt;

    private String updatedAt;

    private String title;

    public update setObjecId(String Id){
        this.objectid=Id;
        return this;
      }

    public String getObjectId(){
        return this.objectid;
      }

    public update setTitle(String title){
        this.title=title;
        return this;
      }

    public String getTitle(){
        return this.title;
      }

    public update setShowType(Number i){
        this.showType=i;
        return this;
      }

    public Number getShowType(){
        return this.showType;
      }

    public update setContent(String content){
        this.content=content;
        return this;
      }

    public String getContent(){
        return this.content;
      }

    public update setApkUrl(String url){
        this.apkurl=url;
        return this;
      }

    public String getApkUrl(){
        return this.apkurl;
      }

    public update setVersionCode(Number num){
        this.versionCode=num;
        return this;
      }

    public Number getVersionCode(){
        return this.versionCode;
      }

    public update setVersionName(String name){
        this.versionName=name;
        return this;
      }

    public String getVersionName(){
        return this.versionName;
      }

    public update setLevel(Number num){
        this.level=num;
        return this;
      }

    public Number getLevel(){
        return this.level;
      }


    public update setCreatedAt(String created){
        this.createdAt=created;
        return this;
      }
    public String getCreatedAt(){
        return this.createdAt;
      }



    public update setUpdatedAt(String update){
        this.updatedAt=update;
        return this;
      }

    public String getUpdatedAt(){
        return this.updatedAt;
      }

    public JSONObject toJson(){
        String str=new Gson().toJson(this);
        JSONObject json = null;
        try{
            json=new JSONObject(str);
          }catch(JSONException e){
            
          }
        return json;
      }

    public String toString(){
        Gson gson3 = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(""+toJson());
        String format= gson3.toJson(je);
        return format;
      }
  }
