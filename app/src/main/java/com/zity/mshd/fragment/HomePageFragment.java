package com.zity.mshd.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zity.mshd.R;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseFragment;
import com.zity.mshd.bean.HomePageBanner;
import com.zity.mshd.http.GsonRequest;
import com.zity.mshd.http.UrlPath;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页
 * Created by luochao on 2017/7/18.
 */

public class HomePageFragment extends BaseFragment {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    List<String> images = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initData() {
        tvTooltarTitle.setText("群众服务平台");
        ivToolbarBack.setVisibility(View.GONE);
        getImagerFromService();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtils.showShortToast("汉子别跑，求勾搭" + position);
            }
        });

    }

    //加载图片

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }

    }
    //获取banner图片
    private void getImagerFromService(){
        GsonRequest<HomePageBanner> request =new GsonRequest<HomePageBanner>(UrlPath.BANNER_IMAGE, HomePageBanner.class, new Response.Listener<HomePageBanner>() {
            @Override
            public void onResponse(HomePageBanner response) {
                if (response.getList()!=null && response.getList().size()>0){
                    //设置图片加载器
                    for (int i = 0; i <response.getList().size() ; i++) {
                        images.add("http://211.151.183.170:9081"+response.getList().get(i).getUrl());
                    }
                    banner.setImageLoader(new GlideImageLoader());
                    //设置图片集合
                    banner.setImages(images);
                    banner.setDelayTime(3600);
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();
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
