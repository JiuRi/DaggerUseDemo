package jiuri.com.dagger2demo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.FaceDetector;
import com.iflytek.cloud.FaceRequest;
import com.iflytek.cloud.RequestListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.util.Accelerometer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import jiuri.com.dagger2demo.R;
import jiuri.com.dagger2demo.util.FaceUtil;
import jiuri.com.dagger2demo.util.ToastUtil;

/**
 * Created by user103 on 2017/9/8.
 */

public class LoginActivity extends AppCompatActivity {
    private boolean mStopTrack;
    private Camera mCamera;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private Matrix mScaleMatrix = new Matrix();
    private int PREVIEW_WIDTH = 640;
    private int PREVIEW_HEIGHT = 480;
    private static final String TAG = "LoginActivity";
    private Bitmap mImage = null;
    private byte[] mImageData = null;
    // authid为6-18个字符长度，用于唯一标识用户
    private Toast mToast;
    private byte[] nv21;
    private byte[] buffer;

    // 拍照得到的照片文件
    private File mPictureFile;
    // FaceRequest对象，集成了人脸识别的各种功能
    private FaceRequest mFaceRequest;
    private SurfaceView mPreviewSurface;
    // 加速度感应器，用于获取手机的朝向
    private Accelerometer mAcc;
    // FaceDetector对象，集成了离线人脸识别：人脸检测、视频流检测功能
    private FaceDetector mFaceDetector;
    private View mLine;
    private Button mButton;
    private TranslateAnimation mTranslateAnimation;
    private int mType;
    private String mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 0);
        mId = intent.getStringExtra("idd");
        Log.e(TAG, "onCreate: __________________"+mType+"          "+mId );
        mButton = (Button) findViewById(R.id.button);
        mPreviewSurface = (SurfaceView) findViewById(R.id.sfv_preview);
        mLine = (View) findViewById(R.id.line);
        nv21 = new byte[PREVIEW_WIDTH * PREVIEW_HEIGHT * 2];
        buffer = new byte[PREVIEW_WIDTH * PREVIEW_HEIGHT * 2];
        mPreviewSurface.getHolder().addCallback(mPreviewCallback);
        mPreviewSurface.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mAcc = new Accelerometer(LoginActivity.this);
        mFaceDetector = FaceDetector.createDetector(LoginActivity.this, null);
        mFaceRequest = new FaceRequest(this);
        setSurfaceSize();
        if (mType == 0) {
            mButton.setText("正在登陆。。。");
        } else {
            mButton.setText("正在注册。。。");
        }
        mTranslateAnimation = new TranslateAnimation(50f, 50f, 50f, 1500f);
        mTranslateAnimation.setDuration(800);
        mTranslateAnimation.setRepeatCount(Integer.MAX_VALUE);
        mTranslateAnimation.setRepeatMode(Animation.REVERSE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mAcc) {
            mAcc.start();
        }

        ;//设置反方向执行
        mStopTrack = true;
        mLine.startAnimation(mTranslateAnimation);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mStopTrack) {
                    if (null == nv21) {
                        continue;
                    }
                    synchronized (nv21) {
                        System.arraycopy(nv21, 0, buffer, 0, nv21.length);
                    }

                    // 获取手机朝向，返回值0,1,2,3分别表示0,90,180和270度
                    int direction = Accelerometer.getDirection();
                    boolean frontCamera = (Camera.CameraInfo.CAMERA_FACING_FRONT == mCameraId);
                    // 前置摄像头预览显示的是镜像，需要将手机朝向换算成摄相头视角下的朝向。
                    // 转换公式：a' = (360 - a)%360，a为人眼视角下的朝向（单位：角度）
                    if (frontCamera) {
                        // SDK中使用0,1,2,3,4分别表示0,90,180,270和360度
                        direction = (4 - direction) % 4;
                    }

                    if (mFaceDetector == null) {
                        /**
                         * 离线视频流检测功能需要单独下载支持离线人脸的SDK
                         * 请开发者前往语音云官网下载对应SDK
                         */
                        // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
                        showTip("创建对象失败，请确认 libmsc.so 放置正确，\n 且有调用 createUtility 进行初始化");
                        break;
                    }

                    String result = mFaceDetector.trackNV21(buffer, PREVIEW_WIDTH, PREVIEW_HEIGHT, mType, direction);
                    Log.d(TAG, "result:" + result);
                    Gson gson = new Gson();
                    FaceBeanBean faceBeanBean = gson.fromJson(result, FaceBeanBean.class);
                    if (faceBeanBean.getFace() != null && faceBeanBean.getFace().size() > 0) {
                        mStopTrack = false;
                        Log.e(TAG, "run: _________________________________有人脸 显示");
                        mCamera.takePicture(null, null, new Camera.PictureCallback() {
                            @Override
                            public void onPictureTaken(byte[] data, Camera camera) {

                                Bitmap mImage = BitmapFactory.decodeByteArray(data, 0, data.length);

                                mImage = FaceUtil.rotateImage(270, mImage);

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                                //可根据流量及网络状况对图片进行压缩
                                mImage.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                                mImageData = baos.toByteArray();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mType==0){
                                            nianZhen();
                                        }
                                        else {
                                            regist();
                                        }



                                    }
                                });

                            }
                        });
                        //  mAcc.stop();
                    }

                }
            }
        }).start();
    }

    private void setSurfaceSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = (int) (width * PREVIEW_WIDTH / (float) PREVIEW_HEIGHT);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        mPreviewSurface.setLayoutParams(params);

    }


    public void nianZhen() {
        ;
        if (TextUtils.isEmpty(mId)) {
            showTip("authid不能为空");
            return;
        }

        if (null != mImageData) {
            // 设置用户标识，格式为6-18个字符（由字母、数字、下划线组成，不得以数字开头，不能包含空格）。
            // 当不设置时，云端将使用用户设备的设备ID来标识终端用户。
            mFaceRequest.setParameter(SpeechConstant.AUTH_ID, mId);
            mFaceRequest.setParameter(SpeechConstant.WFR_SST, "verify");
            int i = mFaceRequest.sendRequest(mImageData, mRequestListener);
        } else {
            showTip("请选择图片后再验证");
        }
    }
    public  void regist(){
        if (TextUtils.isEmpty(mId)) {
            showTip("authid不能为空");
            return;
        }

        if (null != mImageData) {
            // 设置用户标识，格式为6-18个字符（由字母、数字、下划线组成，不得以数字开头，不能包含空格）。
            // 当不设置时，云端将使用用户设备的设备ID来标识终端用户。
            mFaceRequest.setParameter(SpeechConstant.AUTH_ID, mId);
            mFaceRequest.setParameter(SpeechConstant.WFR_SST, "reg");
            int  r = mFaceRequest.sendRequest(mImageData, mRequestListener);
        } else {
            ToastUtil.showToast("请选择图片后再注册");
        }
    }

    private SurfaceHolder.Callback mPreviewCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            closeCamera();
            Log.e(TAG, "surfaceDestroyed: ________________closeCamera");
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            openCamera();
            Log.e(TAG, "surfaceDestroyed: ________________openCamera");
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            mScaleMatrix.setScale(width / (float) PREVIEW_HEIGHT, height / (float) PREVIEW_WIDTH);

            Log.e(TAG, "surfaceDestroyed: ________________surfaceChanged");
        }
    };

    private void closeCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private void openCamera() {
        if (null != mCamera) {
            Log.e(TAG, "openCamera: ————————————————————————");
            return;
        }

        // 只有一个摄相头，打开后置
        if (Camera.getNumberOfCameras() == 1) {
            Log.e(TAG, "openCamera: ————————————————————————1");
            mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }

        try {
            Log.e(TAG, "openCamera: ————————————————————————2");
            mCamera = Camera.open(mCameraId);
            if (Camera.CameraInfo.CAMERA_FACING_FRONT == mCameraId) {
                Log.e(TAG, "openCamera: ————————————————————————3前置摄像头已开启，点击可切换");

            } else {
                Log.e(TAG, "openCamera: ————————————————————————后置摄像头已开启，点击可切换");

            }
        } catch (Exception e) {
            e.printStackTrace();
            closeCamera();
            return;
        }

        Camera.Parameters params = mCamera.getParameters();
        params.setPreviewFormat(ImageFormat.NV21);
        params.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
        mCamera.setParameters(params);

        // 设置显示的偏转角度，大部分机器是顺时针90度，某些机器需要按情况设置
        mCamera.setDisplayOrientation(90);
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {

            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                System.arraycopy(data, 0, nv21, 0, data.length);
            }
        });

        try {
            mCamera.setPreviewDisplay(mPreviewSurface.getHolder());
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mFaceDetector == null) {
            /**
             * 离线视频流检测功能需要单独下载支持离线人脸的SDK
             * 请开发者前往语音云官网下载对应SDK
             */
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            showTip("创建对象失败，请确认 libmsc.so 放置正确，\n 且有调用 createUtility 进行初始化");
        }
    }


    //验证
    private void verify(JSONObject obj) throws JSONException {
        int ret = obj.getInt("ret");
        if (ret != 0) {
            showTip("验证失败");
            return;
        }
        if ("success".equals(obj.get("rst"))) {
            if (obj.getBoolean("verf")) {
                mTranslateAnimation.cancel();
                setResult(100,null);
                finish();
                showTip("通过验证，欢迎回来！");
            } else {
                showTip("验证不通过");
            }
        } else {
            showTip("验证失败");
        }
    }

    //注册
    private void register(JSONObject obj) throws JSONException {
        int ret = obj.getInt("ret");
        if (ret != 0) {
            showTip("注册失败");
            mTranslateAnimation.cancel();
            setResult(101,null);
            finish();
            return;
        }
        if ("success".equals(obj.get("rst"))) {
            showTip("注册成功");
        } else {
            showTip("注册失败");
        }
    }

    // 检测
    private void detect(JSONObject obj) throws JSONException {
        int ret = obj.getInt("ret");
        if (ret != 0) {
            showTip("检测失败");
            return;
        }

        if ("success".equals(obj.get("rst"))) {
            JSONArray faceArray = obj.getJSONArray("face");

            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStrokeWidth(Math.max(mImage.getWidth(), mImage.getHeight()) / 100f);

            Bitmap bitmap = Bitmap.createBitmap(mImage.getWidth(),
                    mImage.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(mImage, new Matrix(), null);
            for (int i = 0; i < faceArray.length(); i++) {
                float x1 = (float) faceArray.getJSONObject(i)
                        .getJSONObject("position").getDouble("left");
                float y1 = (float) faceArray.getJSONObject(i)
                        .getJSONObject("position").getDouble("top");
                float x2 = (float) faceArray.getJSONObject(i)
                        .getJSONObject("position").getDouble("right");
                float y2 = (float) faceArray.getJSONObject(i)
                        .getJSONObject("position").getDouble("bottom");
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(new Rect((int) x1, (int) y1, (int) x2, (int) y2),
                        paint);
            }

            mImage = bitmap;
            ((ImageView) findViewById(R.id.online_img)).setImageBitmap(mImage);
        } else {
            showTip("检测失败");
        }
    }

    // 聚焦
    @SuppressWarnings("rawtypes")
    private void align(JSONObject obj) throws JSONException {
        int ret = obj.getInt("ret");
        if (ret != 0) {
            showTip("聚焦失败");
            return;
        }
        if ("success".equals(obj.get("rst"))) {
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(Math.max(mImage.getWidth(), mImage.getHeight()) / 100f);

            Bitmap bitmap = Bitmap.createBitmap(mImage.getWidth(),
                    mImage.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(mImage, new Matrix(), null);

            JSONArray faceArray = obj.getJSONArray("result");
            for (int i = 0; i < faceArray.length(); i++) {
                JSONObject landmark = faceArray.getJSONObject(i).getJSONObject(
                        "landmark");
                Iterator it = landmark.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    JSONObject postion = landmark.getJSONObject(key);
                    canvas.drawPoint((float) postion.getDouble("x"),
                            (float) postion.getDouble("y"), paint);
                }
            }

            mImage = bitmap;
            ((ImageView) findViewById(R.id.online_img)).setImageBitmap(mImage);
        } else {
            showTip("聚焦失败");
        }
    }

    private RequestListener mRequestListener = new RequestListener() {

        @Override
        public void onEvent(int eventType, Bundle params) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            try {
                String result = new String(buffer, "utf-8");
                Log.d("FaceDemo", result);

                JSONObject object = new JSONObject(result);
                String type = object.optString("sst");
                if ("reg".equals(type)) {
                    register(object);
                } else if ("verify".equals(type)) {
                    verify(object);
                } else if ("detect".equals(type)) {
                    detect(object);
                } else if ("align".equals(type)) {
                    align(object);
                }
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO: handle exception
            }
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error != null) {
                switch (error.getErrorCode()) {
                    case ErrorCode.MSP_ERROR_ALREADY_EXIST:
                        showTip("authid已经被注册，请更换后再试");
                        break;

                    default:
                        showTip(error.getPlainDescription(true));
                        break;
                }
            }
        }
    };

    private void showTip(final String str) {
        Log.d(TAG, "showTip: _________________________" + str);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeCamera();
        if (null != mAcc) {
            mAcc.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mFaceDetector) {
            // 销毁对象
            mFaceDetector.destroy();
        }
    }


}
