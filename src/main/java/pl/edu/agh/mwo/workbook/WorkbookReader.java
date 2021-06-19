package pl.edu.agh.mwo.workbook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


import pl.edu.agh.mwo.reporter.model.Project;
import pl.edu.agh.mwo.reporter.model.Task;


public class WorkbookReader {
    
    private List<Project> projects = new ArrayList();

    public void getProjects(Workbook wb){
        
        for (Sheet sheet : wb) {
            Project project = new Project(sheet.getSheetName());
            project.setTasks(getTasks(sheet));
            projects.add(project);
        }
    }
    
    public List<Task> getTasks(Sheet sheet){
        List<Task> tasks = new ArrayList();
        for (Row row : sheet) {
            tasks.add(new Task(row.getCell(2).getStringCellValue(), LocalDate.parse(row.getCell(1).getStringCellValue()), row.getCell(3).getNumericCellValue()));
            }
        return tasks;
    }
    
    
    
}
