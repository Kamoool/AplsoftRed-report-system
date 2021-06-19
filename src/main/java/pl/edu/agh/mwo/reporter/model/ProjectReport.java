package pl.edu.agh.mwo.reporter.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ProjectReport implements IReport{

    private final SimpleDateFormat REPORT_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
    private final SimpleDateFormat FILE_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
    private final String LINE_FORMAT = "%-10s | %10s\n";
    

    private String head;
    private String legend;
    private List<Project> projects;
    private String reportBody;

    public ProjectReport(List<Project> projects) {
        this.head = "Report #" + this.hashCode() + "\n";
        this.legend = String.format(LINE_FORMAT, "Projekt", "Godziny");
        this.projects = projects;
        updateReport();
    }

    @Override
    public void updateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(head);
        sb.append(legend);

        for (Project project : projects) {
            double projectHoursSum = project.getTasks().stream()
                    .map(x -> x.getHours())
                    .reduce(0.0, Double::sum);

            sb.append(String.format(LINE_FORMAT, project.getName(), projectHoursSum));
        }

        sb.append("Project report generated at: " + REPORT_DATE_FORMATTER.format(new Date(System.currentTimeMillis())));
        reportBody = sb.toString();
    }

    @Override
    public void printReport() {
        System.out.println(reportBody);
    }

    @Override
    public void saveReportToFile(){
        //TODO ustalic gdzie przechowujemy reporty, czy w jednym miejscu czy np. podajemy path na wejscie
        String reportFilePath = new File("ProjRep_" + FILE_DATE_FORMATTER.format(new Date(System.currentTimeMillis())) + ".txt").getAbsolutePath();
        try {
            Files.write(Paths.get(reportFilePath), Arrays.asList(reportBody.split("\n")));
        } catch (IOException e) {
            System.out.println("Error: unalbe to create file " + reportFilePath);
            e.printStackTrace();
        }
        System.out.println("File " + reportFilePath + " created succesfully");        
    }
}
