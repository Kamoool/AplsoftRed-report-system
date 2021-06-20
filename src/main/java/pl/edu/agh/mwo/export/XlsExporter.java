package pl.edu.agh.mwo.export;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.edu.agh.mwo.reporter.model.IReport;

import java.io.FileOutputStream;
import java.io.IOException;

public class XlsExporter {

    public void export(String outputPath, IReport report) throws IOException {
       String reportBody = report.getReportBody();

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Report");

        String[] rows = reportBody.split("\n");
        int rowNum = 0;
        for(String row: rows){
            int cellNum = 0;
            Row xlsRow = sheet.createRow(rowNum++);
            String[]   cells = row.split("\\|");
            for(String cell: cells){
                xlsRow.createCell(cellNum++)
                        .setCellValue(cell);
            }
        }
        for(int i = 0; i <10; i++) {
            sheet.autoSizeColumn(i);
        }
        FileOutputStream fileOut = new FileOutputStream(outputPath);
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();
    }
}
