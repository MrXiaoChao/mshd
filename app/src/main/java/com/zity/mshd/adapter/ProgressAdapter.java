package com.zity.mshd.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zity.mshd.R;
import com.zity.mshd.bean.Progress;

import java.util.List;

/**
 * Created by luochao on 2017/7/21.
 * 进度适配器
 */

public class ProgressAdapter extends BaseQuickAdapter<Progress.ListBean, BaseViewHolder> {


    public ProgressAdapter(@LayoutRes int layoutResId, @Nullable List<Progress.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Progress.ListBean item) {
        int status =item.getState();
        String itemStasus=null;
        switch (status){
            case 1:
                itemStasus="待受理";
                break;
            case 2:
                itemStasus="受理中";
                break;
            case 3:
                itemStasus="已分派";
                break;
            case 4:
                itemStasus="已答复";
                break;
            case 5:
                itemStasus="已评价";
                break;
            case 6:
                itemStasus="已退回";
                break;
            case 7:
                itemStasus="无效";
                break;
            case 8:
                itemStasus="完成";
                break;
        }
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_time, item.getCreatedate())
                .setText(R.id.tv_state, "当前状态："+itemStasus);
    }
}
