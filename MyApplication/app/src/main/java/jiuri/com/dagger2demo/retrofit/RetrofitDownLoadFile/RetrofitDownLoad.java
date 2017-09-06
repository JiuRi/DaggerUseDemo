package jiuri.com.dagger2demo.retrofit.RetrofitDownLoadFile;

import java.io.IOException;

import jiuri.com.dagger2demo.api_base.Constant;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user103 on 2017/9/6.
 */

public class RetrofitDownLoad  {


    public static <T>T getRetrofitService(final RetrofitCallback callback,Class<T> tClass) {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        /**
         * 下面主要是添加一个 拦截器 把我萌的 call back 放到里面去
         */
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                okhttp3.Response response = chain.proceed(chain.request());
                //将ResponseBody转换成我们需要的FileResponseBody
                return response.newBuilder().body(new FileResponseBody(response.body(), callback)).build();
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_DOWN)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())//这个加不加  都可以  无所谓。
                .build();

        T service = retrofit.create(tClass);
        return service;
    }
}
