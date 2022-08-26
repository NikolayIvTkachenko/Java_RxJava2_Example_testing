import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.AsyncSubject;

public class AppDemoRxJava009_AsyncSubject {

    public static void main(String[] args) {

        /*
            https://habr.com/ru/post/270023/
            AsyncSubject также хранит последнее значение.
            Разница в том, что он не выдает данных до тех пока не завершится последовательность.
            Его используют, когда нужно выдать единое значение и тут же завершиться.

         */

        System.out.println("== V1 ===");
        AsyncSubject<Integer> s = AsyncSubject.create();
        s.subscribe(v -> System.out.println(v));
        s.onNext(0);
        s.onNext(1);
        s.onNext(2);
        s.onComplete();

        System.out.println("== V2 ===");
        //https://medium.com/@poudanen/%D0%BF%D0%BE%D0%BD%D0%B8%D0%BC%D0%B0%D0%BD%D0%B8%D0%B5-rxjava-subject-publish-replay-behavior-%D0%B8-async-subject-35ad50cd1064
        AsyncSubject<Integer> source = AsyncSubject.create();
        // Получит только 4 и onComplete
        source.subscribe(getFirstObserver());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        // Тоже получит только 4 и onComplete
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
