# AplsoftRed-report-system

$$$$$  $$$$$  $      $$$$$   $$$$$   $$$$$   $$$$$   $$$$$   $$$$$   $$$$
$   $  $   $  $      $       $   $   $         $     $   $   $       $   $
$   $  $   $  $      $       $   $   $         $     $   $   $       $   $
$   $  $$$$$  $      $$$$$   $   $   $$$$$     $     $$$$$   $$$$$   $   $
$$$$$  $      $          $   $   $   $         $     $ $     $       $   $
$   $  $      $          $   $   $   $         $     $  $    $       $   $
$   $  $      $$$$$  $$$$$   $$$$$   $         $     $   $   $$$$$   $$$$

-----------------------------------------------------------------------------
-----------------------------------------------------------------------------

This program may be used to generate reports.
Input files must be done using certain template. 
User can choose between 4 types of reports.

-----------------------------------------------------------------------------

To use with .jar enter:
java -jar ____
before following commands

-----------------------------------------------------------------------------

-destination <input the source files path>

It may be catalog with several files or with single one

e.g.

-destination resources

-----------------------------------------------------------------------------

-reportType <choose between 1 and 4>

1: Displays all projects with hours summary 

2: Displays all employees with their hours spent on each project

3: Displays all tasks with hours summary relating to each project (searching by the key word)

4: Displays all projects with monthly average hours per employee

e.g.

-destination -reportType 1

-----------------------------------------------------------------------------

-dateFilter <yyyy/mm/dd-yyyy/mm/dd>

This filter specifies range of time

e.g.

-destination -reportType 1 -dateFilter 2012/01/01-2012/01/05

-----------------------------------------------------------------------------

-employeeFilter <surname_name>

e.g.

-destination -reportType 1 -employeeFilter kowalski_jan

-destination -reportType 1 -employeeFilter _jan

-destination -reportType 1 -employeeFilter kowalski_

-----------------------------------------------------------------------------

-keyWordSearch

This filter works only with report of 4th type

e.g.

-destination -reportType 1 -keyWordSearch analiza

-----------------------------------------------------------------------------
-export <path\filename.xls>

Generating Excel file with chosen report with provided file name to the provided path which already exists.




