package jiuri.com.dagger2demo.present.pre;

/**
 * Created by user103 on 2017/8/4.
 */

public abstract class BasePresent<T> implements Present<T> {
    public BasePresent(T activity) {

    }

    @Override
    public void attachView(T activity) {

    }

    @Override
    public void detachView(T activity) {

    }

    protected abstract void   loadData(String url);
}
