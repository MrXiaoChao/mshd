package com.zity.mshd.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.StringUtils;
import com.zity.mshd.R;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseActivity;
import com.zity.mshd.bean.PublicServiceChild;
import com.zity.mshd.http.GsonRequest;
import com.zity.mshd.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by luochao on 2017/7/31.
 * 公共服务详情
 */

public class PublicServiceActivity extends BaseActivity {


    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_orgname)
    TextView tvOrgname;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard videoplayer;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int getLayout() {
        return R.layout.activity_public_service;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String flag = intent.getStringExtra("flag");
        if (StringUtils.equals("医疗",flag)){
            tvTooltarTitle.setText("医疗");
        }else if (StringUtils.equals("农业",flag)){
            tvTooltarTitle.setText("农业");
        }else if (StringUtils.equals("住房",flag)){
            tvTooltarTitle.setText("住房");
        }else if (StringUtils.equals("交通",flag)){
            tvTooltarTitle.setText("交通");
        }
        getDataFromService(id);
    }


    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    //从服务器获取数据
    private void getDataFromService(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        GsonRequest<PublicServiceChild> request = new GsonRequest<PublicServiceChild>(Request.Method.POST, map, UrlPath.GGFUXQ, PublicServiceChild.class, new Response.Listener<PublicServiceChild>() {
            @Override
            public void onResponse(PublicServiceChild response) {
                if (response != null) {
                    videoplayer.setUp(UrlPath.BaseUrl + response.getUrl()
                            , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, response.getTitle());
                    videoplayer.thumbImageView.setImageURI(Uri.parse(UrlPath.BaseUrl + response.getPicurl()));
                    tvTitle.setText(response.getTitle());
                    tvContent.setText(response.getContent());
                    tvOrgname.setText(response.getOrg_name());
                    tvTime.setText(response.getCreatedate());
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
