package pl.edu.agh.mwo.reporter.model;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TaskReport implements IReport{

    private final SimpleDateFormat REPORT_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
    private final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private String head;
    private String legend;
    private List<Project> projects;
    private String reportBody;
    private TaskReportRecord taskReportRecord;

    private String taskNameFilter;

    public TaskReport(String taskNameFilter, Company company) {
        this.taskNameFilter = taskNameFilter;
        this.head = "Report #" + this.hashCode();
        this.legend = String.format("| Task | Projekt 1| Projekt 2 | Projekt 3 | Suma |");
        this.projects = new ArrayList<>();
        for (Employee employee : company.getEmployees()) {
            this.projects.addAll(employee.getProjects());
        }
    }

    public TaskReport(Company company) {
        this("", company);
    }

    public String getTaskNameFilter() {
        return taskNameFilter;
    }

    public void setTaskNameFilter(String taskNameFilter) {
        this.taskNameFilter = taskNameFilter;
    }

    public String getReportBody() {
        return reportBody;
    }

    @Override
    public void updateReport() {

        taskReportRecord = new TaskReportRecord(taskNameFilter, projects);

        String firstEntryFormat = "| %-" + Math.max(taskReportRecord.getLongestTaskName(), 10) + "s |";
        String lastEntryFormat = "| %" + Math.max(taskReportRecord.getLongestTaskName(), 10) + "s |";

        StringBuilder sbRow0 = new StringBuilder();
        StringBuilder sbRow1 = new StringBuilder();
        StringBuilder sbBottomRows = new StringBuilder();

        sbRow0.append(head);
        sbRow1.append(String.format(firstEntryFormat, "Task name "));
        sbBottomRows.append(String.format(lastEntryFormat, "Total:"));

        List<Project> projects = new ArrayList<>();
        Set<Task> tasks = new HashSet<>();

        for (Map.Entry<Project, List<Task>> entry : taskReportRecord.getTaskProjectMap().entrySet()){
            projects.add(entry.getKey());
            tasks.addAll(entry.getValue());
        }


        for (Project project : projects){
            sbRow1.append(String.format(" %" + project.getName().length() + "s |", project.getName()));
        }

        StringBuilder sbMiddleRows = new StringBuilder();
        Map<Project, Double> totalProjectHours = new HashMap<>();
        for (Task task : tasks){
            sbMiddleRows.append(String.format(firstEntryFormat, task.getName()));
            for (Project project : projects){

                double taskHours = 0.0;
                for (Task t1 : project.getTasks()){
                    if (t1.getName().contains(task.getName())){
                        taskHours = t1.getHours();
                        if (totalProjectHours.containsKey(project)){
                            totalProjectHours.put(project, totalProjectHours.get(project) + taskHours);
                        } else {
                            totalProjectHours.put(project, taskHours);
                        }
                        break;
                    }
                }


                sbMiddleRows.append(String.format(" %" + project.getName().length()  + "s |", taskHours));
            }
            sbMiddleRows.append("\n");
        }

        for (Map.Entry<Project, Double> entry: totalProjectHours.entrySet()){
            sbBottomRows.append(String.format(" %" + entry.getKey().getName().length()  + "s |", entry.getValue()));
        }
        sbBottomRows.append("\n");





        if (taskReportRecord.getOldestDate() != null && taskReportRecord.getNewestDate() != null){
            sbBottomRows.append("Oldest entry comes from " + SHORT_DATE_FORMATTER.format(taskReportRecord.getOldestDate()) + ", newest comes from " + SHORT_DATE_FORMATTER.format(taskReportRecord.getNewestDate()) + "\n");
        }
        sbBottomRows.append("Task report generated at: ").append(REPORT_DATE_FORMATTER.format(new Date(System.currentTimeMillis())));

        reportBody = sbRow0.toString() + "\n" + sbRow1.toString() + "\n" + sbMiddleRows.toString() + sbBottomRows.toString();
    }

    public void updateReport(String name) {
        taskNameFilter = name;
        printReport();
    }

    @Override
    public void printReport() {
        updateReport();
        System.out.println(reportBody);
    }

    @Override
    public void handleFilters(Object[] filters) {
        if (filters[5] != null){
            taskNameFilter = (String) filters[5];
        }
    }

}
