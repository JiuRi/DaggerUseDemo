package jiuri.com.dagger2demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import jiuri.com.dagger2demo.MainActivity;
import jiuri.com.dagger2demo.R;

/**
 * Created by user103 on 2017/8/25.
 */

public class Activity2 extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.button)
    Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        ButterKnife.bind(this);
        //设置 可以执行 javascrip
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/first.html");
        //android添加javascript代码，让H5页面能够调用，第二个参数对应的是H5的
        mWebView.addJavascriptInterface(new PayJavaScriptInterface(), "jj");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:playVideo()");
            }
        });

    }

    private class PayJavaScriptInterface {

        @JavascriptInterface
        public void jumpActivity(int param) {

                Intent it = new Intent();
                it.setClass(Activity2.this, MainActivity.class);
                startActivity(it);

        }
        @JavascriptInterface
        public void showToast(String param) {

                Toast.makeText(Activity2.this, param+"      时间：" + System.currentTimeMillis(), Toast.LENGTH_SHORT)
                        .show();
            }

    }
}
