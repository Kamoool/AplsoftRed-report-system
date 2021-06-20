package pl.edu.agh.mwo.reporter.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskReportRecord {

    private String name;
    private Map<Project, Double> taskProjectMap;
    private LocalDate oldestDate;
    private LocalDate newestDate;

    public TaskReportRecord(String name, List<Project> projects) {
        this.name = name;
        this.taskProjectMap = new HashMap<>();
        this.oldestDate = null;
        this.newestDate = null;

        for (Project project : projects){
            for (Task task : project.getTasks()) {
                if (task.getName().equals(this.name)){

                    if (taskProjectMap.containsKey(project)){
                        taskProjectMap.put(project, taskProjectMap.get(project) + task.getHours());
                    } else {
                        taskProjectMap.put(project, task.getHours());
                    }

                    if (newestDate != null){
                        newestDate = task.getDate().isAfter(newestDate)? task.getDate() : newestDate;
                    } else {
                        newestDate = task.getDate();
                    }

                    if (oldestDate != null){
                        oldestDate = task.getDate().isBefore(oldestDate)? task.getDate() : oldestDate;
                    } else {
                        oldestDate = task.getDate();
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public Map<Project, Double> getTaskProjectMap() {
        return taskProjectMap;
    }

    public LocalDate getOldestDate() {
        return oldestDate;
    }

    public void setOldestDate(LocalDate oldestDate) {
        this.oldestDate = oldestDate;
    }

    public LocalDate getNewestDate() {
        return newestDate;
    }

    public void setNewestDate(LocalDate newestDate) {
        this.newestDate = newestDate;
    }
}
