package pl.edu.agh.mwo.reporter.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Project {

    private String name;
    private List<Task> tasks = new ArrayList<>();

    public Project(String name) {
        this.name = name;
    }

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (!this.tasks.contains(task))
            this.tasks.add(task);
        else {
            for (Task task1 : this.tasks) {
                if (task1.equals(task))
                    task1.setHours(task1.getHours() + task.getHours());
            }
        }
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public List<Task> findTaskByName(String taskName) {
        List<Task> foundTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getName().equals(taskName))
                foundTasks.add(task);
        }
        return foundTasks;
    }


    public Task findTaskByNameAndDate(String taskName, LocalDate date) {
        Task testTask = new Task(taskName, date, 0);
        for (Task task : tasks) {
            if (task.equals(testTask))
                return task;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(name, project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
