package pl.edu.agh.mwo.reporter.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

    public Task(String stringCellValue, Date dateCellValue, double numericCellValue) {
        this(stringCellValue, convertDateToLocalDate(dateCellValue), numericCellValue);
    }
    private static LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
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

    public void setHours(double hours) {
        this.hours = hours;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(date, task.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }
}
