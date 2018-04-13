package com.dnake.slidefragmenttest;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragmentList;
    private ViewPager mViewPager;
    private View viewBar;
    private int currIndex;// 当前页编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initBar();
        initViewPager();
    }

    private void initView() {
        TextView pager1 = (TextView) findViewById(R.id.id_page1);
        TextView pager2 = (TextView) findViewById(R.id.id_page2);
        TextView pager3 = (TextView) findViewById(R.id.id_page3);

        pager1.setOnClickListener(new txListener(0));
        pager2.setOnClickListener(new txListener(1));
        pager3.setOnClickListener(new txListener(2));
    }

    public void initBar() {
        viewBar = (View) findViewById(R.id.view_bar);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        // 得到显示屏宽度
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 设置滚动条宽度为屏幕宽度的三分之一
        int tabLineLength = metrics.widthPixels / 3;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewBar.getLayoutParams();
        lp.width = tabLineLength;
        viewBar.setLayoutParams(lp);
    }

    public void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        fragmentList = new ArrayList<Fragment>();
        Fragment oneFragment = new OneFragment();
        Fragment twoFragment = new TwoFragment();
        Fragment threeFragment = new ThreeFragment();
        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(threeFragment);
        //给ViewPager设置适配器
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mViewPager.setCurrentItem(1);// 设置默认显示标签页为Page Two
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());// 标签页变化时的监听器
    }

    private class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 取得该控件的实例
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) viewBar.getLayoutParams();
            if (currIndex == position) {
                ll.leftMargin = (int) (currIndex * viewBar.getWidth() + positionOffset * viewBar.getWidth());
            } else if (currIndex > position) {
                ll.leftMargin = (int) (currIndex * viewBar.getWidth() - (1 - positionOffset) * viewBar.getWidth());
            }
            viewBar.setLayoutParams(ll);
        }

        @Override
        public void onPageSelected(int position) {
            currIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
