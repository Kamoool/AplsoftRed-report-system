package pl.edu.agh.mwo.reporter.model;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeReport implements IReport{

    private final SimpleDateFormat REPORT_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
    private final SimpleDateFormat FILE_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
    private final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private String head;
    private String legend;
    private Company company;
    private List<Employee> employees;
    private String reportBody;
    private String employeeNameFilter = "";
    private String projectNameFilter = "";

    public EmployeeReport(Company company) {
        this.head = "Report #" + this.hashCode();
        this.legend = String.format("Pracownik | ");
        this.company = company;
        this.employees = company.getEmployees();
        this.projectNameFilter = "";
    }

    public String getEmployeeNameFilter() {
        return employeeNameFilter;
    }

    public void setEmployeeNameFilter(String employeeNameFilter) {
        this.employeeNameFilter = employeeNameFilter;
    }

    public void resetEmployeeNameFilter(String employeeNameFilter) {
        this.employeeNameFilter = "";
    }



    @Override
    public void updateReport() {
        if (employeeNameFilter.equals("")){
            this.employees = company.getEmployees();
        } else {
            this.employees = company.getEmployees().stream()
                    .filter(employee -> employee.getLastName().equals(employeeNameFilter.split("_")[0]) && employee.getFirstName().equals(employeeNameFilter.split("_")[1]))
                    .collect(Collectors.toList());
        }


        LocalDate newestDate = null;
        LocalDate oldestDate = null;

        StringBuilder sb = new StringBuilder();
        sb.append(head).append("\n").append(legend);

        Map<String, Integer> projectsMapping = new HashMap<>();
        Integer projectIndex = 1;
                
        for (Employee employee : employees) {
        	for (Project project : employee.getProjects()){
        		if(!projectsMapping.containsKey(project.getName()) && project.getName().contains(projectNameFilter)) {

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
                if (project.getName().contains(projectNameFilter)){
                    double projectHoursSum = project.getTasks().stream()
                            .map(x -> x.getHours())
                            .reduce(0.0, Double::sum);
                    projectsHoursSum[projectsMapping.get(project.getName())-1] = projectHoursSum;
                }
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
        updateReport();
        System.out.println(reportBody);
    }

    @Override
    public void handleFilters(Object[] filters) {
        if (filters[4] != null){
            employeeNameFilter = (String) filters[4];
            System.out.println((String) filters[4]);
        }

        if (filters[5] != null){
            projectNameFilter = (String) filters[5];
        }

    }

    @Override
    public String getReportBody(){
        return reportBody;
    }
}
