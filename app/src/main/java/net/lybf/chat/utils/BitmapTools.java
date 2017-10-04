package net.lybf.chat.utils;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import net.lybf.chat.system.Utils;


public class BitmapTools
  {
    //Created by lybf

    public static Context context;
    public Bitmap bitmap;

    public BitmapTools(){

      }

    public BitmapTools(Context context){
        this.context=context;
      }

    public static BitmapTools build(Context context){
        return with(context);
      }

    public static BitmapTools with(Context ctx){
        BitmapTools bitmaptools=new BitmapTools(ctx);
        return bitmaptools;
      }

    public static Bitmap load(int resourceId){
        return BitmapFactory.decodeResource(context.getResources(),resourceId);
      }

    public static Bitmap load(Resources res,int id){
        return BitmapFactory.decodeResource(res,id);
      }

    public static Bitmap load(String path){
        Bitmap bitmap=BitmapFactory.decodeFile(path);
        return bitmap;
      }


    public static Bitmap download(String url){
        Bitmap bitmap=null;
        try{
            URL myUrl;
            myUrl=new URL(url);
            HttpURLConnection conn=(HttpURLConnection)myUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.connect();
            InputStream is=conn.getInputStream();
            bitmap=BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
          }catch(Exception e){
            Utils.print(BitmapTools.class,e);
          }
        return bitmap;
      }



    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
        return baos.toByteArray();
      }


    public static Bitmap Bytes2Bimap(byte[] b){
        if(b.length!=0)
          return BitmapFactory.decodeByteArray(b,0,b.length);
        else
          return null;
      }



    public static Bitmap Drawable2Bitmap(Drawable draw){
        BitmapDrawable drawable=(BitmapDrawable) draw;
        Bitmap bitmap=drawable.getBitmap();
        return bitmap;
      }

    public static Drawable Bitmap2Drawable(Bitmap bitmap){
        BitmapDrawable drawable=new BitmapDrawable(bitmap);
        return drawable;
      }

    public static Bitmap getCircle(Bitmap bitmap){ 
        int width = bitmap.getWidth(); 
        int height = bitmap.getHeight(); 
        int left = 0, top = 0, right = width, bottom = height; 
        float roundPx = height/2; 
        if(width>height){ 
            left=(width-height)/2; 
            top=0; 
            right=left+height; 
            bottom=height; 
          }else if(height>width){ 
            left=0; 
            top=(height-width)/2; 
            right=width; 
            bottom=top+width; 
            roundPx=width/2; 
          } 

        Bitmap output = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888); 
        Canvas canvas = new Canvas(output); 
        int color = 0xff424242; 
        Paint paint = new Paint(); 
        Rect rect = new Rect(left,top,right,bottom); 
        RectF rectF = new RectF(rect); 

        paint.setAntiAlias(true); 
        canvas.drawARGB(0,0,0,0); 
        paint.setColor(color); 
        canvas.drawRoundRect(rectF,roundPx,roundPx,paint); 
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); 
        canvas.drawBitmap(bitmap,rect,rect,paint); 
        return output; 
      } 


    public static Bitmap setColor(int color,int instead,Bitmap bitmap){
        int bitmap_w=bitmap.getWidth();
        int bitmap_h=bitmap.getHeight();
        int[] arrayColor=new int[bitmap_w*bitmap_h];
        int count=0;
        int i=0;
        int temp = 0;
        for(i=0;i<bitmap_h;i++){
            for(int j=0;j<bitmap_w;j++){
                int color1=bitmap.getPixel(j,i);
                if(color1==0){
                    // temp=color1;
                  }else if(color1==color){
                    temp=instead;
                    arrayColor[count]=temp;         
                  }
                count++;
              }
          }
        Bitmap map=Bitmap.createBitmap(arrayColor,bitmap_w,bitmap_h,Config.ARGB_8888);
        return map;
      }

    public static Bitmap setColors(int[] colors,int[] instead,Bitmap bitmap){
        if(colors.length<=0)
          return bitmap;
        if(instead.length<=0)
          return bitmap;
        int i=0;
        int j=colors.length;
        Bitmap bm=bitmap;
        for(;i<j;i++){
            if(i>=instead.length)
              break;
            bm=setColor(colors[i],instead[i],bm);
          }
        return bm;
      }

    public static void setColors(int[] colors,int[] instead,List<Bitmap> bitmaps){
        if(colors.length<=0||instead.length<=0||bitmaps.size()<=0)
          return;
        int i=0;
        int j=colors.length;

        for(;i<j;i++){
            if(i>=instead.length||i>=bitmaps.size())
              break;
            bitmaps.set(i,setColor(colors[i],instead[i],bitmaps.get(i)));
          }
      }


    public static int getColor(Bitmap bitmap,int x,int y){
        int result = 0;
        result=bitmap.getPixel(x,y);
        return result;
      }

    public static Bitmap compress(Bitmap image,int percent){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = percent;
        image.compress(Bitmap.CompressFormat.JPEG,options,baos);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm,null,null);
        return bitmap;
      }


    public static ImageLoader getImageLoader(Context context){
        return ImageLoader.with(context);
      }

    public static class ImageLoader
      {
        private static ImageLoader INSTANCE;
        private static Context context;

        private ImageLoader(){
          
        }
        
        public static ImageLoader with(Context context){
            if(INSTANCE==null)
              INSTANCE=new ImageLoader();
            INSTANCE.context=context;
            return INSTANCE;
          }
        public static Bitmap load(int resourceId){
            return BitmapFactory.decodeResource(context.getResources(),resourceId);
          }

        public static Bitmap load(Resources res,int id){
            return BitmapFactory.decodeResource(res,id);
          }

        public static Bitmap load(String path){
            Bitmap bitmap=BitmapFactory.decodeFile(path);
            return bitmap;
          }

      }
  }
