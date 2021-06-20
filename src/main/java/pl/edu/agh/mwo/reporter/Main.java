package pl.edu.agh.mwo.reporter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.*;
import pl.edu.agh.mwo.fileBrowser.FileBrowser;
import pl.edu.agh.mwo.reporter.model.*;
import pl.edu.agh.mwo.workbook.WorkbookReader;

public class Main {
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        Object[] parsedArguments = parseArguments(args);

        //TODO - SEND DATA TO FUNCTIONS

        //TODO - SEND DATA TO FOLDER PARSER - DONE
        FileBrowser fileBrowser = new FileBrowser("xls");
        List<String> filePaths = fileBrowser.browse((String) parsedArguments[0]); // C://Users/cos/workbooks    lub C://Users/cos/workbooks/Kowalski_jan.xls
        filePaths.forEach(f -> System.out.println(f));

        //TODO - SEND DATA TO WORKBOOK LOADER
        List<String> testPaths = new ArrayList<>();

        WorkbookReader wr = new WorkbookReader(filePaths);
        Company company = wr.getCompany();
        System.out.println(company.getEmployees().size());

        //TODO - CHOOSE CORRECT REPORT TYPE
        IReport report = handleReportType((int) parsedArguments[1], company);

        report.printReport();


        // mockData
//        Task task1 = new Task("Aktualizacja danych", LocalDate.of(2020, 1, 8), 7);
//        Task task2 = new Task("Przetwarzanie danych", LocalDate.of(2012, 5, 6), 4);
//        Task task3 = new Task("Implementacja prototypu", LocalDate.of(2015, 1, 22), 8);
//        Task task4 = new Task("Aktualizacja danych", LocalDate.of(2016, 9, 8), 17);
//
//        Project project1 = new Project("Projekt testowy", Arrays.asList(task1, task2));
//        Project project2 = new Project("Projekt interfejsu graficznego", Arrays.asList(task3, task4));
//
//        Employee empl1 = new Employee("Jon", "Snow", Arrays.asList(project1, project2));
//
//        Task task5 = new Task("Specyfikacja wymagań", LocalDate.of(2020, 2, 8), 3);
//        Task task6 = new Task("Integracja", LocalDate.of(2013, 4, 16), 4);
//        Task task7 = new Task("Implementacja prototypu", LocalDate.of(2015, 11, 27), 12.5);
//        Task task8 = new Task("Aktualizacja danych", LocalDate.of(2016, 9, 8), 17);
//
//        Project project3 = new Project("Projekt narzędzia do zarządzania", Arrays.asList(task5, task6, task7, task8));
//        Project project4 = new Project("Projekt narzędzia do statystyki", Arrays.asList(task8));
//
//        Employee empl2 = new Employee("Tyrion", "Lannister", Arrays.asList(project3, project4));
//
//        Company company1 = new Company(Arrays.asList(empl1, empl2));
//
//        new EmployeeReport(company1).printReport();
//        FileBrowser fileBrowser = new FileBrowser("xls");
//        List<String> filePaths = fileBrowser.browse("src/main/resources/");
//        filePaths.forEach(f -> System.out.println(f));


//       wr.getEmployees().forEach(e -> System.out.println(e));

//        Employee empl11 = wr.getEmployees().get(0);
//
//
//        new ProjectReport(empl1.getProjects()).printReport();
//
//        new ProjectReport(empl1.getProjects()).printReport(LocalDate.of(2000, 1, 1), LocalDate.now());
//
//
//        new TaskReport("Analiza wymaga�", empl1.getProjects()).printReport();
//
//
//        new ProjectReport(company11).printReport();
//        new ProjectReport(company11).printReport(LocalDate.now(), LocalDate.of(2015, 1, 1));
//        new ProjectReport(company1).printReport(LocalDate.of(2015, 1, 1), LocalDate.now());
//
//
//        new TaskReport("Aktualizacja danych", company1).printReport();
//
//
//        new EmployeeReport(company1).printReport();


    }

    private static IReport handleReportType(int reportType, Company company) {

        IReport report = null;

        switch (reportType) {
            case 1:
                report = new ProjectReport(company);
                break;
            case 2:
                report = new EmployeeReport(company);
                break;
            case 3:
                report = new TaskReport(company);
                break;
            case 4:
                //TODO - REPORT 4 RUN

        }

        return report;
    }


    private static Object[] parseArguments(String[] args) {
        //output[0] - Destination(String) - must
        //output[1] - Report type (int) - must
        //output[2] - Filter date starting date(LocalDate), null if not defined,
        //output[3] - Filter date ending date(LocalDate), null if not defined,
        //output[4] - Employee filter surname_name(String), null if not defined,
        //output[5] - keyword filter(String), null if not defined
        //output[6] - export path(String), null if not defined

        Object[] output = new Object[7];

        CommandLineParser parser = new DefaultParser();
        Options options = new Options();

        //parsing options definition
        options.addOption("destination", true, "path of data files");
        options.addOption("reportType", true, "(1 - 4), type of report to be generated");
        options.addOption("dateFilter", true, "filter with date <dd/MM/yyyy-dd/MM/yyyy>");
        options.addOption("employeeFilter", true, "name of employee to be filtered");
        options.addOption("keyWordSearch", true, "keyword filter");
        options.addOption("export", true, "path to generated .xls report file");
        options.addOption("h", false, "prints help");

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {
                System.out.println("Available commands:\n" +
                        "-destination <path>                      path to source file / folders\n" +
                        "-reportType <1-4>                        choose report type\n" +
                        "-dateFilter <dd/MM/yyyy-dd/MM/yyyy>      choose date for filtering\n" +
                        "-employeeFilter <surname_name>           person filter surname_name\n" +
                        "-keyWordSearch <word>                    word filter\n" +
                        "-export <path\\filename.xls>             path to exported file\n" +
                        "-h                                       print help\n" +
                        "check ReadMe file for more information");
                System.exit(0);
            }

            if (!cmd.hasOption("destination")) {
                throw new MissingArgumentException("DESTINATION MUST BE DEFINED!");
            } else {
                output[0] = (String) cmd.getOptionValue("destination");
            }
            if (!cmd.hasOption("reportType")) {
                throw new MissingArgumentException("REPORT TYPE MUST BE DEFINED!");
            } else {
                output[1] = Integer.parseInt(cmd.getOptionValue("reportType"));
            }

            if (cmd.hasOption("dateFilter")) {
                boolean filterFromDate = cmd.getOptionValue("dateFilter").indexOf("-") == cmd.getOptionValue("dateFilter").length() - 1;
                boolean filterToDate = cmd.getOptionValue("dateFilter").indexOf("-") == 0;
                String[] dates = cmd.getOptionValue("dateFilter").split("-");
                if (!filterToDate && !filterFromDate) {
                    output[2] = LocalDate.parse(dates[0], FORMATTER);
                    output[3] = LocalDate.parse(dates[1], FORMATTER);
                } else if (filterFromDate && !filterToDate) {
                    output[2] = LocalDate.parse(dates[0], FORMATTER);
                } else if (!filterFromDate && filterToDate) {
                    output[3] = LocalDate.parse(dates[1], FORMATTER);
                } else {
                    throw new MissingArgumentException("WRONG DATE FORMAT!");
                }
            }

            if (cmd.hasOption("employeeFilter")) {
                output[4] = cmd.getOptionValue("employeeFilter");
            }
            if (cmd.hasOption("keyWordSearch")) {
                output[5] = cmd.getOptionValue("keyWordSearch");
            }
            if (cmd.hasOption("export")) {
                output[6] = cmd.getOptionValue("export");
            }


        } catch (MissingArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        }
        return output;
    }

}
