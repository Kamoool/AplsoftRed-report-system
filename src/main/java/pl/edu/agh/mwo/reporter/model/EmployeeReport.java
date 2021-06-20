package pl.edu.agh.mwo.reporter.model;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeReport implements IReport{

    private final SimpleDateFormat REPORT_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
    private final SimpleDateFormat FILE_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
    private final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private String head;
    private String legend;
    private List<Employee> employees;
    private String reportBody;

    public EmployeeReport(Company company) {
        this.head = "Report #" + this.hashCode();
        this.legend = String.format("Pracownik | ");
        this.employees = company.getEmployees();
        updateReport();
    }   
        
    @Override
    public void updateReport() {
        LocalDate newestDate = null;
        LocalDate oldestDate = null;

        StringBuilder sb = new StringBuilder();
        sb.append(head).append("\n").append(legend);

        Map<String, Integer> projectsMapping = new HashMap<>();
        Integer projectIndex = 1;
                
        for (Employee employee : employees) {
        	for (Project project : employee.getProjects()){
        		if(!projectsMapping.containsKey(project.getName())) {
        			projectsMapping.put(project.getName(), projectIndex); 
            		projectIndex ++;
            		sb.append(project.getName() + " | ");
        		}

                LocalDate projectNewestDate = project.getTasks().stream()
                        .map(x -> x.getDate())
                        .max(LocalDate::compareTo)
                        .get();

                LocalDate projectOldestDate = project.getTasks().stream()
                        .map(x -> x.getDate())
                        .min(LocalDate::compareTo)
                        .get();

                if (newestDate != null){
                    newestDate = projectNewestDate.isAfter(newestDate)? projectNewestDate : newestDate;
                } else {
                    newestDate = projectNewestDate;
                }

                if (oldestDate != null){
                    oldestDate = projectOldestDate.isBefore(oldestDate)? projectOldestDate : oldestDate;
                } else {
                    oldestDate = projectOldestDate;
                }

        	}
        }
    	sb.append("Suma\n");

        for (Employee employee : employees) {
            double employeeHoursSum = 0.0;

            sb.append(employee.getFirstName() + " " + employee.getLastName() + " | ");

            double projectsHoursSum[] = new double[projectsMapping.size()];
            
            for (Project project : employee.getProjects()){
                double projectHoursSum = project.getTasks().stream()
                        .map(x -> x.getHours())
                        .reduce(0.0, Double::sum);
                
                projectsHoursSum[projectsMapping.get(project.getName())-1] = projectHoursSum;   
            }
            
            for(double hoursSum : projectsHoursSum) {
            	 sb.append(hoursSum + "hrs | ");
                 employeeHoursSum = employeeHoursSum + hoursSum;
            }
           
            sb.append("Total -> " + employeeHoursSum + "hrs\n");
        }

        if (oldestDate != null && newestDate != null){
            sb.append("Oldest entry comes from " + SHORT_DATE_FORMATTER.format(oldestDate) + ", newest comes from " + SHORT_DATE_FORMATTER.format(newestDate) + "\n");
        }
        sb.append("Employee report generated at: " + REPORT_DATE_FORMATTER.format(new Date(System.currentTimeMillis())));
        reportBody = sb.toString();
    }

    @Override
    public void printReport() {
        System.out.println(reportBody);
    }

    @Override
    public void saveReportToFile() {
        //TODO ustalic gdzie przechowujemy reporty, czy w jednym miejscu czy np. podajemy path na wejscie
        String reportFilePath = new File("EmplRep_" + FILE_DATE_FORMATTER.format(new Date(System.currentTimeMillis())) + ".txt").getAbsolutePath();
        try {
            Files.write(Paths.get(reportFilePath), Arrays.asList(reportBody.split("\n")));
        } catch (IOException e) {
            System.out.println("Error: unable to create file " + reportFilePath);
            e.printStackTrace();
        }
        System.out.println("File " + reportFilePath + " created succesfully");
    }

    @Override
    public String getReportBody(){
        return reportBody;
    }
}
