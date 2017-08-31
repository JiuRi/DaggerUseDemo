package jiuri.com.dagger2demo.retrofit;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user103 on 2017/8/4.
 */

public interface ApiService {
    //   http://wthrcdn.etouch.cn/weather_mini?city=深圳
    @GET("weather_mini")
   // Observable<String> getTianQi(@Query("city")String city);
    Observable<String> getTianQi(@Query("city")String city);
    @GET("aaaa/city={city}")
    Observable<String> getDate(@Path("city") String city);
    //http://mobilenews.mingpao.com/dat/MobileINews3Test/latest/category_content/category_tc.xml
    @GET("dat/MobileINews3Test/latest/category_content/{end}")
    Observable<String> getFengLei(@Path("end")String end);
    //http://ovj11j8i9.bkt.clouddn.com/out.apatch
    @GET("out.{name}")
    Observable<ResponseBody> downLoad(@Path("name")String name);
}
