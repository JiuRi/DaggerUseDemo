package jiuri.com.dagger2demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jiuri.com.dagger2demo.ui.fragment.duanzi.DuanZiFragment;
import jiuri.com.dagger2demo.ui.fragment.meizi.MeiZiFragment;
import jiuri.com.dagger2demo.ui.fragment.riji.RiJiFragment;

/**
 * Created by user103 on 2017/9/14.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private String[] name={"日记","段子","妹子"};
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RiJiFragment();
            case 1:
                return new DuanZiFragment();
            case 2:
                return  new MeiZiFragment();
            default:
                return new RiJiFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }


}
