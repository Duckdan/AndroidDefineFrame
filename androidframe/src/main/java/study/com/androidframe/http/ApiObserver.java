package study.com.androidframe.http;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import study.com.androidframe.http.exception.ApiException;
import study.com.androidframe.http.exception.ExceptionHandle;
import study.com.androidframe.utils.log.LogUtil;

/**
 * 观察者示例
 *
 * @param <T>
 * @param <R>
 */
public abstract class ApiObserver<T, R> implements Observer<T> {

    private Context context;

    public ApiObserver() {
    }

    public ApiObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

        LogUtil.d("ApiSubscriber.onStart()");
        //接下来可以检查网络连接、弹出框处理等操作
        /*if (!NetUtils.isConnected(context)) {

            Toast.makeText(context, "当前网络不可用，请检查网络", Toast.LENGTH_SHORT).show();
            // 一定好主动调用下面这一句,取消本次Subscriber订阅
            if (!isUnsubscribed()) {
                unsubscribe();
            }
            return;
        }*/
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e("ApiSubscriber.throwable =" + e.toString());
        LogUtil.e("ApiSubscriber.throwable =" + e.getMessage());

        if (e instanceof Exception) {
            //访问获得对应的Exception
            onFail(ExceptionHandle.handleException(e));
        } else if (e instanceof ApiException) {
            onFail((ApiException) e);
        } else {
            //将Throwable 和 未知错误的status code返回
            onFail(new ApiException(e, ExceptionHandle.ERROR.UNKNOWN));
        }

    }

    @Override
    public void onNext(T data) {
        if (data == null)
            return;

        BaseResp baseResp = (BaseResp) data;
        BaseResp.Meta meta = baseResp.getMeta();

        if (meta == null || meta.getCode() == 0) {
            R model = (R) baseResp.getData();
            onSuccess(model);
        } else {
            onFail(new ApiException(meta.getMessage(), meta.getCode()));
        }

    }

    @Override
    public void onComplete() {
        LogUtil.d("ApiSubscriber.onCompleted()");
    }

    /**
     * 错误回调
     */
    protected abstract void onFail(ApiException ex);

    /**
     * 成功回调
     *
     * @param data
     */
    public abstract void onSuccess(R data);

}