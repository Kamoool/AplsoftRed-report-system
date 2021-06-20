package pl.edu.agh.mwo.reporter;

import java.time.LocalDate;
import java.util.Arrays;

import pl.edu.agh.mwo.reporter.model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");


        // mockData
        Task task1 = new Task("Aktualizacja danych", LocalDate.of(2020, 1, 8), 7);
        Task task2 = new Task("Przetwarzanie danych", LocalDate.of(2012, 5, 6), 4);
        Task task3 = new Task("Implementacja prototypu", LocalDate.of(2015, 1, 22), 8);
        Task task4 = new Task("Aktualizacja danych", LocalDate.of(2016, 9, 8), 17);

        Project project1 = new Project("Projekt testowy", Arrays.asList(task1, task2));
        Project project2 = new Project("Projekt interfejsu graficznego", Arrays.asList(task3, task4));

        Employee empl1 = new Employee("Jon", "Snow", Arrays.asList(project1, project2));

        Task task5 = new Task("Specyfikacja wymagań", LocalDate.of(2020, 2, 8), 3);
        Task task6 = new Task("Integracja", LocalDate.of(2013, 4, 16), 4);
        Task task7 = new Task("Implementacja prototypu", LocalDate.of(2015, 11, 27), 12.5);
        Task task8 = new Task("Aktualizacja danych", LocalDate.of(2016, 9, 8), 17);

        Project project3 = new Project("Projekt narzędzia do zarządzania", Arrays.asList(task5, task6, task7, task8));
        Project project4 = new Project("Projekt narzędzia do statystyki", Arrays.asList(task8));

        Employee empl2 = new Employee("Tyrion", "Lannister", Arrays.asList(project3, project4));

        Company company1 = new Company(Arrays.asList(empl1, empl2));

        new ProjectReport(empl1.getProjects()).printReport();
        new ProjectReport(empl2.getProjects()).printReport();
        new ProjectReport(empl1.getProjects()).printReport(LocalDate.now(), LocalDate.of(2015,1,1));
        new ProjectReport(empl2.getProjects()).printReport(LocalDate.of(2015,1,1), LocalDate.now());

        new TaskReport("Aktualizacja danych", empl2.getProjects()).printReport();

//        new EmployeeReport(company1.getEmployees()).printReport();

    }
}
