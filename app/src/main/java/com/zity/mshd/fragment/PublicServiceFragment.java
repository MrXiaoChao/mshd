package com.zity.mshd.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;
import com.zity.mshd.R;
import com.zity.mshd.adapter.TablayoutAdapter;
import com.zity.mshd.base.BaseFragment;
import com.zity.mshd.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 公共服务
 * Created by luochao on 2017/7/18.
 */

public class PublicServiceFragment extends BaseFragment {

    @BindView(R.id.tlMain)
    TabLayout tlMain;
    @BindView(R.id.vpMain)
    ViewPager vpMain;
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;

    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("医疗");
        add("交通");
        add("农业");
        add("住房");
    }};
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(new YiLiaoFragment());
        add(new JiaoTongFragment());
        add(new NongYeFragment());
        add(new ZhuFangFragment());
    }};


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_public_service;
    }

    @Override
    protected void initData() {
        ivToolbarBack.setVisibility(View.GONE);
        tvTooltarTitle.setText("公共服务");
        vpMain.setOffscreenPageLimit(4);
        TablayoutAdapter adapter = new TablayoutAdapter(getActivity().getSupportFragmentManager(), titleList, fragmentList);
        vpMain.setAdapter(adapter);
        tlMain.setupWithViewPager(vpMain, true);
        tlMain.setTabsFromPagerAdapter(adapter);

        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onMoonEvent(MessageEvent messageEvent){
        if (StringUtils.equals("0",messageEvent.getMessage())){
            tlMain.getTabAt(0).select();
        }else if (StringUtils.equals("1",messageEvent.getMessage())){
            tlMain.getTabAt(1).select();
        }else if (StringUtils.equals("2",messageEvent.getMessage())){
            tlMain.getTabAt(2).select();
        }else if (StringUtils.equals("3",messageEvent.getMessage())){
            tlMain.getTabAt(3).select();
        }
    }

}
