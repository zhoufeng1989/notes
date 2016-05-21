package main.java;

/**
 * Created by zhoufeng on 16/5/21.
 */
public class ParameterTest {
    public static void main(String[] args) {
        int y = 10;
        System.out.printf("before double, y's value is %d.\n", y);
        doubleValue(y);
        System.out.printf("after double, y's value is %d.\n", y);

        Employee x = new Employee("Jobs", 1000.0);
        System.out.printf("before increase, x's salary is %f\n", x.getSalary());
        increaseSalary(x);
        System.out.printf("after increase, x's salary is %f\n", x.getSalary());

        Employee a = new Employee("Alice", 500.0);
        Employee b = new Employee("Bob", 600.0);
        System.out.printf("before swap, a is %s, b is %s\n", a, b);
        swap(a, b);
        System.out.printf("after swap, a is %s, b is %s\n", a, b);

    }

    public static void doubleValue(int x) {
        x = x * 2;
        System.out.printf(" In function, x's value is %d\n", x);
    }

    public static void swap(Employee x, Employee y) {
        System.out.printf(" Before swap, x's value is %s, y's value is %s\n", x, y);
        Employee temp = x;
        x = y;
        y = temp;
        System.out.printf(" After swap, x's value is %s, y's value is %s\n", x, y);
    }

    public static void increaseSalary(Employee x) {
        System.out.printf(" In function, before increase, salary is %f\n", x.getSalary());
        x.increaseSalary(100);
        System.out.printf(" In function, after increase, salary is %f\n", x.getSalary());
    }
}

class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public String toString() {
        return "Employee: name: " + name + ", salary: " + salary;
    }

    public void increaseSalary(double x) {
        this.salary = this.salary + x;
    }
}
