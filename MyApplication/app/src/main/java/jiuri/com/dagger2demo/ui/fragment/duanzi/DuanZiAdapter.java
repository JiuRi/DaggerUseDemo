package jiuri.com.dagger2demo.ui.fragment.duanzi;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import jiuri.com.dagger2demo.R;

/**
 * Created by user103 on 2017/9/18.
 */

public class DuanZiAdapter extends BaseQuickAdapter<DuanziBean, BaseViewHolder>{
    private Context mContext;
    public DuanZiAdapter(@LayoutRes int layoutResId, @Nullable List<DuanziBean> data, Context context) {
        super(layoutResId, data);
        mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DuanziBean item) {
        Log.e(TAG, "convert: _______________________"+item.getType()+item.getGroupBean() );
        ImageView view = helper.getView(R.id.duanzi_civ_avatar);
        if (item.getGroupBean()!=null) {
            Glide.with(mContext).load(item.getGroupBean().getUser().getAvatar_url()).into(view);
            helper.setText(R.id.duanzi_tv_author,  item.getGroupBean().getUser().getName());
            helper.setText(R.id.duanzi_tv_content,item.getGroupBean().getText());
        }

    }
}
