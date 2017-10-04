package net.lybf.chat.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lybf on 2017/9/10.
 */


public class BehaviorDefault extends FloatingActionButton.Behavior
  {

    public BehaviorDefault(Context context,AttributeSet attrs){
        super();
      }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout,final FloatingActionButton child,
    final View directTargetChild,final View target,final int nestedScrollAxes){
        return nestedScrollAxes==ViewCompat.SCROLL_AXIS_VERTICAL
        ||super.onStartNestedScroll(coordinatorLayout,child,directTargetChild,target,nestedScrollAxes);
      }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout,final FloatingActionButton child,
    final View target,final int dxConsumed,final int dyConsumed,
    final int dxUnconsumed,final int dyUnconsumed){
        super.onNestedScroll(coordinatorLayout,child,target,dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed);

        if(dyConsumed>0&&child.getVisibility()==View.VISIBLE){
            child.hide();
          }else if(dyConsumed<0&&child.getVisibility()!=View.VISIBLE){
            child.show();
          }
      }
  }
