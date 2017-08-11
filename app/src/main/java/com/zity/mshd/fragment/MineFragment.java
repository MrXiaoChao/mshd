package com.zity.mshd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.zity.mshd.R;
import com.zity.mshd.activity.ChangePasswordActivity;
import com.zity.mshd.activity.ChangePersonalInfoActivity;
import com.zity.mshd.activity.LoginRegisterActivity;
import com.zity.mshd.activity.WebViewActivity;
import com.zity.mshd.base.BaseFragment;
import com.zity.mshd.widegt.MyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我的
 * Created by luochao on 2017/7/18.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.rl_gender)
    RelativeLayout rlGender;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.rl_account)
    RelativeLayout rlAccount;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.rl_change_password)
    RelativeLayout rlChangePassword;
    @BindView(R.id.rl_about_our)
    RelativeLayout rlAboutOur;
    @BindView(R.id.rl_login)
    RelativeLayout rl_login;
    private String name;
    private String address;
    private String phone;
    private String gender;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {
        tvTooltarTitle.setText("我的账户");
        ivToolbarBack.setVisibility(View.GONE);
        SPUtils sp = new SPUtils("user");
        phone = sp.getString("phone");
        address = sp.getString("address");
        name = sp.getString("name");
        gender = sp.getString("gender");
        tvName.setText(name);
        tvAccount.setText(phone);
        tvAddress.setText(address);
        if (StringUtils.equals("1",gender)){
            gender="男";
        }else {
            gender="女";
        }
        tvGender.setText(gender);
    }


    @OnClick({R.id.rl_name, R.id.rl_gender, R.id.rl_account, R.id.rl_address, R.id.rl_change_password, R.id.rl_about_our,R.id.rl_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_name:
                Intent intent_name =new Intent(getActivity(), ChangePersonalInfoActivity.class);
                intent_name.putExtra("flag","1");
                intent_name.putExtra("name",name);
                startActivityForResult(intent_name,20001);
                break;
            case R.id.rl_gender:
                String gender1=tvGender.getText().toString().trim();
                Intent intent_gender =new Intent(getActivity(), ChangePersonalInfoActivity.class);
                intent_gender.putExtra("flag","3");
                intent_gender.putExtra("gender",gender1);
                startActivityForResult(intent_gender,20003);
                break;
            case R.id.rl_account:
                //账号不能修改
//                Intent intent_account =new Intent(getActivity(), ChangePersonalInfoActivity.class);
//                intent_account.putExtra("flag","2");
//                intent_account.putExtra("phone",phone);
//                startActivityForResult(intent_account,20002);
                break;
            case R.id.rl_address:
                String pastAddress=tvAddress.getText().toString().trim();
                Intent intent_address =new Intent(getActivity(), ChangePersonalInfoActivity.class);
                intent_address.putExtra("flag","4");
                intent_address.putExtra("address",pastAddress);
                startActivityForResult(intent_address,20004);
                break;
            case R.id.rl_change_password:
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_about_our:
                Intent intent1=new Intent(getActivity(), WebViewActivity.class);
                intent1.putExtra("title","关于我们");
                intent1.putExtra("URL","http://211.151.183.170:9081/views/app/appAbout_us.jsp");
                startActivity(intent1);
                break;
            case R.id.rl_login:
                showExitDialog("您确定要退出吗？");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==20001&&resultCode==10001){
            String name = data.getStringExtra("name");
            tvName.setText(name);
        }else if (requestCode==20002&&resultCode==10002){
            String phone = data.getStringExtra("phone");
            tvAccount.setText(phone);
        }else if (requestCode==20003&&resultCode==10003){
            String gender = data.getStringExtra("gender");
            tvGender.setText(gender);
        }else if (requestCode==20004&&resultCode==10004){
            String address = data.getStringExtra("address");
            tvAddress.setText(address);
        }
    }

    private void showExitDialog(String msg) {
        final MyDialog dialog = new MyDialog(getActivity());
        dialog.setTitle("提示");
        dialog.setMessage(msg);
        dialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
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
