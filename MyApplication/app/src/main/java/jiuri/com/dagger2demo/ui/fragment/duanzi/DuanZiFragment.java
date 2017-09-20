package jiuri.com.dagger2demo.ui.fragment.duanzi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jiuri.com.dagger2demo.R;
import jiuri.com.dagger2demo.retrofit.ApiService;
import jiuri.com.dagger2demo.retrofit.SubscriberCallBack;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user103 on 2017/9/15.
 */

public class DuanZiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.reflash)
    SwipeRefreshLayout mReflash;
    private DuanZiAdapter mDuanZiAdapter;
    private List<DuanziBean> mDuanziBeanList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View duanZi = inflater.inflate(R.layout.fragment_duanzi, container, false);
        ButterKnife.bind(this, duanZi);
        return duanZi;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mReflash.setOnRefreshListener(this);
        mReflash.setColorSchemeColors(R.color.colorAccent,R.color.colorPrimaryDark);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDuanZiAdapter = new DuanZiAdapter(R.layout.item_duanzi, mDuanziBeanList, getContext());
        mRecycler.setAdapter(mDuanZiAdapter);
        mDuanZiAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        loadDate();

    }

    private void loadDate() {
        Observable<String> duanzi = RetrofitManager.instance().creatService(ApiService.class).getDuanZi().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        SubscriberCallBack<String> subscriberCallBack = new SubscriberCallBack<String>() {
            @Override
            protected void onSuccess(String string) {
                mReflash.setRefreshing(false);
                List<DuanziBean> duanziBeanList = GsonHelper.getDuanziBeanList(string);
                mDuanziBeanList.addAll(duanziBeanList);
                mDuanZiAdapter.notifyDataSetChanged();
            }
        };
        duanzi.subscribe(subscriberCallBack);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @Override
    public void onRefresh() {
        mReflash.setRefreshing(true);
        loadDate();
    }
}
