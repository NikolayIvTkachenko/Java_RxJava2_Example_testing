import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class AppDemoRxJava004_PublishSubject {

    public static void main(String[] args) {

        //https://russianblogs.com/article/82351513567/


        /*
            https://habr.com/ru/post/270023/
            PublishSubject – самая простая реализация Subject. Когда данные передаются в PublishSubject,
            он выдает их всем подписчикам, которые подписаны на него в данный момент.

            Как мы видим, 1 не была напечатана из-за того, что мы не были подписаны в момент когда она была передана.
            После того как мы подписались, мы начали получать все значения поступающие в subject.

            Здесь мы впервые используем метод subscribe, так что стоит уделить этому внимание.
            В данном случае мы используем перегруженную версию, которая принимает один объект класса Function,
            отвечающий за onNext. Эта функция принимает значение типа Integer и ничего не возвращает.
             Функции, которые ничего не возвращают также называются actions. Мы можем передать эту функцию следующими способами:
            Предоставить объект класса Action1<Integer>
            Неявно создать таковой используя лямбда-выражение
            Передать ссылку на существующий метод с соответствующей сигнатурой. В данном случае,
            System.out::println имеет перегруженную версию, которая принимает Object,
            поэтому мы передаем ссылку на него. Таким образом, подписка позволяет нам печатать
            в основной поток вывода все поступающие в Subject числа.

         */

        System.out.println("== V1 ===");
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.onNext(1);
        subject.subscribe(System.out::println);
        subject.onNext(2);
        subject.onNext(3);
        subject.onNext(4);

        System.out.println("== V2 ===");
        PublishSubject<Integer> source = PublishSubject.create();
        // Получит 1, 2, 3, 4 и onComplete
        source.subscribe(getFirstObserver());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        // Получит 4 и onComplete для следующего наблюдателя тоже.source.subscribe(getSecondObserver());
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



