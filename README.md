# AplsoftRed-report-system



-----------------------------------------------------------------------------
-----------------------------------------------------------------------------

This program may be used to generate reports.
Input files must be done using certain template. 
User can choose between 3 types of reports.

-----------------------------------------------------------------------------

To use with .jar enter:
java -jar filename.jar
before following commands

-----------------------------------------------------------------------------

**Commands required to run the program:**

- -destination
- -reportType

-----------------------------------------------------------------------------

**-destination** <input the source files path>


It may be catalog with several files or with single one.

e.g.

-destination C:/Users/Kowalski_Jan.xls

-destination C:/Users

-----------------------------------------------------------------------------

**-reportType** <choose between 1 and 3>

1: Displays all projects with their hours summary 

2: Displays all employees with their hours spent on each project

3: Displays all tasks with hours summary relating to each project


e.g.

-reportType 1

-----------------------------------------------------------------------------

**-dateFilter** <dd/MM/yyyy-dd/MM/yyyy>

This filter specifies range of time.

**-dateFilter** <dd/MM/yyyy->

This filter shows data from provided date till now.

**-dateFilter** <-dd/MM/yyyy>

This filter shows data from beginning till provided date.


e.g.

-dateFilter 01/01/2012-05/01/2012

-dateFilter 01/01/2012-

-dateFilter -05/01/2012

-----------------------------------------------------------------------------

**-employeeFilter** <surname_name>

This filter works only with the 2nd type report

e.g.

-employeeFilter kowalski_jan

-----------------------------------------------------------------------------

**-keyWordSearch** <word>

This filter works only with the 3rd type report

e.g.

-keyWordSearch analiza

-----------------------------------------------------------------------------
**-export** <path\filename.xls>

Generating Excel file with chosen report with provided file name to the provided path which already exists.

e.g.

-export  C:/Users/report.xls

-----------------------------------------------------------------------------
**-h**

Displays help.

e.g.

-h

-----------------------------------------------------------------------------
**proper examples to run the program**

java -jar reporter.jar -destination C:/Users/Desktop/Resources -reportType 1 -dateFilter -10/08/2020 -export C:/Users/Desktop/OutputReport.xls

**This command read** the directory tree and base on this it **creates** report of the 1st type from the beginning to provided date, **displays** all projects with hours summary and **exports** the report to the .xls file with name "OutputReport" to C:/Users/Desktop

java -jar reporter.jar -destination C:/Users/Desktop/Resources/ -reportType 2 -employeeFilter Kowalski_Jan

**This command reads** the directory tree and base on this it **creates** 2nd type report which **displays** hours summary spent on each project by Jan Kowalski

java -jar reporter.jar -destination C:/Users/Desktop/Resources -reportType 3 -dateFilter 5/02/2016-10/08/2020 -keyWordSearch analiza -export C:/Users/Desktop/OutputReport.xls

**This command reads** the directory tree and base on this it **creates** report of the 3rd type, **displays** data for provided date range (5/02/2016 - 10/08/2020) and summary for "analiza" task completed in each project and **exports** the report to the .xls file with name "OutputReport" to C:/Users/Desktop

java -jar reporter.jar -destination C:/Users/Desktop/Resources/Nowak_Piotr -reportType 3 -dateFilter 5/02/2016-10/08/2020 -keyWordSearch programowanie -export C:/Users/Desktop/OutputReport.xls

**This command reads** the provided file and base on this it creates 3rd type report, **displays** data for provided date range (5/02/2016 - 10/08/2020) and emplyee's hours summary for "programowanie" task completed in each project and **exports** the report to the .xls file with name "OutputReport" to C:/Users/Desktop












