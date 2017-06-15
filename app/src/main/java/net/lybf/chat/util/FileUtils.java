package net.lybf.chat.util;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class FileUtils
  {

    private File file;

    public FileUtils(File file){
        this.file=file;
      }

    public FileUtils setFile(File file){
        this.file=file;
        return this;
      }

    public List<File> getAllFile(){
        List<File> list=new ArrayList<File>();
        collectFile(list,file);
        return list;
      }

    public void deleteAllFile(){
        List<File> l=getAllFile();
        for(File p:l){
            p.delete();
          }
      }

    public void deleteNullDirectory(){
        
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
