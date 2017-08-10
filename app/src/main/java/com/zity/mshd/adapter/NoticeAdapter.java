package com.zity.mshd.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zity.mshd.R;
import com.zity.mshd.bean.Guide;
import com.zity.mshd.bean.Notice;

import java.util.List;

/**
 * Created by luochao on 2017/8/10.
 * 通知公告
 */

public class NoticeAdapter extends BaseQuickAdapter<Notice.ListBean,BaseViewHolder>{
    public NoticeAdapter(@LayoutRes int layoutResId, @Nullable List<Notice.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Notice.ListBean item) {
            helper.setText(R.id.tv_title,item.getTitle())
                    .setText(R.id.tv_time,item.getCreatedate())
                    .setText(R.id.tv_orgname,item.getOrg_name());
    }
}
