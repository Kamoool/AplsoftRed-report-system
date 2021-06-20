package pl.edu.agh.mwo.workbook;

import java.io.File;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Employee;
import pl.edu.agh.mwo.reporter.model.Project;
import pl.edu.agh.mwo.reporter.model.Task;


public class WorkbookReader {
    
    List<Employee> employees = new ArrayList<>();
    
    List<File> files = new ArrayList();
    
    public WorkbookReader(List<String> filePaths) {

        for ( String filePath : filePaths) {
            this.files.add(new File(filePath));
        }
    }

    public Company getCompany() {
        return new Company(this.getEmployees());
    }
    
    public  List <Employee> readEmployees(List<File> files){
        
        for (File file : files) {
        WorkbookLoader wl = new WorkbookLoader(file);
        Workbook wb = wl.openWorkbook();
        String [] SplitedWorkbookName = wl.getWorkbookName().split("_");
        String surname = SplitedWorkbookName[0];
        String name = SplitedWorkbookName[1].replaceFirst("[.][^.]+$", "");
        Employee e = new Employee(name, surname, getProjects(wb));
        employees.add(e);
        }
        return employees;
    }
    

    public List<Employee> getEmployees() {
        return employees;
    }

    private List<Project> getProjects(Workbook wb){
        
        
       List<Project> projects = new ArrayList();
        
        for (Sheet sheet : wb) {
            Project project = new Project(sheet.getSheetName());
            project.setTasks(getTasks(sheet));
            projects.add(project);
        }
        return projects;
    }
    
    private List<Task> getTasks(Sheet sheet){
        List<Task> tasks = new ArrayList();
        for (Row row : sheet) {
            try {
            tasks.add(new Task(row.getCell(1).getStringCellValue(), row.getCell(0).getDateCellValue(), row.getCell(2).getNumericCellValue()));
            }catch(DateTimeParseException e) {
                System.out.println("Niepoprawna data");
            }catch(NumberFormatException e2) {
                System.out.println("niepoprawny numer");
            }catch(IllegalStateException e3) {
                System.out.println("Niepoprawny format danych: " + e3.getMessage() + row.getCell(0).getStringCellValue()  + row.getCell(1).getStringCellValue()  + row.getCell(2).getStringCellValue() );
           }
           
        }
        return tasks;
    }
    
    
    
}
