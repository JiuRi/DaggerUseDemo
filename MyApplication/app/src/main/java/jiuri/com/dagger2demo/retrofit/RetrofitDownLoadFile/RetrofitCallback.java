package jiuri.com.dagger2demo.retrofit.RetrofitDownLoadFile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user103 on 2017/9/6.
 */

public abstract class RetrofitCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if(response.isSuccessful()) {
            onSuccess(call, response);
        } else {
            onFailure(call, new Throwable(response.message()));
        }
    }

    public abstract void onSuccess(Call<T> call, Response<T> response);

    public void onLoading(long total, long progress) {

    }
}