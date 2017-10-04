package net.lybf.chat.utils;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import net.lybf.chat.bmob.Comment;
import net.lybf.chat.system.Utils;
import net.lybf.chat.bmob.Post;

public class CommentCount
  {
    private Post id;

    private  CommentCountListener listener;

    public interface  CommentCountListener
      {
        void done(int i,BmobException e);
      }
      
    public CommentCount setPost(Post postid){
        id=postid;
        return this;
      }

    public void count(CommentCountListener listene){
        this.listener=listene;
        BmobQuery<Comment> bmobQuery= new BmobQuery<Comment>();
        bmobQuery.addWhereEqualTo("post",id);
        boolean cache=bmobQuery.hasCachedResult(Comment.class);
        Class css=this.getClass();
        Utils.print(css,"has cache:"+cache);
        if(new Network().isConnected())
          bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        else
          bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);
        bmobQuery.count(Comment.class,new CountListener(){
            @Override
            public void done(Integer p1,BmobException p2){
                if(p2==null){
                    Utils.print(String.format("帖子id:%s评论数:%s\n",id,p1));
                    listener.done(p1,null);
                  }else{
                    Utils.print(String.format("帖子id:%s评论数:%s\n",id,p1));
                    listener.done(0,p2);
                  }
              }
          });
      }


  }
