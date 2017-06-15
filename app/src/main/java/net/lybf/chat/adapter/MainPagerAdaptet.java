package net.lybf.chat.adapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class MainPagerAdaptet extends PagerAdapter
  {


    private String[] mTitles;
    private List<View> views;

    public MainPagerAdaptet(String[] mTitles,List<View> views){
        this.mTitles=mTitles;
        this.views=views;
      }

    @Override
    public int getCount(){
        return views.size();
      }

    @Override
    public CharSequence getPageTitle(int position){
        return mTitles[position];
      }
    
    public View getItem(int position){
        return views.get(position);
      }


    @Override
    public boolean isViewFromObject(View p1,Object p2){
        return p1==p2;
      }
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(views.get(position));
      }

    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        view.addView(views.get(position));
        return views.get(position);
      }
  }

