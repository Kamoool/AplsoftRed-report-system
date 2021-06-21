package pl.edu.agh.mwo.reporter.model;

import java.util.Map;

public interface IReport {

    void updateReport();
    void printReport();
    void handleFilters(Object[] filters);

}
