package pl.edu.agh.mwo.reporter.model;

import java.util.Map;

public interface IReport {

    void updateReport();
    void printReport();
    void saveReportToFile();
    String getReportBody();
    void handleFilters(Object[] filters);

}
