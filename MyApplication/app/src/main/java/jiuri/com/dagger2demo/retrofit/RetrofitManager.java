package jiuri.com.dagger2demo.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jiuri.com.dagger2demo.api_base.Constant;
import jiuri.com.dagger2demo.app.App;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by user103 on 2017/8/4.
 */

public class RetrofitManager {
private static String TAG="RetrofitManager";
    private static RetrofitManager mRetrofitManager;
    private Retrofit mRetrofit;

    public RetrofitManager() {
        initRetrofit();
    }


    public static RetrofitManager instance() {
        synchronized (RetrofitManager.class) {
            if (mRetrofitManager == null) {
                mRetrofitManager = new RetrofitManager();
            }
        }
        return mRetrofitManager;
    }

    private void initRetrofit() {
        // 1
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(20, TimeUnit.SECONDS);//设置链接超时时间
        builder.readTimeout(20, TimeUnit.SECONDS); // 是指读取超时时间
        builder.writeTimeout(20, TimeUnit.SECONDS);//是设置写入超时时间
        builder.retryOnConnectionFailure(true);

        // OkHttpClient build = builder.build();
        //也可以写成下面这个新式 ;
        //  new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).....
        //okhttp   原本创建请求的
       /* Request.Builder builder1 = new Request.Builder();
        Request.Builder url = builder1.url("");
        Request build1 = url.build();
        build.newCall(build1);*/

        // Log信息拦截器
       HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置 Debug Log 模式
        builder.addInterceptor(loggingInterceptor);
       builder.addInterceptor(new MyIntercapter());
        builder.addNetworkInterceptor(new MyIntercapter());


        //获取到的是 data//data //<application package>/cache目录  也就是应用包名吓得 chtch 目录
        File cacheDir = App.getAppContext().getCacheDir();
        File okhttpCatchFilr = new File(cacheDir, "ohhttp_catch");
        //设置缓存文件 和缓存的最大大小
        Cache cache = new Cache(okhttpCatchFilr, 1024 * 1024 * 100); //
        OkHttpClient.Builder cache1 = builder.cache(cache);





        //创建 retrofit 对象
        mRetrofit = new Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                client(cache1.build()).
                baseUrl(Constant.BASE_URL_TIANQI).
                build();
    }

    /**
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T creatService(Class<T> service) {
        return mRetrofit.create(service);
    }
    class  MyIntercapter implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //  没有网络的时候  ;
            if (!chechNet()) {
                Log.d(TAG, "intercept:_________________________ 没有网络读取缓存");
                request= request.newBuilder().
                        cacheControl(CacheControl.FORCE_CACHE).
                        build();
            }
            Response response = chain.proceed(request);
            //有网络的时候 ;
            if (chechNet()) {
                Log.d(TAG, "intercept:_________________________ 有网络从服务器获取数据");
                Log.d(TAG, "intercept: ______________________________________有网络判断缓存是否过期,若不过期,加载缓存,若过期加载网络重新缓存");
                int maxAge = 10; // 在线缓存在6秒内可读取
                String cacheControl = request.cacheControl().toString();
                Log.e("yjbo-cache", "在线缓存在1分钟内可读取" + cacheControl);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            }
            else {
                Log.e("yjbo-cache", "离线时缓存保存6秒");
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=6")
                        .build();
            }
        }
    }

    /**
     * 检测网络
     * @return
     */
    private boolean chechNet(){
        ConnectivityManager manager = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager!=null) {
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if (activeNetworkInfo!=null&&activeNetworkInfo.isAvailable()) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
}
