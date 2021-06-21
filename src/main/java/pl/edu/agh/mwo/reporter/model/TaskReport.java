package pl.edu.agh.mwo.reporter.model;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskReport implements IReport{

    private final SimpleDateFormat REPORT_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
    private final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private String head;
    private String legend;
    private List<Project> projects;
    private String reportBody;
    private TaskReportRecord taskReportRecord;

    private String taskName;

    public TaskReport(String taskName, Company company) {
        this.taskName = taskName;
        this.head = "Report #" + this.hashCode();
        this.legend = String.format("| Task | Projekt 1| Projekt 2 | Projekt 3 | Suma |");
        this.projects = new ArrayList<>();
        for (Employee employee : company.getEmployees()) {
            this.projects.addAll(employee.getProjects());
        }
    }

    public TaskReport(Company company) {
        this(null, company);
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getReportBody() {
        return reportBody;
    }

    @Override
    public void updateReport() {
        if (taskName == null){
            reportBody = "Set Task name first!";
            return;
        }

        taskReportRecord = new TaskReportRecord(taskName, projects);

        String firstEntryFormat = "| %-" + Math.max(taskName.length(), 10) + "s ";

        double totalHours = 0.0;

        StringBuilder sbRow0 = new StringBuilder();
        StringBuilder sbRow1 = new StringBuilder();
        StringBuilder sbRow2 = new StringBuilder();

        sbRow0.append(head);
        sbRow1.append(String.format(firstEntryFormat, "Task name"));
        sbRow2.append(String.format(firstEntryFormat, taskName));

        for (Map.Entry<Project, Double> entry : taskReportRecord.getTaskProjectMap().entrySet()){
            String entryFormat = "| %-" + entry.getKey().getName().length() + "s ";
            sbRow1.append(String.format(entryFormat, entry.getKey().getName()));
            sbRow2.append(String.format(entryFormat, entry.getValue()));
            totalHours = totalHours + entry.getValue();
        }

        sbRow1.append("| Total hours |");
        sbRow2.append(String.format("| %-11s |", totalHours)).append("\n");
        if (taskReportRecord.getOldestDate() != null && taskReportRecord.getNewestDate() != null){
            sbRow2.append("Oldest entry comes from " + SHORT_DATE_FORMATTER.format(taskReportRecord.getOldestDate()) + ", newest comes from " + SHORT_DATE_FORMATTER.format(taskReportRecord.getNewestDate()) + "\n");
        }
        sbRow2.append("Task report generated at: ").append(REPORT_DATE_FORMATTER.format(new Date(System.currentTimeMillis())));


        reportBody = sbRow0.toString() + "\n" +
                        sbRow1.toString() + "\n" +
                                sbRow2.toString();
    }

    public void updateReport(String name) {
        taskName = name;
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
            taskName = (String) filters[5];
        }
    }

}
