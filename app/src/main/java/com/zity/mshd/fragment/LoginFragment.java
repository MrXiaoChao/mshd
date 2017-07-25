package com.zity.mshd.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.KeyboardUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.SPUtils;
import com.zity.mshd.R;
import com.zity.mshd.activity.ForgetPasswordActivity;
import com.zity.mshd.activity.MainActivity;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseFragment;
import com.zity.mshd.bean.User;
import com.zity.mshd.http.GsonRequest;
import com.zity.mshd.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 * Created by luochao on 2017/7/18.
 */

public class LoginFragment extends BaseFragment {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_toast)
    TextView tvToast;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private Animation shake;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initData() {
        shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
    }


    @OnClick({R.id.tv_forget_password, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                Intent intent =new Intent(getActivity(), ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                //动态隐藏软键盘
                KeyboardUtils.hideSoftInput(getActivity());
                String phone =etPhone.getText().toString().trim();
                String password=etPassword.getText().toString().trim();
                if (EmptyUtils.isEmpty(phone)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("请输入账号！");
                    tvToast.startAnimation(shake);
                    etPhone.requestFocus();
                    return;
                }
                if (!RegexUtils.isMobileExact(phone)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("请输入正确的手机号码！");
                    tvToast.startAnimation(shake);
                    etPhone.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(password)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("请输入密码！");
                    tvToast.startAnimation(shake);
                    etPassword.requestFocus();
                    return;
                }
                getDataFromData(phone,password);
                break;
        }
    }

    //登录
    private void getDataFromData(String phone, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        GsonRequest<User> request = new GsonRequest<User>(Request.Method.POST, map, UrlPath.LoginUrl, User.class, new Response.Listener<User>() {
            @Override
            public void onResponse(User user) {
                if (user.isSuccess()) {
                    SPUtils sp =new SPUtils("user");
                    sp.putString("phone",user.getPhone());
                    sp.putString("address",user.getAddress());
                    sp.putString("name",user.getName());
                    sp.putString("gender",user.getGender());
                    sp.putString("userid",user.getUserid());
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("账号或密码错误！");
                    tvToast.startAnimation(shake);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String a =error.getMessage();
            }
        });
        App.getInstance().getHttpQueue().add(request);
    }
}
