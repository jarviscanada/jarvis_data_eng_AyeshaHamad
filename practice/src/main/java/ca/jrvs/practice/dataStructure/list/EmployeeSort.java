package ca.jrvs.practice.dataStructure.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeSort {

  public static void main(String[] args) {
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

    //Compareable
    Collections.sort(employeeList);

    for(Employee emp: employeeList){
      System.out.println(emp.toString());
    }

    //Comparator
    Collections.sort(employeeList,Employee.EmployeeNameComparator);

    for(Employee emp: employeeList){
      System.out.println(emp.toString());
    }

  }

}
