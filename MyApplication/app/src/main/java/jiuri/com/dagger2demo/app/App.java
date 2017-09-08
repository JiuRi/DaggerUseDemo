package jiuri.com.dagger2demo.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import jiuri.com.dagger2demo.util.ToastUtil;

/**
 * Created by user103 on 2017/8/4.
 */

public class App extends Application {

    private static final String TAG ="App" ;
    public static Context mApplicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: _______________________启动-----------" );
        mApplicationContext = getApplicationContext();
        ToastUtil.init(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=59b21092");


    }

    /**
     * 获取全局的上下文
     * @return
     */
    public static Context getAppContext() {
        return mApplicationContext;
    }

}
