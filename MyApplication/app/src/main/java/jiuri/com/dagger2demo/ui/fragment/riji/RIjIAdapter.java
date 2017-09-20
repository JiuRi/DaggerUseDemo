package jiuri.com.dagger2demo.ui.fragment.riji;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import jiuri.com.dagger2demo.R;

/**
 * Created by user103 on 2017/9/20.
 */

public class RIjIAdapter extends BaseQuickAdapter<DateInfo ,BaseViewHolder> {
    private int CheckPosition=-1;
    public RIjIAdapter(@LayoutRes int layoutResId, @Nullable List<DateInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, DateInfo item) {
            helper.setText(R.id.date,item.getDate());
            helper.setText(R.id.title,item.getTitle());
            helper.setText(R.id.body,item.getBody());
        helper.setVisible(R.id.edit,false);
        View convertView = helper.getConvertView();
        if (CheckPosition==helper.getPosition()){

                helper.setVisible(R.id.edit,true);
        }
        else {
            helper.setVisible(R.id.edit,false);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (helper.getView(R.id.edit).getVisibility()==View.VISIBLE){
                        helper.setVisible(R.id.edit,false);
                    }
                    else {
                        CheckPosition=helper.getPosition();
                        notifyDataSetChanged();
                    }



            }
        });
    }
}
