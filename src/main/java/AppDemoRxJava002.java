import com.google.common.collect.Lists;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AppDemoRxJava002 {

    public static void main(String[] args) {

        final List<String> items = Lists.newArrayList("a", "b", "c", "d", "e", "f");



        System.out.println("== flatMap ===");

        final TestScheduler schedulerFlatMap = new TestScheduler();
        Observable.fromIterable(items)
                .flatMap( s -> {
                    final int delay = new Random().nextInt(10);
                    return Observable.just(s + "x")
                            .delay(delay, TimeUnit.SECONDS, schedulerFlatMap);
                })
                //.toList()
                .doOnNext(System.out::println)
                .subscribe();

        schedulerFlatMap.advanceTimeBy(1, TimeUnit.MINUTES);




        System.out.println("== switchMap ===");

        final TestScheduler schedulerSwitchMap = new TestScheduler();

        Observable.fromIterable(items)
                .switchMap( s -> {
                    final int delay = new Random().nextInt(10);
                    return Observable.just(s + "x")
                            .delay(delay, TimeUnit.SECONDS, schedulerSwitchMap);
                })
                //.toList()
                .doOnNext(System.out::println)
                .subscribe();

        schedulerSwitchMap.advanceTimeBy(1, TimeUnit.MINUTES);



        System.out.println("== concatMap ===");

        final TestScheduler schedulerConcatMap = new TestScheduler();

        Observable.fromIterable(items)
                .concatMap( s -> {
                    final int delay = new Random().nextInt(10);
                    return Observable.just(s + "x")
                            .delay(delay, TimeUnit.SECONDS, schedulerConcatMap);
                })
                //.toList()
                .doOnNext(System.out::println)
                .subscribe();

        schedulerConcatMap.advanceTimeBy(1, TimeUnit.MINUTES);



        System.out.println("== flatMap and concatMap ===");
        System.out.println("== flatMap  ===");
        final TestScheduler scheduler1 = new TestScheduler();
        final TestScheduler scheduler2 = new TestScheduler();

        Observable.fromIterable(items)
                .flatMap(s -> Observable.just(s + "x")
                        .delay(5, TimeUnit.SECONDS, scheduler1)
                        .doOnNext(str -> System.out.print(scheduler1.now(TimeUnit.SECONDS) + " ")))
                //.toList()
                .doOnNext(strings -> System.out.println("\nEND:" + scheduler1.now(TimeUnit.SECONDS)))
                .subscribe();

        scheduler1.advanceTimeBy(1, TimeUnit.MINUTES);

        System.out.println("== concatMap ===");
        Observable.fromIterable(items)
                .concatMap(s -> Observable.just(s + "x")
                        .delay(5, TimeUnit.SECONDS, scheduler2)
                        .doOnNext(str -> System.out.print(scheduler2.now(TimeUnit.SECONDS) + " ")))
                //.toList()
                .doOnNext(strings -> System.out.println("\nEND:" + scheduler2.now(TimeUnit.SECONDS)))
                .subscribe();

        scheduler2.advanceTimeBy(1, TimeUnit.MINUTES);

    }

}
