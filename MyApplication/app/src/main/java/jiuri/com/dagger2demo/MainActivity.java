package jiuri.com.dagger2demo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jiuri.com.dagger2demo.adapter.MainPagerAdapter;
import jiuri.com.dagger2demo.weiget.BaseActivity;
import jiuri.com.dagger2demo.weiget.MainView;

/**
 *
 *
 */
public class MainActivity extends BaseActivity implements MainView {
    private static String TAG = "MainActivity";
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.bottombar)
    TabLayout mTable;

    private int[] images={R.drawable.diary_selected,R.drawable.duanzi_selected,R.drawable.meizi_selected};
    private String[] name={"日记","段子","妹子"};
    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showTianqiDetail(String s) {

    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mTable.setupWithViewPager(mPager);
        for (int i = 0; i < name.length; i++) {
            if (i==0) {
                mTable.addTab(mTable.newTab().setIcon(images[i]).setText(name[i]), true);
            }
           else {
                mTable.addTab(mTable.newTab().setIcon(images[i]).setText(name[i]), false);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

