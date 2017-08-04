package jiuri.com.dagger2demo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by user103 on 2017/8/4.
 */

public class ToastUtil {
    private static Context mContext;
    public static void  init(Context context){
        mContext=context;
    }
    public static void showToast(String s){
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }
}
