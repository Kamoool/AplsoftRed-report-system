package pl.edu.agh.mwo.reporter.model;

import org.junit.*;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

public class CompanyTest {

    private Company company;

    @Before
    public void setUp() {
        company = new Company();
        fillData();
    }

    private void fillData() {
        Task task1 = new Task("name1", LocalDate.of(2020, 10, 10), 5);
        Task task10 = new Task("name1", LocalDate.of(2020, 10, 10), 5);
        Task task2 = new Task("name1", LocalDate.of(2021, 11, 11), 6);
        Task task3 = new Task("name2", LocalDate.of(2020, 10, 10), 7);
        Task task4 = new Task("name3", LocalDate.of(2019, 9, 9), 5);
        Task task5 = new Task("name4", LocalDate.of(2018, 8, 15), 4);
        Task task6 = new Task("name5", LocalDate.of(2016, 12, 3), 8);
        Task task7 = new Task("name6", LocalDate.of(2018, 3, 21), 3);
        Task task8 = new Task("name7", LocalDate.of(2015, 5, 17), 1);
        Task task9 = new Task("name8", LocalDate.of(2013, 2, 12), 3);

        Project project1 = new Project("Project1");
        Project project2 = new Project("Project2");
        Project project3 = new Project("Project3");
        Project project4 = new Project("Project4");
        Project project11 = new Project("Project1");

        Employee employee1 = new Employee("Jan", "Kowalski");
        Employee employee2 = new Employee("Marek", "Nowak");

        project1.addTask(task1);
        project1.addTask(task10);
        project1.addTask(task2);
        project2.addTask(task3);
        project2.addTask(task4);
        project3.addTask(task5);
        project3.addTask(task6);
        project4.addTask(task7);
        project4.addTask(task8);
        project11.addTask(task9);

        employee1.addProject(project1);
        employee1.addProject(project11);
        employee1.addProject(project2);
        employee2.addProject(project3);
        employee2.addProject(project4);

        company.addEmployee(employee1);
        company.addEmployee(employee2);
    }

    @Test
    public void findingEmployeeByName() {
        Employee employeeByName = company.getEmployeeByName("Jan", "Kowalski");
        Employee testEmployee = new Employee("Jan", "Kowalski");
        assertEquals(employeeByName, testEmployee);
    }

    @Test
    public void findingProjectByName() {
        Project projectByName = company.getEmployeeByName("Jan", "Kowalski").findProjectByName("Project1");
        Project testProject = new Project("Project1");
        assertEquals(projectByName, testProject);
    }

    @Test
    public void findingTasksByNameWithMergedHours() {
        List<Task> taskByName = company.getEmployeeByName("Jan", "Kowalski").findProjectByName("Project1").findTaskByName("name1");
        Task testTask = new Task("name1", LocalDate.of(2020, 10, 10), 10);
        assertTrue(taskByName.contains(testTask));

    }

    @Test
    public void findingTaskByNameClonnedProject() {
        Task testTask = new Task("name8", LocalDate.of(2013, 2, 12), 3.0);
        boolean checkedCondition = company.getEmployeeByName("Jan", "Kowalski").findProjectByName("Project1").getTasks().contains(testTask);
        assertTrue(checkedCondition);
    }

}
