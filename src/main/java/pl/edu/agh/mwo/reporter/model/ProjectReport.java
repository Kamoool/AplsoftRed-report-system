package pl.edu.agh.mwo.reporter.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectReport implements IReport{

    private final SimpleDateFormat REPORT_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
    private final SimpleDateFormat FILE_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
    private final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String LINE_FORMAT = "%-30s | %s\n";
    

    private String head;
    private String legend;
    private List<Project> projects;
    private String reportBody;
    private LocalDate from;
    private LocalDate to;
    private String projectName;

    public ProjectReport(Company company) {
        this.head = "Report #" + this.hashCode() + "\n";
        this.legend = String.format(LINE_FORMAT, "Projekt", "Godziny");
        this.projects = new ArrayList<>();
        for (Employee employee : company.getEmployees()) {
            this.projects.addAll(employee.getProjects());
        }
        this.from = LocalDate.of(1900,1,1);
        this.to = LocalDate.now();
        this.projectName = "";
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public void setFromAndTo(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public String getReportBody() {
        return reportBody;
    }

    public void setReportBody(String reportBody) {
        this.reportBody = reportBody;
    }

    @Override
    public void updateReport() {
        LocalDate newestDate = null;
        LocalDate oldestDate = null;

        StringBuilder sb = new StringBuilder();
        sb.append(head);

        if (from.isAfter(to)){
            sb.append("ERROR: 'FROM' DATE CANNOT BE BEFORE 'TO' DATE.\n");
            reportBody = sb.toString();
            return;
        }
        sb.append(legend);

        for (Project project : projects) {

            if (project.getName().contains(projectName)){
                List<Task> filteredList = project.getTasks().stream()
                        .filter(x -> x.getDate().isBefore(to))
                        .filter(x -> x.getDate().isAfter(from))
                        .collect(Collectors.toList());


                double projectHoursSum = filteredList.stream()
                        .map(Task::getHours)
                        .reduce(0.0, Double::sum);

                LocalDate projectNewestDate = null;
                LocalDate projectOldestDate = null;

                if (filteredList.size() != 0) {
                    projectNewestDate = filteredList.stream()
                            .map(Task::getDate)
                            .max(LocalDate::compareTo)
                            .get();

                    projectOldestDate = filteredList.stream()
                            .map(Task::getDate)
                            .min(LocalDate::compareTo)
                            .get();
                }

                if (newestDate != null) {
                    newestDate = projectNewestDate.isAfter(newestDate) ? projectNewestDate : newestDate;
                } else {
                    newestDate = projectNewestDate;
                }

                if (oldestDate != null) {
                    oldestDate = projectOldestDate.isBefore(oldestDate) ? projectOldestDate : oldestDate;
                } else {
                    oldestDate = projectOldestDate;
                }


                String projectName = project.getName();
                if (projectName.length() > 30) {
                    projectName = projectName.substring(0, 30 - 2) + "..";
                }
                sb.append(String.format(LINE_FORMAT, projectName, projectHoursSum));
            }
        }

        sb.append("Report generated for period from " + SHORT_DATE_FORMATTER.format(from) + " to " + SHORT_DATE_FORMATTER.format(to) + "\n");
        if (oldestDate != null && newestDate != null){
            sb.append("Oldest entry comes from " + SHORT_DATE_FORMATTER.format(oldestDate) + ", newest comes from " + SHORT_DATE_FORMATTER.format(newestDate) + "\n");
        }
        sb.append("Project report generated at: " + REPORT_DATE_FORMATTER.format(new Date(System.currentTimeMillis())));
        reportBody = sb.toString();
    }

    @Override
    public void printReport() {
        updateReport();
        System.out.println(reportBody);
    }

    public void printReport(LocalDate from, LocalDate to) {
        setFromAndTo(from, to);
        printReport();
    }

    @Override
    public void handleFilters(Object[] filters) {
        if (filters[2] != null){
            from = (LocalDate) filters[2];
        }

        if (filters[3] != null){
            to = (LocalDate) filters[3];
        }

        if (filters[5] != null){
            projectName = (String) filters[5];
        }

    }
}
