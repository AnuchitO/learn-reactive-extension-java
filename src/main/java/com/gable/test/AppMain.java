package com.gable.test;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

import java.util.IntSummaryStatistics;
import java.util.concurrent.Callable;


public class AppMain {
    public static void main(String[] args) {
        MyRepository repository = new MyRepository();
        repository.setValue("value1");

//        repository.getValueObs()
//                .map(s -> s.length())
//                .map(value -> value * value)
//                .map(result -> "result " + result);

//        Observable.just("12", "123", "1234")
        String[] values = new String[]{"12", "123", "1234", "12345"};
        Observable.from(values)
                .map(check -> {
                    if(check.equals("1234")){
                        Long nn = System.nanoTime();
                        throw new RuntimeException("error:"+nn.toString());
                    }else{
                        return check;
                    }
                })
                .map(value -> value.length())
                .map(length -> length * length)
                .map(multiply -> "result " + multiply)
                .subscribe(
                        value -> {
                            Long nn = System.nanoTime();
                            System.out.println(value+":"+ nn.toString());
                        },
                        throwable -> System.err.println("error : "+ throwable.getMessage()),
                        () -> System.out.println("complete")
                );


//        Subscription subscription = valueObs.subscribe(
//                System.out::println,
//                Throwable::printStackTrace,
//                () -> System.out.println("complete"));


    }
}

class MyRepository {
    private String value;

    public String getValue() {
        return value;
    }
//    Observable is on duty when has subscripber
    public Observable<String> getValueObs() {
//        return Observable.just(getValue());

//        return Observable.defer(new Func0<Observable<String>>() {
//            @Override
//            public Observable<String> call() {
//                System.out.println("== defer ==");
//                return  Observable.just(getValue());
//            }
//        });

        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getValue();
            }
        });

    }

    public void setValue(String value) {
        this.value = value;
    }
}
