import com.google.common.base.Stopwatch;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class AppDemoRxJava001 {

    public static void main(String[] args) {

        Stopwatch stopwatch = Stopwatch.createStarted();

//        Observable.range(1, 10)
//                .subscribe(AppDemoRxJava001::someShowData);

        Observable.range(1, 10)

                .map(AppDemoRxJava001::doubleValue)
                .subscribeOn(Schedulers.computation())
                //.toBlocking()
                .subscribe(AppDemoRxJava001::someShowData);

        stopwatch.stop();
        System.out.println(stopwatch);

    }


    private static void someShowData(int value){
        System.out.println(Thread.currentThread().getName() + " : " + value);
    }

    private static int doubleValue(int value){
        System.out.println(Thread.currentThread().getName() + " : doubling value");
        return value * 2;
    }

}
