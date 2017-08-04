package jiuri.com.dagger2demo.retrofit;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import jiuri.com.dagger2demo.util.ToastUtil;
import rx.Subscriber;

/**
 * Created by user103 on 2017/8/4.
 */

public abstract class BaseCallBack<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException){
            ToastUtil.showToast("网络连接超时");
        }
        else if (e instanceof ConnectException){
            ToastUtil.showToast("网络连接错误");
        }
        else {
            ToastUtil.showToast("网络错误");
        }
        onFailied(e);
    }

    protected abstract void onFailied(Throwable e);


}
