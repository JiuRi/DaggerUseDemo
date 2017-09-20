package jiuri.com.dagger2demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jiuri.com.dagger2demo.R;
import jiuri.com.dagger2demo.ui.fragment.meizi.MeiZiBean;
import jiuri.com.dagger2demo.ui.fragment.meizi.MeiZiList;

/**
 * Created by user103 on 2017/9/19.
 */

public class MeiZiDetailActivity extends AppCompatActivity {
    @BindView(R.id.pager)
    HorizontalInfiniteCycleViewPager mPager;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private List<MeiZiBean.ResultsBean> mBeanList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizidetail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bun = getIntent().getBundleExtra("bun");
        MeiZiList mlist = (MeiZiList) bun.getSerializable("list");
        int position = bun.getInt("position");
        Log.d("aaaa", "onCreate: _____________________" + mlist.getBeanList().size());
        mTitle.setText(position+"/"+mlist.getBeanList().size());
        mBeanList = mlist.getBeanList();
        mPager.setAdapter(new MyPagerAdapter());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = mPager.getCurrentItem();
                mTitle.setText(currentItem+"/"+mBeanList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.title, R.id.toolbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title:
                break;
            case R.id.toolbar:
                break;
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mBeanList.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View inflate = View.inflate(MeiZiDetailActivity.this, R.layout.itemmeizi, null);
            ImageView viewById = (ImageView) inflate.findViewById(R.id.imageview);
            Glide.with(MeiZiDetailActivity.this).load(mBeanList.get(position).getUrl()).into(viewById);
            container.addView(inflate);
            return inflate;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }
}
