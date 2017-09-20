package jiuri.com.dagger2demo.ui.fragment.riji;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jiuri.com.dagger2demo.R;

/**
 * Created by user103 on 2017/9/14.
 */

public class RiJiFragment extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    Unbinder unbinder;
    private List<DateInfo> mList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_riji, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mList.add(new DateInfo(null,"2017/0/09","标题0","内容0"));
        mList.add(new DateInfo(null,"2017/0/09","标题1","内容1"));
        mList.add(new DateInfo(null,"2017/0/09","标题2","内容2"));
        mList.add(new DateInfo(null,"2017/0/09","标题3","内容3"));
        mList.add(new DateInfo(null,"2017/0/09","标题4","内容4"));
        mList.add(new DateInfo(null,"2017/0/09","标题5","内容5"));
        RIjIAdapter rIjIAdapter = new RIjIAdapter(R.layout.item_riji, mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.hasFixedSize();
        mRecycler.setAdapter(rIjIAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
