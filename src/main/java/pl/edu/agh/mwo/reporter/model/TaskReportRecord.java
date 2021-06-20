package pl.edu.agh.mwo.reporter.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskReportRecord {

    private String name;
    private Map<Project, Double> taskProjectMap;

    public TaskReportRecord(String name, List<Project> projects) {
        this.name = name;
        this.taskProjectMap = new HashMap<>();
        for (Project project : projects){
            for (Task task : project.getTasks()) {
                if (task.getName().equals(this.name)){
                    if (taskProjectMap.containsKey(project)){
                        taskProjectMap.put(project, taskProjectMap.get(project) + task.getHours());
                    } else {
                        taskProjectMap.put(project, task.getHours());
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

}
