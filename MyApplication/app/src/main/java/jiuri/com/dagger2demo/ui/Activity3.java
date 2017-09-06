package jiuri.com.dagger2demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jiuri.com.dagger2demo.R;
import jiuri.com.dagger2demo.retrofit.ApiService;
import jiuri.com.dagger2demo.retrofit.RetrofitDownLoadFile.RetrofitCallback;
import jiuri.com.dagger2demo.retrofit.RetrofitDownLoadFile.RetrofitDownLoad;
import jiuri.com.dagger2demo.retrofit.RxjavaRetrofitDownLoadFile.RxRetrofitCallback;
import jiuri.com.dagger2demo.retrofit.RxjavaRetrofitDownLoadFile.RxRetrofitDownLoad;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user103 on 2017/9/6.
 */

public class Activity3 extends AppCompatActivity {
    private static final String TAG = "Activity3";
    @BindView(R.id.button1)
    Button mButton1;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;
    @BindView(R.id.button4)
    Button mButton4;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.progress1)
    ProgressBar mProgress1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /**
             *    Retrofit 下载文件 带进度条的 封装
             *
             */
            case R.id.button1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RetrofitCallback<ResponseBody> callback = new RetrofitCallback<ResponseBody>() {
                            @Override
                            public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    InputStream is = response.body().byteStream();
                                    String path = getFilesDir().getAbsolutePath();
                                    File file = new File(path, "download.zip");
                                    FileOutputStream fos = new FileOutputStream(file);
                                    BufferedInputStream bis = new BufferedInputStream(is);
                                    byte[] buffer = new byte[1024];
                                    int len;
                                    while ((len = bis.read(buffer)) != -1) {
                                        fos.write(buffer, 0, len);
                                    }
                                    fos.flush();
                                    fos.close();
                                    bis.close();
                                    is.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e(TAG, "onFailure: _________________" + t.getMessage());
                            }

                            @Override
                            public void onLoading(long total, long progress) {
                                super.onLoading(total, progress);
                                mProgress.setProgress((int) (progress * 100 / total));
                                Log.d(TAG, "onLoading: ________________" + total + "              " + progress);
                            }
                        };
                        Call<ResponseBody> call = RetrofitDownLoad.getRetrofitService(callback, ApiService.class).downLoad("zip");
                        call.enqueue(callback);
                    }
                }).start();


                break;
            /**
             * Rxjava   加上 retrofit 的 下载带进度条的网络下载框架的封装 ！
             */
            case R.id.button2:
                RxRetrofitCallback<ResponseBody> responseBodyRxRetrofitCallback = new RxRetrofitCallback<ResponseBody>() {
                    @Override
                    protected void onSuccess(ResponseBody o) {
                        try {
                            InputStream is = o.byteStream();
                            String path = getFilesDir().getAbsolutePath();
                            File file = new File(path, "download.zip");
                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = bis.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                            }
                            fos.flush();
                            fos.close();
                            bis.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLoading(long total, long progress) {
                        super.onLoading(total, progress);

                        mProgress1.setProgress((int) (progress * 100 / total));

                    }
                };
                Observable<ResponseBody> zip = RxRetrofitDownLoad.getRetrofitService(responseBodyRxRetrofitCallback, ApiService.class).downLoadb("zip").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

                zip.subscribe(responseBodyRxRetrofitCallback);
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
        }
    }


}
