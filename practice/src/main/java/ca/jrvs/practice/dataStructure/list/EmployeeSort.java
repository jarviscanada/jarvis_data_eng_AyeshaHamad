package ca.jrvs.practice.dataStructure.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeSort {

  public static void main(String[] args) {
    //list
    List<Employee> employeeList = new ArrayList<>(10);

    employeeList.add(new Employee(1, "Zunairah", 50, 5000));
    employeeList.add(new Employee(2, "Ayesha", 20, 2500));
    employeeList.add(new Employee(3, "Mike", 30, 1500));
    employeeList.add(new Employee(4, "Edward", 40, 3500));
    employeeList.add(new Employee(5, "David", 20, 1000));
    employeeList.add(new Employee(6, "Jones", 19, 25));
    employeeList.add(new Employee(7, "Hamad", 26, 100));
    employeeList.add(new Employee(8, "Yunus", 18, 5000));
    employeeList.add(new Employee(9, "Elnaz", 21, 3900));
    employeeList.add(new Employee(10, "Tom", 49, 10));

    //array for later use
    Employee[] empArray = new Employee[employeeList.size()];
    employeeList.toArray(empArray);

    //Comparable
    Collections.sort(employeeList);
    System.out.println("Comparable sort");
    for(Employee emp: employeeList){
      System.out.println(emp.toString());
    }

    //Comparator
    Collections.sort(employeeList,Employee.EmployeeNameComparator.reversed());
    System.out.println("Comparator sort");
    for(Employee emp: employeeList){
      System.out.println(emp.toString());
    }

    //sorting array of custom objects
    System.out.println("Array sort using comparable");
    Arrays.sort(empArray);
    for(Employee emp : empArray){
      System.out.println(emp.toString());
    }

    System.out.println("Array sort using comparator");
    Arrays.sort(empArray, Employee.EmployeeNameComparator);
    for(Employee emp : empArray){
      System.out.println(emp.toString());
    }
  }

}
