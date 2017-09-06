package jiuri.com.dagger2demo.retrofit.RxjavaRetrofitDownLoadFile;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 扩展OkHttp的请求体，实现上传时的进度提示
 *
 * @param <T>
 */
public final class RxFileResponseBody<T> extends ResponseBody {
    /**
     * 实际请求体
     */
    private ResponseBody mResponseBody;
    /**
     * 下载回调接口
     */
    private RxRetrofitCallback<T> mCallback;
    /**
     * BufferedSource
     */
    private BufferedSource mBufferedSource;

    public RxFileResponseBody(ResponseBody responseBody, RxRetrofitCallback<T> callback) {
        super();
        this.mResponseBody = responseBody;
        this.mCallback = callback;
    }

    @Override
    public BufferedSource source() {

        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    /**
     * 回调进度接口
     * @param source
     * @return Source
     */
    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                mCallback.onLoading(mResponseBody.contentLength(), totalBytesRead);
                return bytesRead;
            }
        };
    }
}
