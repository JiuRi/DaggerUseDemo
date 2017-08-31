package jiuri.com.dagger2demo.weiget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by user103 on 2017/8/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        mBind = ButterKnife.bind(this);
        initView();
    }

    public abstract void initView();

    public abstract int setLayoutId();

    public void toNextActivity(Class<? extends Activity> targetActivity,Boolean isFinish){
        Intent intent=new Intent(this,targetActivity);
        startActivity(intent);
        if (isFinish){
            finish();
        }
    }

    /**
     * 显示数据正在加载的进度条
     * @param mProgress
     */
    public void showProgress(View mProgress) {
        mProgress.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏数据加载的进度条
     * @param mProgress
     */
    public void hideProgress(View mProgress) {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
