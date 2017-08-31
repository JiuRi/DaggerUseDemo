package jiuri.com.dagger2demo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import jiuri.com.dagger2demo.dagger2.DaggerMainComponent;
import jiuri.com.dagger2demo.dagger2.MainModel;
import jiuri.com.dagger2demo.present.impl.ImplMainPresent;
import jiuri.com.dagger2demo.retrofit.ApiService;
import jiuri.com.dagger2demo.retrofit.DownLoad;
import jiuri.com.dagger2demo.retrofit.SubscriberCallBack;
import jiuri.com.dagger2demo.weiget.BaseActivity;
import jiuri.com.dagger2demo.weiget.MainView;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

@Inject
    ImplMainPresent mImplMainPresent;

    @Override
    public void initView() {
        mButton.setText("2222222222222222222222222222222222222222222222222222222222");
        File file =new File(getFilesDir(),"apatch/out.apatch");
        boolean exists = file.exists();
        if (exists){
            Log.e(TAG, "onViewClicked: __________________________已经下载查封包文件" );        }
        else {
            Observable<ResponseBody> apatch = DownLoad.instance().creatService(ApiService.class).downLoad("apatch").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            SubscriberCallBack<ResponseBody> subscriberCallBack = new SubscriberCallBack<ResponseBody>() {
                @Override
                protected void onSuccess(ResponseBody responseBody) {
                    InputStream inputStream = responseBody.byteStream();
                    verifyStoragePermissions(MainActivity.this);
                    inputstreamtofile(inputStream);

                }
            };
            apatch.subscribe(subscriberCallBack);
        }
    }

    @Override
    public int setLayoutId() {
        DaggerMainComponent.builder().mainModel(new MainModel(this)).build().inject(this);
        return R.layout.activity_main;
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        showProgress(mProgress);
        mImplMainPresent.loadDate(mEditext.getText().toString().trim());




    }

    @Override
    public void showTianqiDetail(String s) {
        hideProgress(mProgress);
        mText.setText("我是旧的————————旧的\n"+s);
    }
//apkpatch.bat -f new.apk -t old.apk -o output -k mykey.jks -p zx1991 -a key0 -e zx1991
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImplMainPresent.ondestory();
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public void inputstreamtofile(InputStream ins){
        File file =new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(),"out.apatch");
        Log.e(TAG, "inputstreamtofile: ———————————————————"+file.getAbsolutePath() );
        //   _/storage/emulated/0/out.apatch
        //    /storage/emulated/0/out.apatch
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

