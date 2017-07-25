package com.zity.mshd.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zity.mshd.R;
import com.zity.mshd.adapter.ProgressAdapter;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseFragment;
import com.zity.mshd.bean.Progress;
import com.zity.mshd.http.GsonRequest;
import com.zity.mshd.http.UrlPath;
import com.zity.mshd.widegt.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 进度
 * Created by luochao on 2017/7/18.
 */

public class ProgressFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.rv_progress)
    RecyclerView rvProgress;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private boolean mLoadMoreEndGone = false;
    private ProgressAdapter adapter;
    private int mCurrentCounter = 0;
    private static final int TOTAL_COUNTER = 30;
    private static final int PAGE_SIZE = 6;
    private List<Progress.ListBean> progressList = new ArrayList<>();
    private String phone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_progress;
    }

    @Override
    protected void initData() {
        SPUtils sp = new SPUtils("user");
        phone = sp.getString("phone");
        initAdapter();
//         addHeadView();
        getListFromData(phone);
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        tvTooltarTitle.setText("处理进度");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvProgress.setLayoutManager(layoutManager);
        //设置加载更多
        if (adapter != null) {
            adapter.setOnLoadMoreListener(this, rvProgress);
        }

        rvProgress.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShortToast("点击了" + position);
            }
        });
    }

    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }


    @Override
    public void onRefresh() {
        if (progressList != null && progressList.size() > 0) {
            adapter.setNewData(progressList);
        } else {
            getListFromData(phone);
        }
        swipeLayout.setRefreshing(false);
        adapter.setEnableLoadMore(true);
    }

    @Override
    public void onLoadMoreRequested() {

        swipeLayout.setEnabled(false);
        if (mCurrentCounter >= TOTAL_COUNTER) {
            adapter.loadMoreEnd(false);
        } else {
//            adapter.addData(list1);
//            mCurrentCounter = list.size() + list1.size();
//            adapter.loadMoreComplete();
        }
        swipeLayout.setEnabled(true);
    }

//    private void addHeadView() {
//        View headView = getActivity().getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) rvProgress.getParent(), false);
//        ((TextView) headView.findViewById(R.id.tv)).setText("click use custom load view");
//        headView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mLoadMoreEndGone = true;
//                adapter.setLoadMoreView(new CustomLoadMoreView());
//                rvProgress.setAdapter(adapter);
//                Toast.makeText(getActivity(), "use ok!", Toast.LENGTH_LONG).show();
//            }
//        });
//        TextView textView = new TextView(getActivity());
//        textView.setText("ahduhaskdhskahd");
//        adapter.addHeaderView(headView);
//        adapter.addHeaderView(textView);
//    }

    //从服务器获取列表数据
    private void getListFromData(String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        GsonRequest<Progress> request = new GsonRequest<Progress>(Request.Method.POST, map, UrlPath.PROGRESS_LIST, Progress.class, new Response.Listener<Progress>() {
            @Override
            public void onResponse(Progress progress) {
                if (progress.getList() != null && progress.getList().size() > 0) {
                    progressList = progress.getList();
                    adapter = new ProgressAdapter(R.layout.item_progress, progress.getList());
                    rvProgress.setAdapter(adapter);
                }else {
                    ToastUtils.showShortToast("该用户无数据");
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
