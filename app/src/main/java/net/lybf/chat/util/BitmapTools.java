package net.lybf.chat.util;
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


    public Bitmap setColor(int color,int instead,Bitmap bitmap){
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
        bitmap.recycle();
        Bitmap map=Bitmap.createBitmap(arrayColor,bitmap_w,bitmap_h,Config.ARGB_8888);
        return map;
      }


    public int getColor(Bitmap bitmap,int x,int y){
        int result = 0;
        result=bitmap.getPixel(x,y);
        return result;
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
