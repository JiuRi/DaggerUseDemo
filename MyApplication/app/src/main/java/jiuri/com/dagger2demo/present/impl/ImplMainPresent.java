package jiuri.com.dagger2demo.present.impl;

import jiuri.com.dagger2demo.MainActivity;
import jiuri.com.dagger2demo.present.pre.BaseMainPresent;
import jiuri.com.dagger2demo.retrofit.ApiService;
import jiuri.com.dagger2demo.retrofit.RetrofitManager;
import jiuri.com.dagger2demo.retrofit.SubscriberCallBack;
import rx.Observable;

/**
 * Created by user103 on 2017/8/4.
 */

public class ImplMainPresent extends BaseMainPresent {
    private  static  String TAG="ImplMainPresent";
    private MainActivity mMainActivity;
    public ImplMainPresent(MainActivity mainActivity) {
        mMainActivity=mainActivity;
    }

 /*   @Override
    public void loadDate(String s) {
        Observable<String> tianQi = RetrofitManager.instance().creatService(ApiService.class).
                getTianQi(s).
                subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread());

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: -__________________________________");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ____________________________"+e);
            }

            @Override
            public void onNext(String o) {
                mMainActivity.setTianqiDetail(o);
            }
        };
        tianQi.subscribe(observer);
    }*/

    @Override
    public void loadData(String s) {
        Observable<String> tianQi = RetrofitManager.instance().creatService(ApiService.class).
                getTianQi(s);
        SubscriberCallBack<String> stringSubscriberCallBack = new SubscriberCallBack<String> () {
            @Override
            protected void onSuccess(String o) {
                mMainActivity.setTianqiDetail(o);
            }
        };
        addSubscription(tianQi,stringSubscriberCallBack);
    }

}
