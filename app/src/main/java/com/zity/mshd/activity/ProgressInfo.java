package com.zity.mshd.activity;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zity.mshd.R;
import com.zity.mshd.adapter.ProgressXqAdapter;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseActivity;
import com.zity.mshd.bean.ProgressXQ;
import com.zity.mshd.http.GsonRequest;
import com.zity.mshd.http.UrlPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by luochao on 2017/8/1.
 * 进度详情
 */

public class ProgressInfo extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_progress)
    RecyclerView rvProgress;
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_gongkai)
    TextView tvGongkai;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.appeal_leibie)
    TextView appealLeibie;
    @BindView(R.id.tv_appeal_lexing)
    TextView tvAppealLexing;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private List<ProgressXQ.ZtBean> list = new ArrayList<>();
    private ProgressXqAdapter adapter;
    private ProgressDialog progressDialog;
    private String id;

    //自动刷新

    @Override
    protected int getLayout() {
        return R.layout.activity_progress_info;
    }

    @Override
    protected void initData() {
        tvTooltarTitle.setText("进度详情");
        id = getIntent().getStringExtra("id");
        //解决recyclerview与scrollview滑动冲突的一小部分
        rvProgress.setNestedScrollingEnabled(false);
        swipeLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //自动刷新
        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(true);
            }
        });
        onRefresh();
    }

    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }

    //从服务器获取数据
    private void getDataFromService(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        GsonRequest<ProgressXQ> request = new GsonRequest<ProgressXQ>(Request.Method.POST, map, UrlPath.PROGRESS_XQ, ProgressXQ.class, new Response.Listener<ProgressXQ>() {
            @Override
            public void onResponse(ProgressXQ response) {
                if (response != null) {
                    tvName.setText(response.getName());
                    tvTitle.setText(response.getTitle());
                    tvTime.setText(response.getCreatetime());
                    tvPhone.setText(response.getPhone());
                    tvContent.setText(response.getContent());
                    if (response.getOpen() == 1) {
                        tvGongkai.setText("公开");
                    } else {
                        tvGongkai.setText("不公开");
                    }
                    switch (response.getType()) {
                        case 1:
                            appealLeibie.setText("咨询");
                            break;
                        case 2:
                            appealLeibie.setText("求助");
                            break;
                        case 3:
                            appealLeibie.setText("意见");
                            break;
                        case 4:
                            appealLeibie.setText("建议");
                            break;
                        case 5:
                            appealLeibie.setText("投诉");
                            break;
                        case 6:
                            appealLeibie.setText("举报");
                            break;
                    }

                    switch (response.getStatus()) {
                        case 1:
                            tvAppealLexing.setText("水电煤气");
                            break;
                        case 2:
                            tvAppealLexing.setText("公安消防");
                            break;
                        case 3:
                            tvAppealLexing.setText("教育教学");
                            break;
                        case 4:
                            tvAppealLexing.setText("公交问题");
                            break;
                        case 5:
                            tvAppealLexing.setText("停车问题");
                            break;
                        case 6:
                            tvAppealLexing.setText("供热问题");
                            break;
                        case 7:
                            tvAppealLexing.setText("行政执法");
                            break;
                        case 8:
                            tvAppealLexing.setText("效能建设");
                            break;
                        case 9:
                            tvAppealLexing.setText("城市建设");
                            break;
                        case 10:
                            tvAppealLexing.setText("劳保社保");
                            break;
                        case 11:
                            tvAppealLexing.setText("通讯传媒");
                            break;
                        case 12:
                            tvAppealLexing.setText("物业问题");
                            break;
                        case 13:
                            tvAppealLexing.setText("发展改革");
                            break;
                        case 14:
                            tvAppealLexing.setText("产权保障");
                            break;
                        case 15:
                            tvAppealLexing.setText("环境保护");
                            break;
                        case 16:
                            tvAppealLexing.setText("交通驾管");
                            break;
                        case 17:
                            tvAppealLexing.setText("工商税务");
                            break;
                        case 18:
                            tvAppealLexing.setText("民政问题");
                            break;
                        default:
                            tvAppealLexing.setText("其他");
                            break;
                    }

                    swipeLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeLayout.setRefreshing(false);
                        }
                    });
                }
                if (response != null && response.getZt() != null && response.getZt().size() > 0) {
                    adapter = new ProgressXqAdapter(ProgressInfo.this, response.getZt(), response);
                    rvProgress.setLayoutManager(new LinearLayoutManager(mContext));
                    rvProgress.setAdapter(adapter);
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
        getDataFromService(id);
    }
}
