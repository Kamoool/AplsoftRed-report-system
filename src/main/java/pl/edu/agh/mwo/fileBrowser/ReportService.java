package pl.edu.agh.mwo.fileBrowser;

import pl.edu.agh.mwo.reporter.model.Project;
import pl.edu.agh.mwo.workbook.WorkbookLoader;

import java.util.List;

public class ReportService {

    private FileBrowser fileBrowser;
    private String path;
  //  private WorkbookLoader workbookLoader = new WorkbookLoader(fileBrowser.browse(path));

    public ReportService(FileBrowser fileBrowser, String path) {
        this.fileBrowser = fileBrowser;
        this.path = path;
    }

//    public List<Project> getProjects() {
//
//    }

}
