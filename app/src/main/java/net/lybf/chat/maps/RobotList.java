package net.lybf.chat.maps;

public class RobotList
  {
    //CreatedAt 2017/4/23 13:43
    private int flag;
    //文章标题
    private String article;
    //来源
    private String source;
    //图片
    private String icon;
    //信息
    private String info;
    //名称
    private String name;
    //链接
    private String detailurl;

    public RobotList setFlag(int flag){
        this.flag=flag;
        return this;
      }

    public int getFlag(){
        return this.flag;
      }

    public RobotList setArticle(String str){
        this.article=str;
        return this;
      }

    public String getArticle(){
        return this.article;
      }

    public RobotList setSource(String source){
        this.source=source;
        return this;
      }

    public String getSource(){
        return this.source;
      }

    public RobotList setIcon(String url){
        this.icon=url;
        return this;
      }

    public String getIcon(){
        return this.icon;
      }

    public RobotList setInfo(String info){
        this.info=info;
        return this;
      }

    public String getInfo(){
        return this.info;
      }

    public RobotList setName(String name){
        this.name=name;
        return this;
      }

    public String getName(){
        return this.name;
      }

    public RobotList setDetailUrl(String url){
        this.detailurl=url;
        return this;
      }

    public String getDetailUrl(){
        return this.detailurl;
      }
  }

