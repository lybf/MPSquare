package net.lybf.chat.util;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import net.lybf.chat.bmob.Comment;
import net.lybf.chat.system.Utils;

public class CommentCount
  {
    private String id;

    private  CommentCountListener listener;

    private Utils utils;

    public interface  CommentCountListener
      {
         void done(int i,BmobException e);
      }
    public CommentCount setPostID(String postid){
        id=postid;
        return this;
      }

    public void count(CommentCountListener listene){
        this.listener=listene;
        BmobQuery<Comment> bmobQuery= new BmobQuery<Comment>();
        bmobQuery.addWhereEqualTo("parent",id);
        boolean cache=bmobQuery.hasCachedResult(Comment.class);
        Class css=this.getClass();
        utils.print(css,"has cache:"+cache);
        if(new Network().isConnected())
          bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        else
          bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);
        bmobQuery.count(Comment.class,new CountListener(){
            @Override
            public void done(Integer p1,BmobException p2){
                if(p2==null){
                    new Utils().    print(String.format("帖子id:%s评论数:%s\n",id,p1));
                    listener.done(p1,null);
                  }else{
                    new Utils().print(String.format("帖子id:%s评论数:%s\n",id,p1));
                    listener.done(0,p2);
                  }
              }
          });
      }


  }
