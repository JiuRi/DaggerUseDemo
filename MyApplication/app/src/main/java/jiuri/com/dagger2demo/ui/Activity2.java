package jiuri.com.dagger2demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import jiuri.com.dagger2demo.MainActivity;
import jiuri.com.dagger2demo.R;

/**
 * 这里主要是演示 Html 和 Android 的混合开发
 * 需要注意的是 ：
 * <p>
 * 1.加载网页要用WebView控件
 * <p>
 * 2.要想支持js就要设置 webview.getSettings().setJavaScriptEnabled(true);
 * <p>
 * 3.webview.loadUrl可以加载本地的html页面也可以加载网络html页面，还可以调用js的方法 webview.loadUrl("file:///android_asset/JavaCallJS1.html");
 * <p>
 * 放html页面的时候是放在assets目录下，代码加载的时候： file:///android_asset/xxxxx.html
 * <p>
 * 4.在Android中要从html页面中弹出对话框需要
 * <p>
 * //支持从html中弹出对话框 //webview.setWebChromeClient(new WebChromeClient());
 */


public class Activity2 extends AppCompatActivity {

    private static final String TAG = "Activity2";
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
        mWebView.setWebChromeClient(new WebChromeClient());

        //android添加javascript代码，让H5页面能够调用，第二个参数对应的是H5的
        mWebView.addJavascriptInterface(new PayJavaScriptInterface(), "jj");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 下面这个就是 android 调用 html5 的方法 ：
                 *        mWebView.loadUrl("javascript:playVideo()");
                 *        其中参数是 ：   mWebView.loadUrl（“javascript : html5中需要 被调用的 h5方法。”）
                 */
                mWebView.loadUrl("javascript:playVideo()");


            }
        });

    }

    /**
     * 'js 调用安卓
     * 第一部：首先要给webview  添加 一个 javascripInterface
     * mWebView.addJavascriptInterface(new PayJavaScriptInterface(), "jj");
     * 其中 ： 第一个参数书我们自己写的 一个类 然后在我们的 类里面  去写 和 html5 同名的方法 然后在进行 方法的操作； 在方法的 顶部 加上 @JavascripInterface
     * : 第二个参数  是 我们自己随意 定义的一个字符串 但是 要和 html5 里面的 要一样。
     * window.jj.showToast(param);   html5 调用 ： window.第二个参数的字符串，和 js 同名的 方法；
     */
    private class PayJavaScriptInterface {

        @JavascriptInterface
        public void jumpActivity(int param) {

            Intent it = new Intent();
            it.setClass(Activity2.this, MainActivity.class);
            startActivity(it);

        }

        @JavascriptInterface
        public void showToast(String param) throws IOException {

            Toast.makeText(Activity2.this, param + "      时间：" + System.currentTimeMillis(), Toast.LENGTH_SHORT)
                    .show();

        }

    }
}
