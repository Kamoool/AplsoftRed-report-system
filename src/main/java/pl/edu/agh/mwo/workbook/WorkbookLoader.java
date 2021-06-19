package pl.edu.agh.mwo.workbook;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

public class WorkbookLoader {

    public static Workbook openWorkbook() {
        try {
            return WorkbookFactory.create(new File("src/main/resources/Kowalski_Jan.xls"));
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
