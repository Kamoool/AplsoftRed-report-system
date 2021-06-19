package pl.edu.agh.mwo.reporter.model;

import java.time.LocalDate;
import java.util.Objects;

public class Task {

    private String name;
    private LocalDate date;
    private double hours;

    public Task(String name, LocalDate date, double hours) {
        this.name = name;
        this.date = date;
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getHours() {
        return hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
