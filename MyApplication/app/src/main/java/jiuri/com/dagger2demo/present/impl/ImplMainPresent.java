package jiuri.com.dagger2demo.present.impl;

import android.util.Log;

import javax.inject.Inject;

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

    @Inject
    public ImplMainPresent(MainActivity mainActivity) {
        mMainActivity=mainActivity;
    }


    @Override
    public void loadData(String s) {
        Log.d(TAG, "loadData: ++++++++++++++++++++++++++++++++++");
        Observable<String> tianQi = RetrofitManager.instance().creatService(ApiService.class).
                getTianQi(s);
        SubscriberCallBack<String> stringSubscriberCallBack = new SubscriberCallBack<String> () {
            @Override
            protected void onSuccess(String o) {
               mMainActivity.showTianqiDetail(o);
            }
        };
        addSubscription(tianQi,stringSubscriberCallBack);
    }

}
