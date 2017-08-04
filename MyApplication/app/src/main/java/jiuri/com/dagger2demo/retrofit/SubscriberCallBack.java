package jiuri.com.dagger2demo.retrofit;

import android.util.Log;

/**
 * Created by user103 on 2017/8/4.
 */

public abstract class SubscriberCallBack<T> extends BaseCallBack<T> {
    @Override
    protected void onFailied(Throwable e) {
        Log.e("CallBack", "onFailied:_______________________ "+e.toString());
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    protected abstract void onSuccess(T t);
}
