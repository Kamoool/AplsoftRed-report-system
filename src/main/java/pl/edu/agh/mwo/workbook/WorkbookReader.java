package pl.edu.agh.mwo.workbook;

import java.io.File;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import pl.edu.agh.mwo.reporter.model.Company;
import pl.edu.agh.mwo.reporter.model.Employee;
import pl.edu.agh.mwo.reporter.model.Project;
import pl.edu.agh.mwo.reporter.model.Task;


public class WorkbookReader {

    private List<Employee> employees = new ArrayList<>();

    private List<File> files = new ArrayList();
    
    private List<String> errorLog = new ArrayList();
    
    

    public WorkbookReader(List<String> filePaths) {
        for (String filePath : filePaths) {
            this.files.add(new File(filePath));
        }
    }

    public Company getCompany() {
        return new Company(this.readEmployees(files));
    }

    public List<String> getErrorLog() {
        return errorLog;
    }

    public List<Employee> readEmployees(List<File> files) {

        for (File file : files) {
            WorkbookLoader wl = new WorkbookLoader(file);
            Workbook wb = wl.openWorkbook();
            String substring = "";
            if (wl.getWorkbookName().contains(" "))
                substring = wl.getWorkbookName().substring(0, wl.getWorkbookName().indexOf(" "));
            else
                substring = wl.getWorkbookName();
            String[] SplitedWorkbookName = substring.split("_");
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

    private List<Project> getProjects(Workbook wb) {


        List<Project> projects = new ArrayList();

        for (Sheet sheet : wb) {
            Project project = new Project(sheet.getSheetName());
            project.setTasks(getTasks(sheet));
            projects.add(project);
        }
        return projects;
    }

    private List<Task> getTasks(Sheet sheet) {
        List<Task> tasks = new ArrayList();
        String error;
        String name;
        Date date;
        double hours;
        int cellCount = 0;
        
        for (int rowCount = 1; rowCount <= sheet.getLastRowNum(); rowCount++) {
            try {
                cellCount=1;
                date = sheet.getRow(rowCount).getCell(0).getDateCellValue();
                cellCount=2;
                name = sheet.getRow(rowCount).getCell(1).getStringCellValue();
                cellCount=3;
                hours = sheet.getRow(rowCount).getCell(2).getNumericCellValue();
                
                tasks.add(new Task( name , date, hours));
            } catch (DateTimeParseException e) {
                rowCount++;
                error = "Niepoprawna data w wierszu " + rowCount + " w komórce " + cellCount;
                errorLog.add(error);
                rowCount--;
            } catch (NumberFormatException e2) {
                rowCount++;
                error = "Niepoprawny numer w wierszu " + rowCount + " w komórce " + cellCount;
                errorLog.add(error);
                rowCount--;
            } catch (IllegalStateException e3) {
                rowCount++;
                error = "Niepoprawny format danych w wierszu " + rowCount + " w komórce "+ cellCount + " : " + e3.getMessage();
                errorLog.add(error);
                rowCount--;
            } catch (NullPointerException e4) {
                rowCount++;
                error = "Komórka " + cellCount + " w wierszu " + rowCount + " jest pusta";
                errorLog.add(error);
                rowCount--;
            }
        }
        return tasks;
    }
}
