package pl.edu.agh.mwo.workbook;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

public class WorkbookLoader {
    

    private File file;
    
    
    public WorkbookLoader(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
    
    public void setFile(File file) {
        this.file = file;
    }
    
    public Workbook openWorkbook() {
        try {
            return WorkbookFactory.create(file);
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getWorkbookName() {
        
        return file.getName();
    }
}
