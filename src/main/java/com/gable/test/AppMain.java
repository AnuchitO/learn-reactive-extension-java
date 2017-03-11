package com.gable.test;

import org.javatuples.Pair;
import rx.Observable;

import java.util.*;


public class AppMain {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();
        employeeService.getAllEmployee()
                .subscribe(
                        pair -> System.out.println(pair.getValue0().getName()+ " " + pair.getValue1().size())
                );


    }
}

class EmployeeService {
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    private AddressRepository addressRepository = new AddressRepository();

    public Observable<Pair<Employee, List<Address>>> getAllEmployee() {
       return employeeRepository.list()
               .flatMap(
                       employee -> addressRepository.getByEmployeeId(employee.getId())
                       .toList()
                       .map(addresses -> Pair.with(employee, addresses))
               );
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

    public Map<Long, List<Address>> getAddresses() {
        return addresses;
    }

    public Observable<Address> getByEmployeeId(Long id) {
        return Observable.from(addresses.get(id));
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