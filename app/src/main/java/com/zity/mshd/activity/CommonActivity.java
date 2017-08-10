package com.zity.mshd.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.KeyboardUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zity.mshd.R;
import com.zity.mshd.adapter.GuideAdapter;
import com.zity.mshd.adapter.NoticeAdapter;
import com.zity.mshd.adapter.PolicyAdapter;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseActivity;
import com.zity.mshd.bean.Guide;
import com.zity.mshd.bean.Notice;
import com.zity.mshd.bean.Policy;
import com.zity.mshd.http.GsonRequest;
import com.zity.mshd.http.UrlPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by luochao on 2017/8/10.
 * 办事指南 政策法规 通知公告
 */

public class CommonActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, TextView.OnEditorActionListener {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.rv_progress)
    RecyclerView rvProgress;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.et_search)
    EditText et_search;
    private ProgressDialog progressDialog;
    private String title;
    private List<Guide.ListBean> listGuide =new ArrayList<>();
    private List<Policy.ListBean> listPolicy =new ArrayList<>();
    private List<Notice.ListBean> listNotice =new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_common;
    }

    @Override
    protected void initData() {
        KeyboardUtils.hideSoftInput(this);
        setProgress();
        title = getIntent().getStringExtra("title");
        tvTooltarTitle.setText(title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProgress.setLayoutManager(layoutManager);

        if (StringUtils.equals("办事指南", title)) {
            getGuideFromService();
        }else if (StringUtils.equals("政策法规", title)){
            getPolicyFromService();
        }else if (StringUtils.equals("通知公告", title)){
            getNoticeFromService();
        }
        swipeLayout.setOnRefreshListener(this);

        rvProgress.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String url1 = "http://211.151.183.170:9081/views/app/appGuide_detail.jsp?id=";
                String url2 = "http://211.151.183.170:9081/views/app/appPolicy_detail.jsp?id=";
                String url3 = "http://211.151.183.170:9081/views/app/appAnnouncement_detail.jsp?id=";
                Intent intent =new Intent(CommonActivity.this,WebViewActivity.class);
                if (StringUtils.equals("办事指南", title)) {
                    intent.putExtra("URL",url1+listGuide.get(position).getGuideId());
                    intent.putExtra("title",listGuide.get(position).getTitle());
                }else if (StringUtils.equals("政策法规", title)){
                    intent.putExtra("URL",url2+listPolicy.get(position).getPolicyId());
                    intent.putExtra("title",listPolicy.get(position).getTitle());
                }else if (StringUtils.equals("通知公告", title)){
                    intent.putExtra("URL",url3+listNotice.get(position).getAnnId());
                    intent.putExtra("title",listNotice.get(position).getTitle());
                }
                startActivity(intent);
            }
        });

        et_search.setOnEditorActionListener(this);
    }


    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }

    //获取办事指南数据
    private void getGuideFromService() {
        GsonRequest<Guide> request = new GsonRequest<Guide>(UrlPath.GUIDE, Guide.class, new Response.Listener<Guide>() {
            @Override
            public void onResponse(Guide response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    listGuide=response.getList();
                    rvProgress.setAdapter(new GuideAdapter(R.layout.item_common, response.getList()));
                    cancelProgress();
                    swipeLayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    //获取消息通知数据
    private void getNoticeFromService() {
        GsonRequest<Notice> request = new GsonRequest<Notice>(UrlPath.NOTICE, Notice.class, new Response.Listener<Notice>() {
            @Override
            public void onResponse(Notice response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    listNotice=response.getList();
                    rvProgress.setAdapter(new NoticeAdapter(R.layout.item_common, response.getList()));
                    cancelProgress();
                    swipeLayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    //获取政策法规数据
    private void getPolicyFromService() {
        GsonRequest<Policy> request = new GsonRequest<Policy>(UrlPath.POLICY, Policy.class, new Response.Listener<Policy>() {
            @Override
            public void onResponse(Policy response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    listPolicy=response.getList();
                    rvProgress.setAdapter(new PolicyAdapter(R.layout.item_common, response.getList()));
                    cancelProgress();
                    swipeLayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    private void setProgress() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("正在加载中...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    private void cancelProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onRefresh() {
        if (StringUtils.equals("办事指南", title)) {
            getGuideFromService();
        }else if (StringUtils.equals("政策法规",title)){
            getPolicyFromService();
        }else if (StringUtils.equals("通知公告",title)){
            getNoticeFromService();
        }
    }

    //搜索事件
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) et_search.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    CommonActivity.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

            //获取要搜索的值
            String searchtitle= et_search.getText().toString().trim();
            if (StringUtils.equals("办事指南", title)) {
                getGuideFromService1(searchtitle);
            }else if (StringUtils.equals("政策法规",title)){
                getPolicyFromService1(searchtitle);
            }else if (StringUtils.equals("通知公告",title)){
                getNoticeFromService1(searchtitle);
            }
            return true;
        }
        return false;
    }


    //搜索

    //获取办事指南数据
    private void getGuideFromService1(String title) {
        Map<String,String> map =new HashMap<>();
        map.put("title",title);
        GsonRequest<Guide> request = new GsonRequest<Guide>(Request.Method.POST,map,UrlPath.GUIDE, Guide.class, new Response.Listener<Guide>() {
            @Override
            public void onResponse(Guide response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    listGuide=response.getList();
                    rvProgress.setAdapter(new GuideAdapter(R.layout.item_common, response.getList()));
                    cancelProgress();
                    swipeLayout.setRefreshing(false);
                }else {
                    ToastUtils.showShortToast("没有该数据");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    //获取消息通知数据
    private void getNoticeFromService1(String title) {
        Map<String,String> map =new HashMap<>();
        map.put("title",title);
        GsonRequest<Notice> request = new GsonRequest<Notice>(Request.Method.POST,map,UrlPath.NOTICE, Notice.class, new Response.Listener<Notice>() {
            @Override
            public void onResponse(Notice response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    listNotice=response.getList();
                    rvProgress.setAdapter(new NoticeAdapter(R.layout.item_common, response.getList()));
                    cancelProgress();
                    swipeLayout.setRefreshing(false);
                }else {
                    ToastUtils.showShortToast("没有该数据");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

    //获取政策法规数据
    private void getPolicyFromService1(String title) {
        Map<String,String> map =new HashMap<>();
        map.put("title",title);
        GsonRequest<Policy> request = new GsonRequest<Policy>(Request.Method.POST,map,UrlPath.POLICY, Policy.class, new Response.Listener<Policy>() {
            @Override
            public void onResponse(Policy response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    listPolicy=response.getList();
                    rvProgress.setAdapter(new PolicyAdapter(R.layout.item_common, response.getList()));
                    cancelProgress();
                    swipeLayout.setRefreshing(false);
                }else {
                    ToastUtils.showShortToast("没有该数据");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getInstance().getHttpQueue().add(request);
    }

}
