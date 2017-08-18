package jiuri.com.dagger2demo.adapter;

import com.orhanobut.logger.AndroidLogAdapter;

/**
 * Created by user103 on 2017/8/17.
 */

public class LoggerAdapter extends AndroidLogAdapter {
    @Override
    public boolean isLoggable(int priority, String tag) {
        return super.isLoggable(priority, "logger");
    }
}
