package net.lybf.chat.maps;
import android.graphics.Bitmap;

public class MainTools
  {
    //CreatedAt 2017/4/22 13:00
    private String TAG;
    private Bitmap draw;
    private String describe;

    public MainTools setTAG(String Tag){
        this.TAG=Tag;
        return this;
      }

    public String getTAG(){
        return this.TAG;
      } 

    
    public MainTools setBitmap(Bitmap draw){
        this.draw=draw;
        return this;
      }

    public Bitmap getBitmap(){
        return this.draw;
      }

    public MainTools setDescribe(String describe){
        this.describe=describe;
        return this;
      }

    public String getDescribe(){
        return this.describe;
      }
  }
