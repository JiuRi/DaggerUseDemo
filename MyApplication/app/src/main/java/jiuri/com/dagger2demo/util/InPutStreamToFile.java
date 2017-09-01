package jiuri.com.dagger2demo.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by user103 on 2017/9/1.
 */

public class InPutStreamToFile {
    private static final String TAG = "InPutStreamToFile";
    public static void inputstreamtofile(InputStream ins){
        File file =new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(),"out.apatch");
        Log.e(TAG, "inputstreamtofile: ———————————————————"+file.getAbsolutePath() );
        //   _/storage/emulated/0/out.apatch
        //    /storage/emulated/0/out.apatch
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
