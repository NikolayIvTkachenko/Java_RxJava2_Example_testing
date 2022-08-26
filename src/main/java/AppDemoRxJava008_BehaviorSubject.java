import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public class AppDemoRxJava008_BehaviorSubject {

    public static void main(String[] args) {

        /*
            https://habr.com/ru/post/270023/
            BehaviorSubject хранит только последнее значение.
            Это то же самое, что и ReplaySubject, но с буфером размером
            1. Во время создания ему может быть присвоено начальное значение, таким образом гарантируя,
            что данные всегда будут доступны новым подписчикам.

         */

        System.out.println("== V1 ===");
        BehaviorSubject<Integer> s = BehaviorSubject.create();
        s.onNext(0);
        s.onNext(1);
        s.onNext(2);
        s.subscribe(v -> System.out.println("Late: " + v));
        s.onNext(3);


        //Начальное значение(не работает сейчас)
//        BehaviorSubject<Integer> s = BehaviorSubject.create(0);
//        s.subscribe(v -> System.out.println(v));
//        s.onNext(1);


        System.out.println("== V2 ===");
        BehaviorSubject<Integer> source = BehaviorSubject.create();
        // Получит 1, 2, 3, 4 and onComplete
        source.subscribe(getFirstObserver());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        // Получит 3(последний элемент) и 4(последующие элементы) и onComplete
        source.subscribe(getSecondObserver());
        source.onNext(4);
        source.onComplete();


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
