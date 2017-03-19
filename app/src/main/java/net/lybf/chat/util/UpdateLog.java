package net.lybf.chat.util;
import net.lybf.chat.system.update;
import org.json.JSONObject;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import org.json.JSONArray;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.json.JSONException;
import net.lybf.chat.system.Utils;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

public class UpdateLog
  {

	private update update;

	private String rootPath="/sdcard/lybf/MPSquare/ApplicationUpdate.json";
	private File file;
	private JSONArray rootjson;


	public UpdateLog(){
		this.update=new update();
		refresh();
		order(true);
	  }

	public UpdateLog refresh(){
		file=new File(rootPath);
		if(!file.getParentFile().exists())
		  file.getParentFile().mkdirs();
		if(file.exists()){
			try{
				InputStream in=new FileInputStream(file);
				byte[] by=new byte[1024*1024];
				in.read(by);
				rootjson=new JSONArray(new String(by));
			  }catch(Exception e){
				print(e);
			  }
		  }else{
			try{
				rootjson=new JSONArray("[]");
				save();
			  }catch(Exception e){
				print(e);
			  }
		  }
		return this;
	  }

	public List<update> getAllUpdateLog(){
		List list=new ArrayList();
		int j=rootjson.length();
		for(int i=0;i<j;i++){
			try{
				JSONObject obj=new JSONObject(rootjson.get(i).toString());
				Number num=(Number) obj.get("showType");
				list.add(((update)new Gson().fromJson(rootjson.get(i).toString(),update.class)).setShowType(num));
			  }catch(Exception e){
				print(e);
			  }
		  }
		return list;
	  }


	/*
	 @param index 获取指定信息
	 */
	public update getItem(int index){
		if(index>count()&&index<0)
		  return null;
		return new Gson().fromJson(rootjson.opt(index).toString(),update.class);
	  }

	public int count(){
		return rootjson.length();
	  }


	/*
	 @param up 日志记录 class类
	 */
	public UpdateLog addItem(update up){
		if(!isExists(up)){
			try{
				rootjson.put(new JSONObject(new Gson().toJson(up)));
			  }catch(Exception e){
				print(e);
			  }
			save();
		  }
		return this;
	  }

	/*
	 @param up 日志记录
	 */
	public UpdateLog addItemAtFirst(update up){
		try{
			if(!isExists(up)){
				rootjson.put(0,new Gson().toJson(up));
				save();
			  }
		  }catch(Exception e){
			print(e);
		  }
		return this;
	  }

	/*
	 @param up 日志记录 class类
	 */
	private boolean isExists(update up){
		int j=count();
		for(int i=0;i<j;i++){
			if(up.equals(new Gson().fromJson(rootjson.opt(i).toString(),update.class))
			||new Gson().toJson(up).equals(rootjson.opt(i)))
			  return true;
		  }
		return false;
	  }


	/*
	 @param order 排序
	 */
	public UpdateLog order(boolean order){
		try{
			if(order){
				int l=count();
				for(int i=0;i<l;i++){
					try{
						JSONObject temp;
						update up=new Gson().fromJson((temp=new JSONObject(""+rootjson.opt(i))).toString(),update.class);
						for(int p=0;p<l;p++){
							JSONObject temp2;
							update po=new Gson().fromJson((temp2=new JSONObject(""+rootjson.opt(p))).toString(),update.class);

							boolean remove=false;
							if(isExists(po)  &&i!=p){
								rootjson.remove(p);
								remove=true;
							  }	
							if(!remove){
								SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								try{
									Date d2=da.parse(up.getCreatedAt());
									Date d3=da.parse(po.getCreatedAt());
									if(d3.getTime()-d2.getTime()<0){
									  }else{
										rootjson.remove(i);
										rootjson.put(i,temp2);
										rootjson.remove(p);
										rootjson.put(p,temp);
									  }
								  }catch(Exception e){
									print(e);
								  }
							  }
						  }
					  }catch(Exception e){
						print(e);
					  }
				  }
			  }
		  }catch(Exception e){
			print(e);
		  }
		save();
		return this;
	  }


	public UpdateLog save(){
		try{
			Gson gson3 = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(rootjson.toString());
			String format= gson3.toJson(je);
			new FileOutputStream(new File(rootPath))
			.write(format.getBytes());
		  }catch(Exception e){
			print(e);
		  }
		return this;
	  }

	private void print(Object obj){
		new Utils().print(this.getClass(),obj);
	  }
  }
