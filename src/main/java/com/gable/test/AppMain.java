package com.gable.test;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

import java.util.*;
import java.util.concurrent.Callable;


public class AppMain {
    public static void main(String[] args) {
//        MyRepository repository = new MyRepository();
    
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

class EmployeeRepository {
    public Observable<Employee> list() {
        return Observable.just(
            new Employee(1L, "user1"),
            new Employee(2L, "user2"),
            new Employee(3L, "user3")
        );
    }
}

class AddressRepository {
    private Map<Long, List<Address>> addresses = new HashMap<>();

    public AddressRepository() {
        addresses.put(1L, Arrays.asList(
                new Address(1L, "34/432", "add1 of emp1"),
                new Address(1L, "432/42", "add2 of emp1")));
        addresses.put(2L, Arrays.asList(
                new Address(2L, "34/67", "add1 of emp2")));
        addresses.put(3L, Arrays.asList(
                new Address(3L, "78/67", "add1 of emp3"),
                new Address(3L, "79/67", "add2 of emp3"),
                new Address(3L, "70/67", "add3 of emp3")));
    }
}

class Employee {
    private Long id;
    private String name;

    public Employee(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Address {
    private Long employeeId;
    private String no;
    private String road;

    public Address(Long employeeId, String no, String road) {
        this.employeeId = employeeId;
        this.no = no;
        this.road = road;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }
}