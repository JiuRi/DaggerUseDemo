package jiuri.com.dagger2demo.ui.fragment.meizi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jiuri.com.dagger2demo.R;
import jiuri.com.dagger2demo.retrofit.ApiService;
import jiuri.com.dagger2demo.retrofit.SubscriberCallBack;
import jiuri.com.dagger2demo.ui.fragment.MeiZiDetailActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user103 on 2017/9/15.
 */

public class MeiZiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.reflash)
    SwipeRefreshLayout mReflash;
    Unbinder unbinder;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<MeiZiBean.ResultsBean> mBeanList =new ArrayList<>();
    private MeiZiAdapter mMeiZiAdapter;
    private View mInflate;
    private int num=1;
    private SelectPicPopupWindow mSelectPicPopupWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View meizi = inflater.inflate(R.layout.fragment_meizi, container, false);
        unbinder = ButterKnife.bind(this, meizi);
        return meizi;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        Observable<MeiZiBean> meiZiBeanObservable = RetrofitManager.instance().creatService(ApiService.class).getMeiZi(num+"").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
        SubscriberCallBack<MeiZiBean> callBack = new SubscriberCallBack<MeiZiBean>() {
            @Override
            protected void onSuccess(MeiZiBean meiZiBean) {
                mReflash.setRefreshing(false);
                List<MeiZiBean.ResultsBean> results = meiZiBean.getResults();
                mBeanList.addAll(results);
                mMeiZiAdapter.notifyDataSetChanged();
               //mInflate.setVisibility(View.GONE);
            }
        };
        meiZiBeanObservable.subscribe(callBack);
    }

    private void initView() {
        mInflate = View.inflate(getContext(), R.layout.meizi_footview, null);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mStaggeredGridLayoutManager);
        Collections.shuffle(mBeanList);
        mMeiZiAdapter = new MeiZiAdapter(R.layout.itemmeizi, mBeanList, getContext());
        mMeiZiAdapter.addFooterView(mInflate);
        mMeiZiAdapter.setOnItemClickListener(this);
        mRecycler.setAdapter(mMeiZiAdapter);
      mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
          //用来标记是否正在向最后一个滑动，既是否向下滑动
          boolean isSlidingToLast = false;
          @Override
          public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
              super.onScrollStateChanged(recyclerView, newState);
              if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                  int[] ints = new int[mStaggeredGridLayoutManager.getSpanCount()];
                  mStaggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(ints);
                  int lastVisiblePos = getMaxElem(ints);
                  int totalItemCount = mStaggeredGridLayoutManager.getItemCount();

                  // 判断是否滚动到底部
                  if (lastVisiblePos == (totalItemCount - 1) && isSlidingToLast) {
                      //加载更多功能的代码
                      Log.d("aaa", "onScrolled: __________________________________记载更过");
                      ++num;
                      mInflate.setVisibility(View.VISIBLE);
                      initData();
                  }
              }
          }
              @Override
              public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                  super.onScrolled(recyclerView, dx, dy);

                  //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                  if (dy > 0) {
                      //大于0表示，正在向下滚动

                      isSlidingToLast = true;
                  } else {
                      //小于等于0 表示停止或向上滚动
                      isSlidingToLast = false;
                  }


              }
          });
        mReflash.setOnRefreshListener(this);
    }
    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i]>maxVal){
                maxVal = arr[i];}
        }
        return maxVal;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onRefresh() {
      mReflash.setRefreshing(true);
        mBeanList.clear();
        initData();
    }
    //条目点击时间
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MeiZiList meiZiList = new MeiZiList();
        meiZiList.setBeanList(mBeanList);
        Intent intent1 = new Intent(getActivity(), MeiZiDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("list",meiZiList);
        bundle.putInt("position",position);
        intent1.putExtra("bun",bundle);
        getContext().startActivity(intent1);

    }


}
