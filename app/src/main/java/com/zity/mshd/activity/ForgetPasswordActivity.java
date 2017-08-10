package com.zity.mshd.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.KeyboardUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.zity.mshd.R;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseActivity;
import com.zity.mshd.bean.Success;
import com.zity.mshd.bean.Success1;
import com.zity.mshd.http.GsonRequest;
import com.zity.mshd.http.UrlPath;
import com.zity.mshd.widegt.BanCNandEmpty;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by luochao on 2017/7/20.
 * 忘记密码
 */

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_security_code)
    EditText etSecurityCode;
    @BindView(R.id.btn_security_code)
    Button btnSecurityCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btnSecurityCode.setEnabled(false);
            btnSecurityCode.setText((millisUntilFinished / 1000) + "秒后重发");
        }

        @Override
        public void onFinish() {
            btnSecurityCode.setEnabled(true);
            btnSecurityCode.setText("获取验证码");
        }
    };
    private String phone;

    @Override
    protected int getLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initData() {
        tvTooltarTitle.setText("忘记密码");
        //禁止输入中文跟空格
        BanCNandEmpty.newInstance().banCNandEmpty(etPassword);
        //禁止输入中文跟空格
        BanCNandEmpty.newInstance().banCNandEmpty(etConfirmPassword);
    }


    @OnClick({R.id.iv_toolbar_back, R.id.btn_security_code, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.btn_security_code:
                KeyboardUtils.hideSoftInput(ForgetPasswordActivity.this);
                String phone =etPhone.getText().toString().trim();
                if (EmptyUtils.isEmpty(phone)){
                    ToastUtils.showShortToast("请输入您的手机号码");
                    etPhone.requestFocus();
                    return;
                }
                if (!RegexUtils.isMobileExact(phone)){
                    ToastUtils.showShortToast("请输入正确的手机号码");
                    etPhone.requestFocus();
                    return;
                }
                timer.start();
                getSecurityCode(phone);
                break;
            case R.id.btn_submit:
                KeyboardUtils.hideSoftInput(ForgetPasswordActivity.this);
                String password =etPassword.getText().toString().trim();
                String confimPassword=etConfirmPassword.getText().toString().trim();
                String code =etSecurityCode.getText().toString().trim();
                String phone1 =etPhone.getText().toString().trim();
                if (EmptyUtils.isEmpty(phone1)){
                    ToastUtils.showShortToast("请输入您的手机号码");
                    etPhone.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(code)){
                    ToastUtils.showShortToast("请输入验证码");
                    etSecurityCode.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(password)){
                    ToastUtils.showShortToast("请输入密码");
                    etPassword.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(confimPassword)){
                    ToastUtils.showShortToast("请输入确认密码");
                    etConfirmPassword.requestFocus();
                    return;
                }
                if (!StringUtils.equals(password,confimPassword)){
                    ToastUtils.showShortToast("两次输入密码不一致");
                    etPassword.requestFocus();
                    return;
                }
                changePassword(phone1,password,code);
                break;
        }
    }
    //获取验证码
    private void getSecurityCode(String phone) {
        Map<String,String> map =new HashMap<>();
        map.put("phone",phone);
        GsonRequest<Success> request =new GsonRequest<Success>(Request.Method.POST, map, UrlPath.getSecurityCode, Success.class, new Response.Listener<Success>() {
            @Override
            public void onResponse(Success success) {
                if (success.isSuccess()){
                    ToastUtils.showShortToast("正在发送中...");
                }else {
                    ToastUtils.showShortToast("错误!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }
//    修改密码
    private void changePassword(String phone,String password,String code){
        Map<String,String> map =new HashMap<>();
        map.put("phone",phone);
        map.put("password",password);
        map.put("code",code);
        GsonRequest<Success1> request =new GsonRequest<Success1>(Request.Method.POST, map, UrlPath.CHANGE_PASSWORD, Success1.class, new Response.Listener<Success1>() {
            @Override
            public void onResponse(Success1 success) {
                if (success.isSuccess()){
                    ToastUtils.showShortToast(success.getMessage());
                    onBackPressedSupport();
                }else {
                    ToastUtils.showShortToast(success.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
