package jiuri.com.dagger2demo.retrofit.RxjavaRetrofitDownLoadFile;

import java.io.IOException;

import jiuri.com.dagger2demo.api_base.Constant;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user103 on 2017/9/6.
 */

public class RxRetrofitDownLoad {


    public static <T>T getRetrofitService(final RxRetrofitCallback callback,Class<T> tClass) {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        /**
         * 下面主要是添加一个 拦截器 把我萌的 call back 放到里面去
         */
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
                //将ResponseBody转换成我们需要的FileResponseBody
                return response.newBuilder().body(new RxFileResponseBody(response.body(), callback)).build();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_DOWN)
                .client(clientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//这个加不加  都可以  无所谓。
                .build();

        T service = retrofit.create(tClass);
        return service;
    }
}
