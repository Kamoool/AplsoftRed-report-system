package pl.edu.agh.mwo.reporter.model;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskReport implements IReport{

    private final SimpleDateFormat REPORT_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
    private final SimpleDateFormat FILE_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
    private final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private String head;
    private String legend;
    private List<Project> projects;
    private String reportBody;
    private TaskReportRecord taskReportRecord;

    private String taskName;

    public TaskReport(String taskName, List<Project> projects) {
        this.taskName = taskName;
        this.head = "Report #" + this.hashCode();
        this.legend = String.format("| Task | Projekt 1| Projekt 2 | Projekt 3 | Suma |");
        this.projects = projects;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void updateReport() {
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
        sbRow2.append(String.format("| %-11s |", totalHours)).append("\n")
                .append("Task report generated at: ").append(REPORT_DATE_FORMATTER.format(new Date(System.currentTimeMillis())));


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
    public void saveReportToFile() {

    }
}
