package pl.edu.agh.mwo.reporter.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Company {

    private List<Employee> employees = new ArrayList<>();
    private static LocalDate oldestDate;
    private static LocalDate newestDate;


    public Company(List<Employee> employees) {
        this.employees = employees;
    }

    public Company() {
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public static LocalDate getOldestDate() {
        return oldestDate;
    }

    public static void setOldestDate(LocalDate oldestDate) {
        Company.oldestDate = oldestDate;
    }

    public static LocalDate getNewestDate() {
        return newestDate;
    }

    public static void setNewestDate(LocalDate newestDate) {
        Company.newestDate = newestDate;
    }

    public void addEmployee(Employee employee) {
        if (!this.employees.contains(employee))
            this.employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }

    public Employee getEmployeeByName(String firstName, String lastName) {
        Employee testEmployee = new Employee(firstName, lastName);
        for (Employee employee : employees) {
            if (employee.equals(testEmployee))
                return employee;
        }
        return null;
    }
}
