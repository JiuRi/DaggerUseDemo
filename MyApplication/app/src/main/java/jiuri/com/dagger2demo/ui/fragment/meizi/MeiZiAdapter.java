package jiuri.com.dagger2demo.ui.fragment.meizi;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Random;

import jiuri.com.dagger2demo.R;

/**
 * Created by user103 on 2017/9/19.
 */

public class MeiZiAdapter extends BaseQuickAdapter<MeiZiBean.ResultsBean,BaseViewHolder> {
    private Random mRandom=new Random();
    private  Context mContext;
    public MeiZiAdapter(@LayoutRes int layoutResId, @Nullable List<MeiZiBean.ResultsBean> data, Context context) {
        super(layoutResId, data);
        mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiZiBean.ResultsBean item) {
        ImageView imageView = helper.getView(R.id.imageview);
        //等比缩放

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = mRandom.nextInt(300)+400;
        imageView.setLayoutParams(layoutParams);
        Glide.with(mContext).load(item.getUrl()).into(imageView);
    }
}
