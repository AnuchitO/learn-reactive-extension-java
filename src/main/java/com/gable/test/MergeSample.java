package com.gable.test;

import rx.Observable;
import rx.Subscriber;
import rx.observers.Subscribers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MergeSample {
    public static void main(String[] args) throws InterruptedException {
        Observable<String> obs1 = Observable.interval(3, TimeUnit.MICROSECONDS)
                .take(5)
                .map(l -> "Obs1_" + l);

        Observable<String> obs2 = Observable.interval(1, TimeUnit.MICROSECONDS)
                .take(10)
                .map(l -> "Obs2_" + l);

        Observable<String> concatObs = Observable.concat(obs1, obs2);
        Observable<String> mergeObs = Observable.merge(obs1, obs2);

        CountDownLatch latch = new CountDownLatch(1);

        concatObs.subscribe(Subscribers.<Object>create(
                v -> System.out.print(v + " "),
                Throwable::printStackTrace,
                () -> latch.countDown()
        ));

        latch.await();
        System.out.println("");
        System.out.println("xxxxx");

        CountDownLatch latch2 = new CountDownLatch(1);

        mergeObs.subscribe(Subscribers.<Object>create(
                v -> System.out.print(v + " "),
                Throwable::printStackTrace,
                () -> latch2.countDown()
        ));
        latch2.await();
        System.out.println("");
        System.out.println("end");
    }
}
