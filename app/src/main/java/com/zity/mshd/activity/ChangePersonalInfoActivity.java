package com.zity.mshd.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.gson.Gson;
import com.zity.mshd.R;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseActivity;
import com.zity.mshd.bean.JsonBean;
import com.zity.mshd.bean.Success1;
import com.zity.mshd.http.GsonRequest;
import com.zity.mshd.http.UrlPath;
import com.zity.mshd.widegt.GetJsonDataUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by luochao on 2017/7/20.
 * 修改个人信息
 */

public class ChangePersonalInfoActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.tv_right_save)
    TextView tvRightSave;
    @BindView(R.id.et_useinfo)
    EditText etUseinfo;
    @BindView(R.id.ll_personal_info)
    LinearLayout llPersonalInfo;
    @BindView(R.id.et_confirmpas)
    TextView etConfirmpas;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    private String nameorphone;
    private String address;
    private String userid;
    private String flag;
    private String nameorphone1;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread==null){//如果已创建就不再重新创建子线程了
//                        Toast.makeText(ChangePersonalInfoActivity.this,"开始解析数据",Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
//                    Toast.makeText(ChangePersonalInfoActivity.this,"解析数据成功",Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
//                    Toast.makeText(ChangePersonalInfoActivity.this,"解析数据失败",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_changepersonal_info;
    }

    @Override
    protected void initData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        SPUtils sp = new SPUtils("user");
        userid = sp.getString("userid");
        //1.修改姓名 2修改账号 3修改地址
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        String phone = intent.getStringExtra("phone");
        String address = intent.getStringExtra("address");
        String name = intent.getStringExtra("name");
        String gender = intent.getStringExtra("gender");
        if (StringUtils.equals("1", flag)) {
            llPersonalInfo.setVisibility(View.VISIBLE);
            tvTooltarTitle.setText("姓名");
            etUseinfo.setText(name);
        } else if (StringUtils.equals("2", flag)) {
            llPersonalInfo.setVisibility(View.VISIBLE);
            tvTooltarTitle.setText("账号");
            etUseinfo.setText(phone);
        } else if (StringUtils.equals("3", flag)) {
            llPersonalInfo.setVisibility(View.VISIBLE);
            tvTooltarTitle.setText("性别");
            etUseinfo.setText(gender);
        } else {
            rlAddress.setVisibility(View.VISIBLE);
            tvTooltarTitle.setText("地址");
            tvAddress.setText(address);
        }
    }


    @OnClick({R.id.iv_toolbar_back, R.id.tv_right_save, R.id.ll_personal_info, R.id.rl_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.tv_right_save:
                KeyboardUtils.hideSoftInput(ChangePersonalInfoActivity.this);
                nameorphone = etUseinfo.getText().toString().trim();
                address = tvAddress.getText().toString().trim();
                if (StringUtils.equals("1", flag)) {
                    if (EmptyUtils.isEmpty(nameorphone)) {
                        ToastUtils.showShortToast("请输入姓名");
                        etUseinfo.requestFocus();
                        return;
                    }
                    changeName(userid, nameorphone);
                } else if (StringUtils.equals("2", flag)) {
                    if (EmptyUtils.isEmpty(nameorphone)) {
                        ToastUtils.showShortToast("请输入手机号码");
                        etUseinfo.requestFocus();
                        return;
                    }
                    if (!RegexUtils.isMobileExact(nameorphone)){
                        ToastUtils.showShortToast("请输入正确的手机号码");
                        etUseinfo.requestFocus();
                        return;
                    }
                    changePhone(userid, nameorphone);
                } else if (StringUtils.equals("3", flag)) {
                    if (EmptyUtils.isEmpty(nameorphone)) {
                        ToastUtils.showShortToast("请输入姓别");
                        etUseinfo.requestFocus();
                        return;
                    }
                    if (StringUtils.equals("男", nameorphone)) {
                        nameorphone1 = "1";
                    } else {
                        nameorphone1 = "2";
                    }
                    changeGender(userid, nameorphone1);
                } else {
                    if (EmptyUtils.isEmpty(address)) {
                        ToastUtils.showShortToast("请输入地址");
                        return;
                    }
                    changeAddress(userid,address);
                }
                break;
            case R.id.ll_personal_info:
                break;
            case R.id.rl_address:
                if (isLoaded){
                    ShowPickerView();
                }else {
                    Toast.makeText(ChangePersonalInfoActivity.this,"数据暂未解析成功，请等待",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //修改姓名
    private void changeName(String id, String name) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        GsonRequest<Success1> request = new GsonRequest<Success1>(Request.Method.POST, map, UrlPath.CHANGE_USERINFO, Success1.class, new Response.Listener<Success1>() {
            @Override
            public void onResponse(Success1 success) {
                if (success.isSuccess()) {
                    ToastUtils.showShortToast(success.getMessage());
                    Intent intent = new Intent();
                    intent.putExtra("name", nameorphone);
                    setResult(10001, intent);
                    onBackPressedSupport();
                } else {
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

    //修改账号也就是手机号码
    private void changePhone(String id, String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("phone", phone);
        GsonRequest<Success1> request = new GsonRequest<Success1>(Request.Method.POST, map, UrlPath.CHANGE_USERINFO, Success1.class, new Response.Listener<Success1>() {
            @Override
            public void onResponse(Success1 success) {
                if (success.isSuccess()) {
                    ToastUtils.showShortToast(success.getMessage());
                    Intent intent = new Intent();
                    intent.putExtra("phone", nameorphone);
                    setResult(10002, intent);
                    onBackPressedSupport();
                } else {
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

    //修改性别
    private void changeGender(String id, String gender) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("gender", gender);
        GsonRequest<Success1> request = new GsonRequest<Success1>(Request.Method.POST, map, UrlPath.CHANGE_USERINFO, Success1.class, new Response.Listener<Success1>() {
            @Override
            public void onResponse(Success1 success) {
                if (success.isSuccess()) {
                    ToastUtils.showShortToast(success.getMessage());
                    Intent intent = new Intent();
                    intent.putExtra("gender", nameorphone);
                    setResult(10003, intent);
                    onBackPressedSupport();
                } else {
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

    //修改地址
    private void changeAddress(String id, String address1) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("address", address1);
        GsonRequest<Success1> request = new GsonRequest<Success1>(Request.Method.POST, map, UrlPath.CHANGE_USERINFO, Success1.class, new Response.Listener<Success1>() {
            @Override
            public void onResponse(Success1 success) {
                if (success.isSuccess()) {
                    ToastUtils.showShortToast(success.getMessage());
                    Intent intent = new Intent();
                    intent.putExtra("address", address);
                    setResult(10004, intent);
                    onBackPressedSupport();
                } else {
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

    //城市选择
    private void ShowPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);

                tvAddress.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
}

