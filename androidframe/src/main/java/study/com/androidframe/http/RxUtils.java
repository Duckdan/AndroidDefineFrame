package study.com.androidframe.http;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava中调度转换器的重复使用
 */


public class RxUtils {
    private static ObservableTransformer ioToMainThreadSchedulerTransformer = createIOToMainThreadScheduler();

    public RxUtils() {
    }

    private static <T> ObservableTransformer<T, T> createIOToMainThreadScheduler() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(@NonNull Observable observable) {
                return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }

        };
    }

    public static <T> ObservableTransformer<T, T> applyIOToMainThreadSchedulers() {
        return ioToMainThreadSchedulerTransformer;
    }
}

