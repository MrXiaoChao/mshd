package com.zity.mshd.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPickerView;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.KeyboardUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.zity.mshd.R;
import com.zity.mshd.activity.LoginRegisterActivity;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseFragment;
import com.zity.mshd.bean.Register;
import com.zity.mshd.bean.Success;
import com.zity.mshd.http.GsonRequest;
import com.zity.mshd.http.UrlPath;
import com.zity.mshd.widegt.BanCNandEmpty;
import com.zity.mshd.widegt.MyDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.AbstractPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 注册
 * Created by luochao on 2017/7/18.
 */

public class RegisterFragment extends BaseFragment {
    @BindView(R.id.ll_personal)
    LinearLayout llPersonal;
    @BindView(R.id.et_name)
    EditText etName;
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
    @BindView(R.id.tv_toast)
    TextView tvToast;
    @BindView(R.id.tv_account_type)
    TextView tvAccountType;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private Animation shake;
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initData() {
        shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        //禁止输入中文跟空格
        BanCNandEmpty.newInstance().banCNandEmpty(etPassword);
        //禁止输入中文跟空格
        BanCNandEmpty.newInstance().banCNandEmpty(etConfirmPassword);
    }


    @OnClick({R.id.ll_personal, R.id.btn_security_code, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_personal:
                final List<String> list = new ArrayList<>();
                list.add("个人");
                list.add("企业");
                OptionsPickerView pickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvAccountType.setText(list.get(options1));
                    }
                }).setTitleText("账号类型").build();
                pickerView.setPicker(list);
                pickerView.show();
                break;
            case R.id.btn_security_code:
                //隐藏软键盘
                KeyboardUtils.hideSoftInput(getActivity());
                String name =etName.getText().toString().trim();
                String phone=etPhone.getText().toString().trim();
                if (EmptyUtils.isEmpty(name)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("请输入姓名！");
                    tvToast.startAnimation(shake);
                    etName.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(phone)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("请手机号码！");
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
                timer.start();
                getSecurityCode(phone);
                break;
            case R.id.btn_register:
                showExitDialog("注册成功");
                KeyboardUtils.hideSoftInput(getActivity());
                String name1 =etName.getText().toString().trim();
                String phone1=etPhone.getText().toString().trim();
                String securityCode=etSecurityCode.getText().toString().trim();
                String password=etPassword.getText().toString().trim();
                String confirmPassword=etConfirmPassword.getText().toString().trim();
                String accountType=tvAccountType.getText().toString().trim();
                if (StringUtils.equals("个人",accountType)){
                    accountType="1";
                }else {
                    accountType="2";
                }

                if (EmptyUtils.isEmpty(name1)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("请输入姓名！");
                    tvToast.startAnimation(shake);
                    etName.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(phone1)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("请手机号码！");
                    tvToast.startAnimation(shake);
                    etPhone.requestFocus();
                    return;
                }
                if (!RegexUtils.isMobileExact(phone1)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("请输入正确的手机号码！");
                    tvToast.startAnimation(shake);
                    etPhone.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(securityCode)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("请输入验证码！");
                    tvToast.startAnimation(shake);
                    etSecurityCode.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(password)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("请输入密码！");
                    tvToast.startAnimation(shake);
                    etPassword.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(confirmPassword)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("请再次输入密码！");
                    tvToast.startAnimation(shake);
                    etConfirmPassword.requestFocus();
                    return;
                }
                if (!StringUtils.equals(password,confirmPassword)){
                    tvToast.setVisibility(View.VISIBLE);
                    tvToast.setText("两次输入密码不一致！");
                    tvToast.startAnimation(shake);
                    etPassword.requestFocus();
                    return;
                }
                sendRegister(phone1,password,name1,securityCode,accountType);
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
    //注册
    private void sendRegister(String phone,String password,String name,String code,String type){
        Map<String,String> map =new HashMap<>();
        map.put("phone",phone);
        map.put("password",password);
        map.put("name",name);
        map.put("code",code);
        map.put("type",type);
        GsonRequest<Register> request =new GsonRequest<Register>(Request.Method.POST, map, UrlPath.SendRegister, Register.class, new Response.Listener<Register>() {
            @Override
            public void onResponse(Register register) {
                if (register.isSuccess()){
                    showExitDialog("注册成功");
                }else {
                    ToastUtils.showShortToast(register.getMessage());
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
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
    }
    private void showExitDialog(String msg) {
        final MyDialog dialog = new MyDialog(getActivity());
        dialog.setTitle("提示");
        dialog.setMessage(msg);
        dialog.setYesOnclickListener("去登录", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                LoginRegisterActivity activity = (LoginRegisterActivity) getActivity();
                activity.defaultSelected();
                dialog.dismiss();
            }
        });
        dialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
