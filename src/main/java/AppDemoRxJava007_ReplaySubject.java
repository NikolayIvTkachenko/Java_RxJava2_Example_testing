import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

import java.util.concurrent.TimeUnit;

public class AppDemoRxJava007_ReplaySubject {

    public static void main(String[] args) throws InterruptedException {

        //https://habr.com/ru/post/270023/

        System.out.println("== V1 ===");
        ReplaySubject<Integer> s = ReplaySubject.createWithTime(150, TimeUnit.MILLISECONDS, Schedulers.io());
        s.onNext(0);
        s.onNext(1);
        s.onNext(2);
        Thread.sleep(100);
        s.onNext(3);
        s.onNext(4);
        Thread.sleep(100);
        s.onNext(5);
        s.onNext(6);
        s.onNext(7);
        s.subscribe(v -> System.out.println("Late: " + v));
        s.onNext(8);



        System.out.println("== V2 ===");
        ReplaySubject<Integer> source = ReplaySubject.create();
        // Он получит 1, 2, 3, 4
        source.subscribe(getFirstObserver());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        source.onComplete();
        // Он также получит 1, 2, 3, 4 так как он использует Replay Subject
        source.subscribe(getSecondObserver());



    }

    private static Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("getFirstObserver onSubscribe");
                System.out.println(" First onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {
                System.out.println(" First onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(" First onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println(" First onComplete");
            }
        };
    }

    private static Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("getSecondObserver onSubscribe");
                System.out.println( " Second onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {
                System.out.println(" Second onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println( " Second onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println(" Second onComplete");
            }
        };
    }

}
