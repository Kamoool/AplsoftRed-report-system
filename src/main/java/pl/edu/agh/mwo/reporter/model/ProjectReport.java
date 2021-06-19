package pl.edu.agh.mwo.reporter.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectReport implements IReport{

    private String head;
    private String legend;
    private List<Project> projects;
    private String reportBody;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
    private String lineFormat = "%-10s | %10s\n";

    public ProjectReport(List<Project> projects) {
        this.head = "Report #" + this.hashCode();
        this.head = String.format(lineFormat, "Projekt", "Godziny");
        this.projects = projects;
    }

    @Override
    public void generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(head);

        for (Project project : projects) {
            double projectHoursSum = project.getTasks().stream()
                    .map(x -> x.getHours())
                    .reduce(0, Double::sum);

            sb.append(String.format(lineFormat, project.getName(), projectHoursSum));
        }

        sb.append("Project report generated at: " + formatter.format(new Date(System.currentTimeMillis())));
        reportBody = sb.toString();
    }

    @Override
    public void printReport() {

    }

    @Override
    public void saveReportToFile() {

    }
}
