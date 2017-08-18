package jiuri.com.dagger2demo;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import jiuri.com.dagger2demo.dagger2.DaggerMainComponent;
import jiuri.com.dagger2demo.dagger2.MainModel;
import jiuri.com.dagger2demo.present.impl.ImplMainPresent;
import jiuri.com.dagger2demo.weiget.BaseActivity;
import jiuri.com.dagger2demo.weiget.MainView;

/**
 *
 *
 */
public class MainActivity extends BaseActivity implements MainView {
    private static String TAG = "MainActivity";
    @BindView(R.id.editext)
    EditText mEditext;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.progress)
    ProgressBar mProgress;

@Inject
    ImplMainPresent mImplMainPresent;
    @Override
    public int setLayoutId() {
        DaggerMainComponent.builder().mainModel(new MainModel(this)).build().inject(this);
        return R.layout.activity_main;
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        showProgress(mProgress);
        mImplMainPresent.loadDate(mEditext.getText().toString());
    }

    @Override
    public void showTianqiDetail(String s) {
        hideProgress(mProgress);
        mText.setText(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImplMainPresent.ondestory();
    }
}
