package jiuri.com.dagger2demo.app;

import android.app.Application;
import android.content.Context;

import jiuri.com.dagger2demo.util.ToastUtil;

/**
 * Created by user103 on 2017/8/4.
 */

public class App extends Application {

    public static Context mApplicationContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
        ToastUtil.init(this);

    }

    /**
     * 获取全局的上下文
     * @return
     */
    public static Context getAppContext() {
        return mApplicationContext;
    }

}
