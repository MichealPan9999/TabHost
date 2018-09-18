package com.example.panzq.tabhost;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

public class MainActivity extends FinalActivity {
    // 如果没有继承TabActivity时，通过该种方法加载启动tabHost
    @ViewInject(id = R.id.tabhost)
    TabHost mTabHost;
    private int[] unSelectedTabIcons = {R.drawable.menu_icon_0_normal, R.drawable.menu_icon_1_normal, R.drawable.menu_icon_2_normal, R.drawable.menu_icon_3_normal};
    private int[] selectedTabIcons = {R.drawable.menu_icon_0_pressed, R.drawable.menu_icon_1_pressed, R.drawable.menu_icon_2_pressed, R.drawable.menu_icon_3_pressed};
    private String[] menuText = {"添加联系人", "通讯录", "备忘录", "设置"};
    private int[] contentItems = {R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabHost.setup();//初始化TabHost,在addTab之前要先调用setup;不和TabActivity关联，通过findViewById获取的TabHost需要调用setup();如果是在TabActivity中通过getTabHost()的方式获取的不需要调用这个方法
        addTabHost();
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                updateTab(mTabHost, tabId);
            }
        });
        mTabHost.setCurrentTab(0);
        ((TextView) mTabHost.getCurrentTabView().findViewById(R.id.tv_indicator)).setTextColor(getResources().getColor(R.color.colorGreen));//第一次进入的时候讲选中的Tab修改文字颜色
        ((ImageView) mTabHost.getCurrentTabView().findViewById(R.id.iv_indicator)).setImageResource(selectedTabIcons[0]);//第一次进入的时候讲选中的Tab修改文字颜色

    }

    private void addTabHost() {
        for (int i = 0; i < 4; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.indicator, null, false);
            TextView textView = (TextView) view.findViewById(R.id.tv_indicator);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_indicator);
            textView.setText(menuText[i]);
            imageView.setImageResource(unSelectedTabIcons[i]);
            mTabHost.addTab(mTabHost.newTabSpec(String.valueOf(i)).setIndicator(view).setContent(contentItems[i]));
        }

    }

    private void updateTab(TabHost tabHost, String tabId) {
        for (int i = 0; i < 4; i++) {
            ((TextView) tabHost.getTabWidget().getChildTabViewAt(i).findViewById(R.id.tv_indicator)).setTextColor(getResources().getColor(R.color.colorBlue));
            ((ImageView) tabHost.getTabWidget().getChildTabViewAt(i).findViewById(R.id.iv_indicator)).setImageResource(unSelectedTabIcons[i]);//第一次进入的时候讲选中的Tab修改文字颜色
        }
        Log.e("panzqww", tabHost.getCurrentTabTag() + " ::: " + tabId);
        if (tabHost.getCurrentTabTag().equals(tabId)) {
            int i = Integer.valueOf(tabId);
            ((TextView) tabHost.getCurrentTabView().findViewById(R.id.tv_indicator)).setTextColor(getResources().getColor(R.color.colorGreen));
            ((ImageView) tabHost.getCurrentTabView().findViewById(R.id.iv_indicator)).setImageResource(selectedTabIcons[i]);//第一次进入的时候讲选中的Tab修改文字颜色
        }//选中的那个Tab文字颜色修改
    }
}
