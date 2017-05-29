package net.lybf.chat.util;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import java.io.InputStream;
import android.graphics.Bitmap.Config;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.ByteArrayOutputStream;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;

public class BitmapTools
  {

    public Bitmap download(String url){
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
            new CommonUtil().print(this.getClass(),e);
          }
        return bitmap;
      }



    public byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
        return baos.toByteArray();
      }


    public Bitmap Bytes2Bimap(byte[] b){
        if(b.length!=0)
          return BitmapFactory.decodeByteArray(b,0,b.length);
        else
          return null;
      }



    public Bitmap Drawable2Bitmap(Drawable draw){
        BitmapDrawable drawable=(BitmapDrawable) draw;
        Bitmap bitmap=drawable.getBitmap();
        return bitmap;
      }

    public Drawable Bitmap2Drawable(Bitmap bitmap){
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






    public Bitmap compressImage(Bitmap image,int percent){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = percent;
        image.compress(Bitmap.CompressFormat.JPEG,options,baos);//这里压缩options%，把压缩后的数据存放到baos中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm,null,null);//把ByteArrayInputStream数据生成图片
        return bitmap;
      }

  }
