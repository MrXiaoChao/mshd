package com.zity.mshd.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.zity.mshd.R;
import com.zity.mshd.base.BaseActivity;
import com.zity.mshd.fragment.HomePageFragment;
import com.zity.mshd.fragment.LoginFragment;
import com.zity.mshd.fragment.RegisterFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录  注册
 * Created by luochao on 2017/7/18.
 */

public class LoginRegisterActivity extends BaseActivity {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_login_register;
    }

    @Override
    protected void initData() {
        defaultSelected();
    }


    @OnClick({R.id.tv_login, R.id.tv_register})
    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (view.getId()) {
            case R.id.tv_login:
                selected();
                tvLogin.setSelected(true);
                if (loginFragment == null) {
                    loginFragment = new LoginFragment();
                    transaction.add(R.id.fragment_container, loginFragment);
                } else {
                    transaction.show(loginFragment);
                }
                break;
            case R.id.tv_register:
                selected();
                tvRegister.setSelected(true);
                if (registerFragment == null) {
                    registerFragment = new RegisterFragment();
                    transaction.add(R.id.fragment_container, registerFragment);
                } else {
                    transaction.show(registerFragment);
                }
                break;
        }
        transaction.commit();
    }
    //默认选中
    private void defaultSelected(){
        selected();
        tvLogin.setSelected(true);
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
            transaction.add(R.id.fragment_container, loginFragment);
        } else {
            transaction.show(loginFragment);
        }
        transaction.commit();
    }
    //重置所有文本的选中状态
    public void selected() {
        tvLogin.setSelected(false);
        tvRegister.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction) {
        if (loginFragment != null) {
            transaction.hide(loginFragment);
        }
        if (registerFragment != null) {
            transaction.hide(registerFragment);
        }
    }
}
