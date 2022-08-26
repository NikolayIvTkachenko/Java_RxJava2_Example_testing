import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

public class AppDemoRxJava010_Subject {

    public static void main(String[] args) {

        //https://habr.com/ru/post/270023/
        //https://medium.com/@poudanen/%D0%BF%D0%BE%D0%BD%D0%B8%D0%BC%D0%B0%D0%BD%D0%B8%D0%B5-rxjava-subject-publish-replay-behavior-%D0%B8-async-subject-35ad50cd1064
        //https://russianblogs.com/article/82351513567/

        /*
            Subject — это своего рода мост или прокси, доступный в некоторых реализациях ReactiveX,
            который действует как наблюдатель(Observer) и наблюдаемый(Observable).
            Так как он является наблюдателем(observer), он может подписаться на один
            и более наблюдаемых(Observables), и потому как он наблюдатель(Observer),
            он может пройти через все элементы, за которыми он наблюдает, повторно передав
            их, а также может излучать(emit) новые элементы.

            Как мы уже упоминали, существуют принципы, которые могут быть не очевидны в коде.
            Один из важнейших заключается в том, что ни одно событие не будет выдано после того,
            как последовательность завершена (onError или onCompleted). Реализация subject’
            уважает эти принципы

         */

        Subject<Integer> s = ReplaySubject.create();
        s.subscribe(v -> System.out.println(v));
        s.onNext(0);
        s.onComplete();
        s.onNext(1);
        s.onNext(2);
    }

}
