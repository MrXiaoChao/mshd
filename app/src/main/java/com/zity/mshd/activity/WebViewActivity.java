package com.zity.mshd.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;
import com.zity.mshd.R;
import com.zity.mshd.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by luochao on 2017/4/20.
 * 网页
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_tooltar_title)
    TextView tvTooltarTitle;

    @Override
    protected int getLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String URL = intent.getStringExtra("URL");
        String title = intent.getStringExtra("title");
        if (StringUtils.equals("关于我们", title)) {
            tvTooltarTitle.setText("关于我们");
        }else if (StringUtils.equals("诉求须知",title )) {
            tvTooltarTitle.setText("诉求须知");
        } else {
            tvTooltarTitle.setText(title);
        }
        init(URL);
    }

    private WebView webView;
    private ProgressBar pb;

    private void init(String url) {
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setMax(100);
        webView = (WebView) findViewById(R.id.webView);
        //WebView加载web资源
        webView.loadUrl(url);

        webView.requestFocusFromTouch();//支持获取手势焦点
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);//JS交互
        webView.setWebChromeClient(new WebViewChromeClient());
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick(R.id.iv_toolbar_back)
    public void onClick() {
        onBackPressedSupport();
    }

    private class WebViewChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            pb.setProgress(newProgress);
            if (newProgress == 100) {
                pb.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }
}
