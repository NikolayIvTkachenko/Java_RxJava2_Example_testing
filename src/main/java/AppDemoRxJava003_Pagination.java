import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.subjects.BehaviorSubject;

import java.util.Arrays;
import java.util.List;

public class AppDemoRxJava003_Pagination {

    public static void main(String[] args) {

        //https://itsobes.ru/AndroidSobes/kakie-byvaiut-subjects-v-rxjava/


        Observable<Response> call = createPaginatedCall();

        BehaviorSubject<Observable<Response>> subject = BehaviorSubject.create(); //call
        subject.onNext(call);

        Observable<List<String>> pages = subject.flatMap(or -> or).map(r -> {
            Observable<Response> next = r.next();
            if (next != null) {
                subject.onNext(next);
            } else {
                subject.onComplete();
            }
            return r.data();
        });

        // Verify:
        pages.subscribe(
                strings -> {
                    System.out.println(strings);
                }
        );
    }


    private static Observable<Response> createPaginatedCall() {
        List<String> data1 = Arrays.asList("One", "Two", "Three");
        List<String> data2 = Arrays.asList("Four", "Five", "Six");
        List<String> data3 = Arrays.asList("Seven", "Eight", "Nine");
        Response response3 = new DemoResponse(data3, null);
        Response response2 = new DemoResponse(data2, Observable.just(response3));
        Response response1 = new DemoResponse(data1, Observable.just(response2));
        return Observable.just(response1);
    }

    static class DemoResponse implements Response {
        private final List<String> data;
        private final Observable<Response> next;

        DemoResponse(List<String> data, Observable<Response> next) {
            this.data = data;
            this.next = next;
        }

        @Override public List<String> data() {
            return data;
        }

        @Override public Observable<Response> next() {
            return next;
        }
    }
    interface Response {
        List<String> data();
        Observable<Response> next();
    }

}


