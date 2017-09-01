package jiuri.com.dagger2demo;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
    private String mString;

    @Override
    public void initView() {
        mButton.setText("请求深圳天气");
    }

    @Override
    public int setLayoutId() {
        DaggerMainComponent.builder().mainModel(new MainModel(this)).build().inject(this);
        return R.layout.activity_main;
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        showProgress(mProgress);
        mString = mEditext.getText().toString().trim();
        mImplMainPresent.loadDate(mString);




    }

    @Override
    public void showTianqiDetail(String s) {
        hideProgress(mProgress);
        SpannableString spannableString =new SpannableString("请求"+mString+"天气 :\n"+s);
        ForegroundColorSpan foregroundColorSpan=new ForegroundColorSpan(R.color.colorAccent);
      //  ImageSpan image = new ImageSpan(this,R.color.colorPrimaryDark, DynamicDrawableSpan.ALIGN_BOTTOM);
        spannableString.setSpan(foregroundColorSpan,2,3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mText.setText(spannableString);
    }
//apkpatch.bat -f new.apk -t old.apk -o output -k mykey.jks -p zx1991 -a key0 -e zx1991
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImplMainPresent.ondestory();
    }


}

