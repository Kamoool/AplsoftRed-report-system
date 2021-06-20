package pl.edu.agh.mwo.reporter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.cli.*;
import pl.edu.agh.mwo.export.XlsExporter;
import pl.edu.agh.mwo.fileBrowser.FileBrowser;
import pl.edu.agh.mwo.reporter.model.*;
import pl.edu.agh.mwo.workbook.WorkbookReader;

public class Main {
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) throws IOException {

        Object[] parsedArguments = parseArguments(args);

        //TODO - SEND DATA TO FUNCTIONS

        //TODO - SEND DATA TO FOLDER PARSER - DONE
        FileBrowser fileBrowser = new FileBrowser("xls");
        List<String> filePaths = fileBrowser.browse((String) parsedArguments[0]); // C://Users/cos/workbooks    lub C://Users/cos/workbooks/Kowalski_jan.xls
        filePaths.forEach(f -> System.out.println(f));

        //TODO - SEND DATA TO WORKBOOK LOADER -DONE
        WorkbookReader wr = new WorkbookReader(filePaths);
        Company company = wr.getCompany();
        System.out.println(company.getEmployees().size());

        //TODO - CHOOSE CORRECT REPORT TYPE -DONE
        IReport report = handleReportType((int) parsedArguments[1], company);
        report.printReport();

        //TODO - EXPORT REPORT TO XLS
        XlsExporter xlsExporter = new XlsExporter();
        String outputPath = "EmplRep_" + new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date(System.currentTimeMillis())) + ".xls";
        xlsExporter.export(outputPath,report);
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
