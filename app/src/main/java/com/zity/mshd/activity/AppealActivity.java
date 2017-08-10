package com.zity.mshd.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPickerView;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.KeyboardUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.zity.mshd.R;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseActivity;
import com.zity.mshd.bean.Success1;
import com.zity.mshd.http.GsonRequest;
import com.zity.mshd.http.UrlPath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 诉求
 * Created by luochao on 2017/7/18.
 */

public class AppealActivity extends BaseActivity implements View.OnTouchListener {
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
    @BindView(R.id.rg_group)
    RadioGroup radioGroup;
    @BindView(R.id.tv_appeal_xuzhi)
    TextView tv_appeal_xuzhi;
    private String open;
    private String type1;
    private String status1;
    private String userid;
    private String bigtitle;


    @Override
    protected int getLayout() {
        return R.layout.activity_appeal;
    }


    @Override
    protected void initData() {
        bigtitle = getIntent().getStringExtra("title");
        SPUtils sp = new SPUtils("user");
        userid = sp.getString("userid");
        tvTooltarTitle.setText(bigtitle);
//        一个textviwew设置不同的颜色
        SpannableStringBuilder style = new SpannableStringBuilder("温馨提示：为了使您的诉求能够得到解决请填写真实电话，并通过此电话登录官网可以查询办理进度和答复内容。您的身份信息我们将会保密，不会对外公开，谢谢您的信任。");
        style.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPointOut.setText(style);

        //第一种获得单选按钮值的方法
        //为radioGroup设置一个监听器:setOnCheckedChanged()
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = (RadioButton) findViewById(checkedId);
                String openbutton = radbtn.getText().toString();
                if (StringUtils.equals(openbutton,"允许公开")){
                    open="1";
                }else {
                    open="2";
                }
            }
        });

        etAppealContent.setOnTouchListener(this);
    }


    @OnClick({R.id.rl_appeal_type, R.id.rl_appeal_status, R.id.btn_submit,R.id.tv_appeal_xuzhi,R.id.iv_toolbar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            //因为改动所以把这个功能给隐藏了`这里就先不删除
            case R.id.rl_appeal_type:
                OptionsPickerView pickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvAppealType.setText(getResources().getStringArray(R.array.appealType)[options1]);
                    }
                }).setTitleText("诉求类别").build();
                pickerView.setPicker(Arrays.asList(getResources().getStringArray(R.array.appealType)));
                pickerView.show();
                break;
            case R.id.rl_appeal_status:
                OptionsPickerView pickerView1 = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tvAppealStatus.setText(getResources().getStringArray(R.array.appealStatus)[options1]);
                    }
                }).setTitleText("诉求类型").build();
                pickerView1.setPicker(Arrays.asList(getResources().getStringArray(R.array.appealStatus)));
                pickerView1.show();
                break;
            case R.id.btn_submit:
                String title= etAppealTitle.getText().toString().trim();
                String content=etAppealContent.getText().toString().trim();
                String name =etAppealName.getText().toString().trim();
                String phone=etAppealPhone.getText().toString().trim();
