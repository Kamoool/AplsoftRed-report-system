package pl.edu.agh.mwo.reporter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {

    private String firstName;
    private String lastName;
    private List<Project> projects = new ArrayList<>();


    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(String firstName, String lastName, List<Project> projects) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.projects = projects;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        if (!this.projects.contains(project))
            this.projects.add(project);
        else {
            for (Project project1 : projects) {
                if (project1.equals(project))
                    project.getTasks().forEach(project1::addTask);
            }
        }
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
    }

    public Project findProjectByName(String projectName) {
        Project testProject = new Project(projectName);
        for (Project project : projects) {
            if (project.equals(testProject))
                return project;
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
