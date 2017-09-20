package jiuri.com.dagger2demo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import jiuri.com.dagger2demo.adapter.MainPagerAdapter;
import jiuri.com.dagger2demo.weiget.BaseActivity;
import jiuri.com.dagger2demo.weiget.MainView;

/**
 *
 *
 */
public class MainActivity extends BaseActivity implements MainView, TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    private static String TAG = "MainActivity";
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.bottombar)
    TabLayout mTable;

    private int[] images={R.drawable.diary_seletor,R.drawable.duanzi_seletor,R.drawable.meizi_seletor};
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
        mPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        for (int i = 0; i < name.length; i++) {

            if (i==0){
                mTable.addTab(mTable.newTab().setText(name[0]).setIcon(images[0]));
            }
            else {
                mTable.addTab(mTable.newTab().setText(name[i]).setIcon(images[i]));
            }
        }
        mTable.addOnTabSelectedListener(this);
        mPager.addOnPageChangeListener(this);
        mPager.setCurrentItem(0);

    }

//table listener
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.e(TAG, "onTabSelected: ________________"+mTable.getSelectedTabPosition() );
            mPager.setCurrentItem(mTable.getSelectedTabPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
// viewpager listener
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        mTable.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

