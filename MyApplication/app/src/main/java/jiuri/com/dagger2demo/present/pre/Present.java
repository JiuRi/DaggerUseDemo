package jiuri.com.dagger2demo.present.pre;

/**
 * Created by user103 on 2017/8/4.
 */

public interface Present<T> {
     void attachView(T activity);
    void detachView(T activity);
}
