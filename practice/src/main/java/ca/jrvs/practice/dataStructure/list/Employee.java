package ca.jrvs.practice.dataStructure.list;

import java.util.Comparator;
import java.util.Objects;

public class Employee implements Comparable{

  private int id;
  private String name;
  private int age;
  private long salary;

  public Employee(int id, String name, int age, long salary) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.salary = salary;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public long getSalary() {
    return salary;
  }

  public void setSalary(long salary) {
    this.salary = salary;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employee employee = (Employee) o;
    return getId() == employee.getId() && getAge() == employee.getAge()
        && getSalary() == employee.getSalary() && getName().equals(employee.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getAge(), getSalary());
  }

  @Override
  public String toString() {
    return "Employee{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", age=" + age +
        ", salary=" + salary +
        '}';
  }

  @Override
  public int compareTo(Object o) {
    int compareAge = ((Employee) o).getAge();
    //ascending order
    return this.age - compareAge;
  }

  public static Comparator<Employee> EmployeeNameComparator = new Comparator<Employee>() {

    public int compare(Employee emp1, Employee emp2) {

      String emp1Name = emp1.getName().toUpperCase();
      String emp2Name = emp2.getName().toUpperCase();

      //ascending order
      return emp1Name.compareTo(emp2Name);

      //descending order
      //return emp2Name.compareTo(emp1Name);
    }

  };

  public static Comparator<Employee> EmployeeAgeComparator = new Comparator<Employee>() {
    @Override
    public int compare(Employee employee, Employee employee2) {
      Integer employeeAge = employee.getAge();
      Integer employee2Age = employee2.getAge();

      return employeeAge.compareTo(employee2Age);
    }
  };

  //replced with lambda
  /*public static Comparator<Employee> FruitNameComparator = (fruit1, fruit2) -> {

    String emp1Name = emp1.getName().toUpperCase();
    String emp2Name = emp2.getName().toUpperCase();

    //ascending order
    return emp1Name.compareTo(emp2Name);

    //descending order
    //return emp2Name.compareTo(emp1Name);
  };*/

}

