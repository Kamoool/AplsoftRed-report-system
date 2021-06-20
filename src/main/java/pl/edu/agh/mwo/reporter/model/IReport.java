package pl.edu.agh.mwo.reporter.model;

import java.time.LocalDate;

public interface IReport {

    void updateReport(LocalDate from, LocalDate to);
    void updateReport();
    void printReport(LocalDate from, LocalDate to);
    void printReport();
    void saveReportToFile();

}
