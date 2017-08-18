package jiuri.com.dagger2demo.present.pre;

/**
 * Created by user103 on 2017/8/4.
 */


import com.orhanobut.logger.Logger;

import jiuri.com.dagger2demo.adapter.LoggerAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
public abstract class BaseMainPresent implements MainPresent {

    private CompositeSubscription mCompositeSubscription;

    @Override
    public void loadDate(String s) {
        loadData(s);
    }

    public void  addSubscription(Observable observable, Subscriber observer){
        mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()
        ).subscribe(observer));

    }
    public abstract  void  loadData(String s);
    public void ondestory(){
        Logger.addLogAdapter(new LoggerAdapter());
        mCompositeSubscription.unsubscribe();
        Logger.d("已经解除绑定");
    }
}
