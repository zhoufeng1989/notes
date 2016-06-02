package main.java;

import java.util.Objects;

/**
 * Created by zhoufeng on 16/5/24.
 */
public class Employee implements Comparable<Employee> {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void increaseSalary(double x) {
        this.salary = this.salary + x;
    }

    @Override public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (this.getClass() != otherObject.getClass()) return false;
        Employee other = (Employee) otherObject;
        return Objects.equals(name, other.name) && salary == other.salary;
    }

    public int hashCode() {
        return Objects.hash(name, salary);
    }

    public String toString() {
        return getClass().getName() + "[name=" + name + ",salary=" + salary + "]";
    }

    public int compareTo(Employee other) {
        return Double.compare(this.salary, other.salary);
    }

    public Employee clone() throws CloneNotSupportedException {
        return (Employee) super.clone();
    }

}

class Manager extends Employee implements Cloneable {
    private double bonus;

    public Manager(String name, double salary, double bonus) {
        super(name, salary);
        this.bonus = bonus;
    }

    @Override public boolean equals(Object otherObject) {
        if (!super.equals(otherObject)) return false;
        Manager other = (Manager)otherObject;
        return bonus == other.bonus;
    }

    public int hashCode() {
        return super.hashCode() + Objects.hash(bonus);
    }

    public String toString() {
        return super.toString() + "[bonus=" + bonus + "]";
    }

    public int compareTo(Employee other) {
        if (other.getClass() != this.getClass()) {
            return 1;
        }
        Manager manager = (Manager)other;
        return Double.compare(this.getSalary() + this.bonus, manager.getSalary() + manager.bonus);
    }

    public Manager clone() throws CloneNotSupportedException {
        return (Manager) super.clone();
    }
}
