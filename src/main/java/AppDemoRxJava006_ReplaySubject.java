import io.reactivex.subjects.ReplaySubject;

public class AppDemoRxJava006_ReplaySubject {

    public static void main(String[] args) {
        //https://habr.com/ru/post/270023/


        ReplaySubject<Integer> s = ReplaySubject.createWithSize(2);
        s.onNext(0);
        s.onNext(1);
        s.onNext(2);
        s.onNext(3);
        s.onNext(4);
        s.onNext(5);
        s.subscribe(v -> System.out.println("Late: " + v));
        s.onNext(6);


    }

}
