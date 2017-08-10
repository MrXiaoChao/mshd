package com.zity.mshd.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.zity.mshd.R;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseActivity;
import com.zity.mshd.fragment.HomePageFragment;
import com.zity.mshd.fragment.MineFragment;
import com.zity.mshd.fragment.MyAppealFragment;
import com.zity.mshd.fragment.PublicServiceFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{

    @BindView(R.id.tv_homepae)
    TextView tvHomepae;
    @BindView(R.id.tv_myappeal)
    TextView tvMyAppeal;
    @BindView(R.id.tv_public_service)
    TextView tvPublicService;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    private long backtime=0;
    private HomePageFragment homePageFragment;
    private MyAppealFragment myAppealFragment;
    private PublicServiceFragment publicServiceFragment;
    private MineFragment mineFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;

    }

    @Override
    protected void initData() {
        defaultSelected();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //默认选中
    private void defaultSelected(){
        selected();
        tvHomepae.setSelected(true);
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        if (homePageFragment == null) {
            homePageFragment = new HomePageFragment();
            transaction.add(R.id.fragment_container, homePageFragment);
        } else {
            transaction.show(homePageFragment);
        }
        transaction.commit();
    }
    //重置所有文本的选中状态
    public void selected() {
        tvHomepae.setSelected(false);
        tvMyAppeal.setSelected(false);
        tvPublicService.setSelected(false);
        tvMine.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction) {
        if (homePageFragment != null) {
            transaction.hide(homePageFragment);
        }
        if (myAppealFragment != null) {
            transaction.hide(myAppealFragment);
        }
        if (publicServiceFragment != null) {
            transaction.hide(publicServiceFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    @OnClick({R.id.tv_homepae,R.id.tv_myappeal,R.id.tv_public_service, R.id.tv_mine})
    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (view.getId()) {
            case R.id.tv_homepae:
                selected();
                tvHomepae.setSelected(true);
                if (homePageFragment == null) {
                    homePageFragment = new HomePageFragment();
                    transaction.add(R.id.fragment_container, homePageFragment);
                } else {
                    transaction.show(homePageFragment);
                }
                break;

            case R.id.tv_myappeal:
                selected();
                tvMyAppeal.setSelected(true);
                if (myAppealFragment == null) {
                    myAppealFragment = new MyAppealFragment();
                    transaction.add(R.id.fragment_container, myAppealFragment);
                } else {
                    transaction.show(myAppealFragment);
                }
                break;
            case R.id.tv_public_service:
                selected();
                tvPublicService.setSelected(true);
                if (publicServiceFragment == null) {
                    publicServiceFragment = new PublicServiceFragment();
                    transaction.add(R.id.fragment_container, publicServiceFragment);
                } else {
                    transaction.show(publicServiceFragment);
                }
                break;
            case R.id.tv_mine:
                selected();
                tvMine.setSelected(true);
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fragment_container, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
    }

    //是否退出客户端
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long t = System.currentTimeMillis();
            if (t - backtime > 3000) {
                ToastUtils.showShortToast("再按一次退出应用");
                backtime = t;
                return true;
            }
            App.getInstance().exitApp();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    //切换到诉求页面的方法
    public void selectProgress(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        selected();
        tvMyAppeal.setSelected(true);
        if (myAppealFragment == null) {
            myAppealFragment = new MyAppealFragment();
            transaction.add(R.id.fragment_container, myAppealFragment);
        } else {
            transaction.show(myAppealFragment);
        }
        transaction.commit();
    }

    //切换到服务页面的方法
    public void selectService(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        selected();
        tvPublicService.setSelected(true);
        if (publicServiceFragment == null) {
            publicServiceFragment = new PublicServiceFragment();
            transaction.add(R.id.fragment_container, publicServiceFragment);
        } else {
            transaction.show(publicServiceFragment);
        }
        transaction.commit();
    }

}
