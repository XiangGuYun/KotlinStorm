package com.kotlinbase.kotlin.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.kotlindemo.base.utils.DensityUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Destroyable;

/**
 * Created by asus on 2018/1/13.
 fragPagerUtils(vp, arrayListOf(MainFragment(),MainFragment(),MainFragment()))
 .addTabLayout(tabLayout,true) { tab, index ->
 tab.text = "Fragment${index+1}"
 }
 */

public class FragPagerUtils<T extends Fragment>{

    private ViewPager viewPager;
    private ArrayList<T> fragments;
    private FragAdapter adapter;
    private Context ctx;
    private int tabNum;
    private TabLayout tabLayout;

    /**
     * 当不需要ViewPager，只使用Fragment时，采用该构造方法
     * @param act
     */
    public FragPagerUtils(FragmentActivity act){
        ctx = act;
    }

    /**
     * 当需要ViewPager和Fragment结合使用时，采用该构造方法
     * @param act
     * @param viewPager
     * @param fragments
     */
    public FragPagerUtils(FragmentActivity act, ViewPager viewPager, ArrayList<T> fragments) {
        ctx = act;
        this.viewPager = viewPager;
        this.fragments = fragments;
        tabNum = fragments.size();
        adapter = new FragAdapter(act.getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }

    /**
     * 添加TabLayout
     * @param tl
     * @param hasVP 是否有ViewPager
     * @param isScroll Tab栏是否可滑动
     * @param listener 点击Tab监听
     */
    public void addTabLayout(TabLayout tl, boolean hasVP, boolean isScroll, TabListener listener){
        tabLayout = tl;
        if(isScroll){
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置滑动Tab模式
        }else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置固定Tab模式
        }

        for (int i = 0; i < tabNum; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }
        if(hasVP){
            //将TabLayout和ViewPager关联起来
            tabLayout.setupWithViewPager(viewPager, true);
        }
        //Tab属性必须在关联ViewPager之后设置
        for (int i = 0; i < tabNum; i++) {
            listener.setTabContent(tabLayout.getTabAt(i), i);
        }
    }

    public void addTabLayout(TabLayout tabLayout, boolean hasVP, TabListener listener){
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置固定Tab模式
        for (int i = 0; i < tabNum; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }
        if(hasVP){
            //将TabLayout和ViewPager关联起来
            tabLayout.setupWithViewPager(viewPager, true);
        }
        //Tab属性必须在关联ViewPager之后设置
        for (int i = 0; i < tabNum; i++) {
            listener.setTabContent(tabLayout.getTabAt(i), i);
        }
    }

    public void setScroll(boolean isScroll){
        if(isScroll){
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置滑动Tab模式
        }else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置固定Tab模式
        }
    }



    /**
     * 设置指示条宽度
     * @param tabLayout
     * @param marginLeft
     * @param marginRight
     */
    public void setUpIndicatorWidth(TabLayout tabLayout, int marginLeft, int marginRight) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setMarginStart(DensityUtils.Companion.dip2px(ctx, marginLeft));
                    params.setMarginEnd(DensityUtils.Companion.dip2px(ctx, marginRight));
                }
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public interface TabListener{
        void setTabContent(TabLayout.Tab tab, int index);
    }

    public interface PagerSelectListener{
        void select(int pos);
    }


    public ViewPager getViewPager() {
        return viewPager;
    }

    public List<T> getFragments() {
        return fragments;
    }

    public FragAdapter getAdapter() {
        return adapter;
    }

    public class FragAdapter extends FragmentStatePagerAdapter {

        private List<T> fragments;

        public FragAdapter(FragmentManager fm, List<T> fragments) {
            super(fm);
            this.fragments=fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return PagerAdapter.POSITION_NONE;
        }

    }
}
