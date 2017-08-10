package com.zity.mshd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.zity.mshd.R;
import com.zity.mshd.activity.MakeCommentActivity;
import com.zity.mshd.bean.ProgressXQ;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by luochao on 2017/8/7.
 * 信访详情
 */

public class ProgressXqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private List<ProgressXQ.ZtBean> list = new ArrayList<>();
    private static final int TYPE_1 = 0x0001;
    private static final int TYPE_2 = 0x0002;
    private static final int TYPE_3 = 0x0003;
    private static final int TYPE_4 = 0x0004;
    private ProgressXQ progressXQ;

    private Context context;

    public ProgressXqAdapter(Context context, List<ProgressXQ.ZtBean> list,ProgressXQ progressXQ) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context=context;
        this.progressXQ=progressXQ;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_1) {
            return new ViewHolder1(inflater.inflate(R.layout.item_progress_xq1, parent, false));
        } else if (viewType == TYPE_2) {
            return new ViewHolder2(inflater.inflate(R.layout.item_progress_xq2, parent, false));
        } else if (viewType == TYPE_4) {
            return new ViewHolder4(inflater.inflate(R.layout.item_progress_xq4, parent, false));
        }else {
            return new ViewHolder3(inflater.inflate(R.layout.item_progress_xq3, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder1) {
            ViewHolder1 viewHolder1 = (ViewHolder1) holder;
            viewHolder1.bindHolder(list.get(position));
        } else if (holder instanceof ViewHolder2) {
            ViewHolder2 viewHolder2 = (ViewHolder2) holder;
            viewHolder2.bindHolder(list.get(position));
        }  else if (holder instanceof ViewHolder4) {
            ViewHolder4 viewHolder4 = (ViewHolder4) holder;
            viewHolder4.bindHolder(list.get(position));
        }else if (holder instanceof ViewHolder3){
            ViewHolder3 viewHolder3 = (ViewHolder3) holder;
            viewHolder3.bindHolder(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (list.get(position).getTasstatus().equals("1") || list.get(position).getTasstatus().equals("2")) {
            return TYPE_1;
        } else if (list.get(position).getTasstatus().equals("8")) {
            return TYPE_3;
        }else if (list.get(position).getTasstatus().equals("7")) {
            return TYPE_4;
        }  else {
            return TYPE_2;
        }

    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        private TextView tvcontent, tvtime;


        public ViewHolder1(View itemView) {
            super(itemView);
            tvcontent = (TextView) itemView.findViewById(R.id.tv_content);
            tvtime = (TextView) itemView.findViewById(R.id.tv_time);
        }

        public void bindHolder(ProgressXQ.ZtBean ztBean) {
            tvcontent.setText(ztBean.getTascontent());
            tvtime.setText(ztBean.getTascreatetime());
            if (ztBean.getTasstatus().equals("2")){
                tvcontent.setTextColor(context.getResources().getColor(R.color.lan));
            }else if (ztBean.getTasstatus().equals("6")){
                tvcontent.setTextColor(context.getResources().getColor(R.color.Progressred));
            }
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        private TextView tvcontent, tvtime, tvOrgname;


        public ViewHolder2(View itemView) {
            super(itemView);
            tvcontent = (TextView) itemView.findViewById(R.id.tv_content);
            tvtime = (TextView) itemView.findViewById(R.id.tv_time);
            tvOrgname = (TextView) itemView.findViewById(R.id.tv_orgname);
        }

        public void bindHolder(ProgressXQ.ZtBean ztBean) {
            tvtime.setText(ztBean.getTascreatetime());
            tvOrgname.setText(ztBean.getOrg_name());
            if (ztBean.getTasstatus().equals("4")){
                StringBuffer stringBuffer =new StringBuffer("补充：");
                stringBuffer.append(ztBean.getTascontent());
                SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer);
                style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.Progressblack)), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvcontent.setText(style);
            }else if (ztBean.getTasstatus().equals("3")){
                StringBuffer stringBuffer =new StringBuffer("答复：");
                stringBuffer.append(ztBean.getTascontent());
                SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer);
                style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.Progressblack)), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvcontent.setText(style);
            }else if (ztBean.getTasstatus().equals("5")){
                StringBuffer stringBuffer =new StringBuffer("退回：");
                stringBuffer.append(ztBean.getTascontent());
                SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer);
                style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.Progressblack)), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvcontent.setText(style);
            }else if (ztBean.getTasstatus().equals("6")){
                StringBuffer stringBuffer =new StringBuffer("无效：");
                stringBuffer.append(ztBean.getTascontent());
                SpannableStringBuilder style = new SpannableStringBuilder(stringBuffer);
                style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.Progressblack)), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvcontent.setText(style);
            }else {
                tvcontent.setText(ztBean.getTascontent());
            }
        }
    }


    public class ViewHolder3 extends RecyclerView.ViewHolder {
        private TextView tvcontent, tvtime;
        private com.zity.mshd.widegt.RatingBar ratingBar;


        public ViewHolder3(View itemView) {
            super(itemView);
            tvcontent = (TextView) itemView.findViewById(R.id.tv_content);
            tvtime = (TextView) itemView.findViewById(R.id.tv_time);
            ratingBar = (com.zity.mshd.widegt.RatingBar) itemView.findViewById(R.id.ratingbar);
        }

        public void bindHolder(ProgressXQ.ZtBean ztBean) {
            tvcontent.setText(ztBean.getTascontent());
            tvtime.setText(ztBean.getTascreatetime());
            ratingBar.setClickable(false);//设置可否点击
            ratingBar.setStar(Float.parseFloat(ztBean.getGrade()));//设置显示的星星个数
        }
    }

    public class ViewHolder4 extends RecyclerView.ViewHolder {
        private TextView tvcontent, tvtime;
        private Button btn_progress;


        public ViewHolder4(View itemView) {
            super(itemView);
            tvcontent = (TextView) itemView.findViewById(R.id.tv_content);
            tvtime = (TextView) itemView.findViewById(R.id.tv_time);
            btn_progress = (Button) itemView.findViewById(R.id.btn_progress);
        }

        public void bindHolder(final ProgressXQ.ZtBean ztBean) {
            tvcontent.setText(ztBean.getTascontent());
            tvtime.setText(ztBean.getTascreatetime());
            btn_progress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, MakeCommentActivity.class);
                    intent.putExtra("title",progressXQ.getTitle());
                    intent.putExtra("time",progressXQ.getCreatetime());
                    intent.putExtra("id",progressXQ.getAppealId());
                    context.startActivity(intent);
                }
            });
        }
    }
}


