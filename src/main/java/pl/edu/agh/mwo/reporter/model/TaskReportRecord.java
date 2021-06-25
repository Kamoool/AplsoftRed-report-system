package pl.edu.agh.mwo.reporter.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskReportRecord {

    private String nameFilter;
    private Map<Project, List<Task>> taskProjectMap;
    private LocalDate oldestDate;
    private LocalDate newestDate;
    int longestTaskName = 10;

    public TaskReportRecord(String name, List<Project> projects) {
        this.nameFilter = name;
        this.taskProjectMap = new HashMap<>();
        this.oldestDate = null;
        this.newestDate = null;

        for (Project project : projects){
            if (!taskProjectMap.containsKey(project)) {
                taskProjectMap.put(project, new ArrayList<>());
                for (Task task : project.getTasks()) {
                    if (task.getName().contains(this.nameFilter)) {
                        taskProjectMap.get(project).add(task);
                        longestTaskName = Math.max(longestTaskName, task.getName().length());
                        updateDates(task);
                    }
                }
            } else {
                List<Task> tasksToBeAdded = new ArrayList<>();
                for (Task task : project.getTasks()){
                    if (task.getName().contains(this.nameFilter)) {
                        tasksToBeAdded.add(task);
                        taskProjectMap.get(project).add(task);
                        longestTaskName = Math.max(longestTaskName, task.getName().length());
                        updateDates(task);
                    }
                }
                Project existingProject = null;
                for (Project key : taskProjectMap.keySet()){
                    if (key.equals(project)){
                        existingProject = key;
                        break;
                    }
                }
                existingProject.getTasks().addAll(tasksToBeAdded);
                taskProjectMap.put(project, taskProjectMap.get(project));


            }
        }

        List<Project> emptyProjects = new ArrayList<>();

        for (Map.Entry<Project, List<Task>> entry : taskProjectMap.entrySet()){
            if (entry.getValue().size() == 0){
                emptyProjects.add(entry.getKey());
            }
        }

        for (Project projectToDelete : emptyProjects){
            taskProjectMap.remove(projectToDelete);
        }
    }

    private void updateDates(Task task) {
        if (newestDate != null) {
            newestDate = task.getDate().isAfter(newestDate) ? task.getDate() : newestDate;
        } else {
            newestDate = task.getDate();
        }

        if (oldestDate != null) {
            oldestDate = task.getDate().isBefore(oldestDate) ? task.getDate() : oldestDate;
        } else {
            oldestDate = task.getDate();
        }
        longestTaskName = Math.max(longestTaskName, task.getName().length());
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public Map<Project, List<Task>> getTaskProjectMap() {
        return taskProjectMap;
    }

    public LocalDate getOldestDate() {
        return oldestDate;
    }

    public LocalDate getNewestDate() {
        return newestDate;
    }

    public int getLongestTaskName() {
        return longestTaskName;
    }
}
