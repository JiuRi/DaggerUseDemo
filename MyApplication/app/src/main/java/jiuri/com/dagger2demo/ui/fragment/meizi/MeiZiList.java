package jiuri.com.dagger2demo.ui.fragment.meizi;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user103 on 2017/9/19.
 */

public class MeiZiList implements Serializable {
    private List<MeiZiBean.ResultsBean> mBeanList;

    public List<MeiZiBean.ResultsBean> getBeanList() {
        return mBeanList;
    }

    public void setBeanList(List<MeiZiBean.ResultsBean> beanList) {
        mBeanList = beanList;
    }
}
