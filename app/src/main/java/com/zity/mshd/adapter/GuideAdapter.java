package com.zity.mshd.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zity.mshd.R;
import com.zity.mshd.bean.Guide;

import java.util.List;

/**
 * Created by luochao on 2017/8/10.
 * 办事指南
 */

public class GuideAdapter extends BaseQuickAdapter<Guide.ListBean,BaseViewHolder>{
    public GuideAdapter(@LayoutRes int layoutResId, @Nullable List<Guide.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Guide.ListBean item) {
            helper.setText(R.id.tv_title,item.getTitle())
                    .setText(R.id.tv_time,item.getCreatedate())
                    .setText(R.id.tv_orgname,item.getOrg_name());

    }
}
