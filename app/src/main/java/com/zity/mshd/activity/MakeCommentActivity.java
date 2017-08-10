package com.zity.mshd.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.zity.mshd.R;
import com.zity.mshd.app.App;
import com.zity.mshd.base.BaseActivity;
import com.zity.mshd.bean.Success1;
import com.zity.mshd.http.GsonRequest;
import com.zity.mshd.http.UrlPath;
import com.zity.mshd.widegt.RatingBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by luochao on 2017/8/8.
 * 发表评论
 */

public class MakeCommentActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;
    @BindView(R.id.tv_tooltar_make)
    TextView tvTooltarMake;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.et_make_comment)
    EditText etMakeComment;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    private String id;
    private int count;

    @Override
    protected int getLayout() {
        return R.layout.activity_makecomment;
    }



    @Override
    protected void initData() {
        Intent intent =getIntent();
        String title =intent.getStringExtra("title");
        String time=intent.getStringExtra("time");
        id = intent.getStringExtra("id");
        tvTitle.setText(title);
        tvTime.setText(time);
        tvTooltarTitle.setText("发表评价");
        ratingbar.setClickable(true);//设置可否点击
        ratingbar.setStar(0f);//设置显示的星星个数
        ratingbar.setStepSize(RatingBar.StepSize.Full);//设置每次点击增加一颗星还是半颗星
        ratingbar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {//点击星星变化后选中的个数
                count = (int) ratingCount;
                switch (count){
                    case 1:
                        tv_comment.setText("非常差");
                        break;
                    case 2:
                        tv_comment.setText("差");
                        break;
                    case 3:
                        tv_comment.setText("一般");
                        break;
                    case 4:
                        tv_comment.setText("好");
                        break;
                    case 5:
                        tv_comment.setText("非常好");
                        break;
                }
            }
        });
    }



    @OnClick({R.id.iv_toolbar_back, R.id.tv_tooltar_make})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                onBackPressedSupport();
                break;
            case R.id.tv_tooltar_make:
                String content =etMakeComment.getText().toString().trim();
                String grade=tv_comment.getText().toString().trim();
                if (EmptyUtils.isEmpty(grade)){
                    ToastUtils.showShortToast("请填写评价");
                    return;
                }
                if (EmptyUtils.isEmpty(content)){
                    ToastUtils.showShortToast("评价内容不能为空");
                    return;
                }
                sendCommentToService(id,content, String.valueOf(count));
                break;
        }
    }
    //提交评论到服务器
    private void sendCommentToService(String id,String content,String grade){
        Map<String,String> map =new HashMap<>();
        map.put("id",id);
        map.put("content",content);
        map.put("grade",grade);
        GsonRequest<Success1> request =new GsonRequest<Success1>(Request.Method.POST, map, UrlPath.MAKE_COMMENT, Success1.class, new Response.Listener<Success1>() {
            @Override
            public void onResponse(Success1 response) {
                if (response.isSuccess()){
                    ToastUtils.showShortToast(response.getMessage());
                    finish();
                }else {
                    ToastUtils.showShortToast(response.getMessage());
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
