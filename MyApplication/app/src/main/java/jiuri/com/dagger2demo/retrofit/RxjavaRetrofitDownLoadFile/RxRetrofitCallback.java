package jiuri.com.dagger2demo.retrofit.RxjavaRetrofitDownLoadFile;

import android.util.Log;

import jiuri.com.dagger2demo.retrofit.BaseCallBack;

/**
 * Created by user103 on 2017/9/6.
 */

public abstract class RxRetrofitCallback<T> extends BaseCallBack<T> {

    @Override
    protected void onFailied(Throwable e) {
        Log.e("CallBack", "onFailied:_______________________ " + e.toString());
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    protected abstract void onSuccess(T t);

    public void onLoading(long total, long progress) {

    }

}