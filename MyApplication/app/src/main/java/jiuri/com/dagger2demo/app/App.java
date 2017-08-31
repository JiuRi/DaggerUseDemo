package jiuri.com.dagger2demo.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.File;
import java.io.IOException;

import jiuri.com.dagger2demo.util.ToastUtil;

/**
 * Created by user103 on 2017/8/4.
 */

public class App extends Application {

    private static final String TAG ="App" ;
    public static Context mApplicationContext;
    private PatchManager mPatchManager;
    private static final String APATCH_PATH = "/out.apatch";

    private static final String DIR = "apatch";//补丁文件夹

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: _______________________启动-----------" );
        mApplicationContext = getApplicationContext();
        ToastUtil.init(this);
        mPatchManager = new PatchManager(this);
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 1).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mPatchManager.init("1.0");
        mPatchManager.loadPatch();
        try {
            // .apatch file path
            String patchFileString = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + APATCH_PATH;
            Log.d(TAG, "onCreate: _________________"+patchFileString);

            mPatchManager.addPatch(patchFileString);


            //复制且加载补丁成功后，删除下载的补丁
            File f = new File(this.getFilesDir(), DIR + APATCH_PATH);

            if (f.exists()) {
                boolean result = new File(patchFileString).delete();
                if (!result)
                    Log.e(TAG, patchFileString + " delete fail");
            }
        } catch (IOException e) {
            Log.e( TAG, e.toString());
        }


    }

    /**
     * 获取全局的上下文
     * @return
     */
    public static Context getAppContext() {
        return mApplicationContext;
    }

}
