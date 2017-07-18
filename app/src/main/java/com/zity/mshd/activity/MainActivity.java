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
import com.zity.mshd.fragment.AppealFragment;
import com.zity.mshd.fragment.HomePageFragment;
import com.zity.mshd.fragment.MineFragment;
import com.zity.mshd.fragment.ProgressFragment;
import com.zity.mshd.fragment.PublicServiceFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{

    @BindView(R.id.tv_homepae)
    TextView tvHomepae;
    @BindView(R.id.tv_appeal)
    TextView tvAppeal;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_public_service)
    TextView tvPublicService;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    private long backtime=0;
    private HomePageFragment homePageFragment;
    private AppealFragment appealFragment;
    private ProgressFragment progressFragment;
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
        tvAppeal.setSelected(false);
        tvProgress.setSelected(false);
        tvPublicService.setSelected(false);
        tvMine.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction) {
        if (homePageFragment != null) {
            transaction.hide(homePageFragment);
        }
        if (appealFragment != null) {
            transaction.hide(appealFragment);
        }
        if (progressFragment != null) {
            transaction.hide(progressFragment);
        }
        if (publicServiceFragment != null) {
            transaction.hide(publicServiceFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    @OnClick({R.id.tv_homepae, R.id.tv_appeal,R.id.tv_progress,R.id.tv_public_service, R.id.tv_mine})
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
            case R.id.tv_appeal:
                selected();
                tvAppeal.setSelected(true);
                if (appealFragment == null) {
                    appealFragment = new AppealFragment();
                    transaction.add(R.id.fragment_container, appealFragment);
                } else {
                    transaction.show(appealFragment);
                }
                break;
            case R.id.tv_progress:
                selected();
                tvProgress.setSelected(true);
                if (progressFragment == null) {
                    progressFragment = new ProgressFragment();
                    transaction.add(R.id.fragment_container, progressFragment);
                } else {
                    transaction.show(progressFragment);
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
}
