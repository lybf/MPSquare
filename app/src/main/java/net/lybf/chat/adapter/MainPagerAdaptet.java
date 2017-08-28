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

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        view.addView(views.get(position));
        return views.get(position);
      }
  }

