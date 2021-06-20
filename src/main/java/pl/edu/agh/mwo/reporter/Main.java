package pl.edu.agh.mwo.reporter;

import java.time.LocalDate;

import pl.edu.agh.mwo.reporter.model.*;
import pl.edu.agh.mwo.workbook.WorkbookReader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
      
        WorkbookReader wr = new WorkbookReader();
        
        Company company1 = wr.getCompany();
        
        Employee empl1 = wr.getEmployees().get(0);
       
        new EmployeeReport(company1.getEmployees()).printReport();

        new ProjectReport(empl1.getProjects()).printReport();
  
        new ProjectReport(empl1.getProjects()).printReport(LocalDate.of(2000,1,1), LocalDate.now());

        new TaskReport("Analiza wymagañ", empl1.getProjects()).printReport();

    }
}