//                String type =tvAppealType.getText().toString().trim();
                String status=tvAppealStatus.getText().toString().trim();
                changType(bigtitle);
                changStatus(status);

                if (EmptyUtils.isEmpty(title)){
                    ToastUtils.showShortToast("诉求主题不能为空");
                    return;
                }
                if (EmptyUtils.isEmpty(content)){
                    ToastUtils.showShortToast("诉求内容不能为空");
                    return;
                }

                if (EmptyUtils.isEmpty(status)){
                    ToastUtils.showShortToast("诉求类型不能为空");
                    return;
                }
                if (EmptyUtils.isEmpty(name)){
                    ToastUtils.showShortToast("姓名不能为空");
                    return;
                }
                if (EmptyUtils.isEmpty(phone)){
                    ToastUtils.showShortToast("手机号码不能为空");
                    return;
                }
                if (!RegexUtils.isMobileExact(phone)){
                    ToastUtils.showShortToast("请填写正确的手机号码");
                    etAppealPhone.requestFocus();
                    return;
                }
                if (EmptyUtils.isEmpty(open)){
                    ToastUtils.showShortToast("请勾选内容公开权限");
                    return;
                }
                if (!cbAgree.isChecked()){
                    ToastUtils.showShortToast("请阅读并勾选诉求须知");
                    return;
                }
                sendRequestToService(title,content,type1,status1,name,phone,open,userid);
                break;
            case  R.id.tv_appeal_xuzhi:
                Intent intent1=new Intent(mContext, WebViewActivity.class);
                intent1.putExtra("title","诉求须知");
                intent1.putExtra("URL","http://211.151.183.170:9081/views/app/appNotice.jsp");
                startActivity(intent1);
                break;
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
        }
    }

    private void changStatus(String status) {
        if (StringUtils.equals(status,"水电煤气")){
        status1="1";
        }else if (StringUtils.equals(status,"公安消防")){
            status1="2";
        }else if (StringUtils.equals(status,"教育教学")){
            status1="3";
        }else if (StringUtils.equals(status,"公交问题")){
            status1="4";
        }else if (StringUtils.equals(status,"停车问题")){
            status1="5";
        }else if (StringUtils.equals(status,"供热问题")){
            status1="6";
        }else if (StringUtils.equals(status,"行政执法")){
            status1="7";
        }else if (StringUtils.equals(status,"效能建设")){
            status1="8";
        }else if (StringUtils.equals(status,"城市建设")){
            status1="9";
        }else if (StringUtils.equals(status,"劳保社保")){
            status1="10";
        }else if (StringUtils.equals(status,"通讯传媒")){
            status1="11";
        }else if (StringUtils.equals(status,"物业问题")){
            status1="12";
        }else if (StringUtils.equals(status,"发展改革")){
            status1="13";
        }else if (StringUtils.equals(status,"产权保障")){
            status1="14";
        }else if (StringUtils.equals(status,"环境保护")){
            status1="15";
        }else if (StringUtils.equals(status,"交通驾管")){
            status1="16";
        }else if (StringUtils.equals(status,"工商税务")){
            status1="17";
        }else if (StringUtils.equals(status,"民政问题")){
            status1="18";
        }else {
            status1="19";
        }
    }

    private void changType(String type) {
        if (StringUtils.equals(type,"咨询")){
            type1="1";
        }else if (StringUtils.equals(type,"求助")){
            type1="2";
        }else if (StringUtils.equals(type,"意见")){
            type1="3";
        }else if (StringUtils.equals(type,"建议")){
            type1="4";
        }else if (StringUtils.equals(type,"投诉")){
            type1="5";
        }else if (StringUtils.equals(type,"举报")){
            type1="6";
        }
    }

    //发送请求给服务器
    private void sendRequestToService(String title, String content, String type, String status, String name, String phone, String open,String userid) {
        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);
        map.put("type", type);
        map.put("status", status);
        map.put("name", name);
        map.put("phone", phone);
        map.put("open", open);
        map.put("userId",userid);
        GsonRequest<Success1> request = new GsonRequest<Success1>(Request.Method.POST, map, UrlPath.LEFT_APPEAL, Success1.class, new Response.Listener<Success1>() {
            @Override
            public void onResponse(Success1 response) {
                if (response.isSuccess()){
                    ToastUtils.showShortToast(response.getMessage());
                    etAppealTitle.setText("");
                    etAppealContent.setText("");
                    tvAppealType.setText("");
                    tvAppealStatus.setText("");
                    etAppealName.setText("");
                    etAppealPhone.setText("");
                    cbAgree.setChecked(false);
                    ToastUtils.showShortToast(response.getMessage());
                    finish();
                }else {
                    ToastUtils.showShortToast(response.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }
    //解决scrollview 与 editext 获取焦点
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if ((v.getId() == R.id.et_appeal_content && canVerticalScroll(etAppealContent))) {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }
}
