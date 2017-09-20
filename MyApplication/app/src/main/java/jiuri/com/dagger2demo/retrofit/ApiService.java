package jiuri.com.dagger2demo.retrofit;


import jiuri.com.dagger2demo.retrofit.RetrofitDownLoadFile.FileResponseBody;
import jiuri.com.dagger2demo.ui.fragment.meizi.MeiZiBean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
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
    @GET("test.{name}")
    Call<ResponseBody> downLoad(@Path("name")String name);
    @GET("test.{name}")
    Observable<ResponseBody> downLoadb(@Path("name")String name);
    @Streaming
    @GET("test.{name}")
    Call<FileResponseBody> downLoada(@Path("name")String name);
    @GET("weather_mini")
    Call<ResponseBody> getTianQi1(@Query("city")String city);
    @GET("?mpic=1&webp=1&essence=1&content_type=-102&message_cursor=-1&am_longitude=110&am_latitude=120&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&am_loc_time=1489226058493&count=30&min_time=1489205901&screen_width=1450&do00le_col_mode=0&iid=3216590132&device_id=32613520945&ac=wifi&channel=360&aid=7&app_name=joke_essay&version_code=612&version_name=6.1.2&device_platform=android&ssmix=a&device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&uuid=326135942187625&openudid=3dg6s95rhg2a3dg5&manifest_version_code=612&resolution=1450*2800&dpi=620&update_version_code=6120")
    Observable<String> getDuanZi();
    @GET("15/{num}")
    Observable<MeiZiBean> getMeiZi(@Path("num")String num);
}
