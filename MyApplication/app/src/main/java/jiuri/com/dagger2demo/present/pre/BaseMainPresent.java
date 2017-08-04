package jiuri.com.dagger2demo.present.pre;

/**
 * Created by user103 on 2017/8/4.
 */


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
public abstract class BaseMainPresent implements MainPresent {
    @Override
    public void loadDate(String s) {
        loadData(s);
    }

    public void  addSubscription(Observable observable, Subscriber observer){
        new CompositeSubscription().add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()
        ).subscribe(observer));

    }
    public abstract  void  loadData(String s);

}
