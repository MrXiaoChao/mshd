package com.zity.mshd.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zity.mshd.R;
import com.zity.mshd.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 诉求
 * Created by luochao on 2017/7/18.
 */

public class AppealFragment extends BaseFragment {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.tv_appeal_title)
    TextView tvAppealTitle;
    @BindView(R.id.et_appeal_title)
    EditText etAppealTitle;
    @BindView(R.id.tv_appeal_content)
    TextView tvAppealContent;
    @BindView(R.id.et_appeal_content)
    EditText etAppealContent;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_appeal_type)
    TextView tvAppealType;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_appeal_status)
    TextView tvAppealStatus;
    @BindView(R.id.tv_appeal_name)
    TextView tvAppealName;
    @BindView(R.id.et_appeal_name)
    EditText etAppealName;
    @BindView(R.id.tv_appeal_phone)
    TextView tvAppealPhone;
    @BindView(R.id.et_appeal_phone)
    EditText etAppealPhone;
    @BindView(R.id.rt_open)
    RadioButton rtOpen;
    @BindView(R.id.rt_inopen)
    RadioButton rtInopen;
    @BindView(R.id.cb_agree)
    CheckBox cbAgree;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_point_out)
    TextView tvPointOut;
    @BindView(R.id.rl_appeal_type)
    RelativeLayout rlAppealType;//诉求类别
    @BindView(R.id.rl_appeal_status)
    RelativeLayout rlAppealStatus;//诉求类型

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_appeal;
    }

    @Override
    protected void initData() {
        ivToolbarBack.setVisibility(View.GONE);
        tvTooltarTitle.setText("填写诉求");
//        一个textviwew设置不同的颜色
        SpannableStringBuilder style = new SpannableStringBuilder("温馨提示：为了使您的诉求能够得到解决请填写真实电话，并通过此电话登录官网可以查询办理进度和答复内容。您的身份信息我们将会保密，不会对外公开，谢谢您的信任。");
        style.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPointOut.setText(style);
    }


    @OnClick({R.id.rl_appeal_type, R.id.rl_appeal_status, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_appeal_type:
                OptionsPickerView pickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvAppealType.setText(getResources().getStringArray(R.array.appealType)[options1]);
                    }
                }).setTitleText("诉求类别").build();
                pickerView.setPicker(Arrays.asList(getResources().getStringArray(R.array.appealType)));
                pickerView.show();
                break;
            case R.id.rl_appeal_status:

                OptionsPickerView pickerView1 = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvAppealStatus.setText(getResources().getStringArray(R.array.appealStatus)[options1]);
                    }
                }).setTitleText("诉求类型").build();
                pickerView1.setPicker(Arrays.asList(getResources().getStringArray(R.array.appealStatus)));
                pickerView1.show();
                break;
            case R.id.btn_submit:
                break;
        }
    }
}
