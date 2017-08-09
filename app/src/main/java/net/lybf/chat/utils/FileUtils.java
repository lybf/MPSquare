package net.lybf.chat.utils;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils
  {

    private static File file;

    public FileUtils(File file){
        this.file=file;
      }

    public FileUtils setFile(File file){
        this.file=file;
        return this;
      }


    public File getFile(){
        return this.file;
      }
    
    //@Return FileInputStream
    public InputStream openFileInputStream() throws FileNotFoundException{
        return new FileInputStream(this.file);
      }

    //@Return  FileOutpuStream
    public OutputStream getOutpuStream() throws FileNotFoundException{
        return new FileOutputStream(file);
      }

    public FileOutputStream getOutpuStream(boolean append) throws FileNotFoundException{
        return new FileOutputStream(file,append);
      }

    public List<File> getAllFile(){
        List<File> list=new ArrayList<File>();
        collectFile(list,file);
        return list;
      }

    public FileUtils getAllFile(List<File> list,File file){
        collectFile(list,file);
        return this;
      }

    public void deleteAllFile(){
        List<File> l=getAllFile();
        for(File p:l){
            p.delete();
          }
      }

    public void deleteNullDirectory(){
        List<File> l=getAllFile();
        for(File file:l){
            if(file.isDirectory()){
                File[] files=file.listFiles();
                if(files.length==0){
                    file.delete();
                  }
              }
          }
      }

    private void collectFile(List<File> list,File file){
        if(list!=null){
            System.out.println("传入的List集合是空的");
            return;
          }
        if(file.isDirectory()){
            File[] fl=file.listFiles();
            for(File f:fl){
                if(f.isDirectory())
                  collectFile(list,f);
                else
                  list.add(f);
              }//end for
          }else
          list.add(file);
      }
  }
