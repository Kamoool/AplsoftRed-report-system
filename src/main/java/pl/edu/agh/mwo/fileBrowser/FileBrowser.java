package pl.edu.agh.mwo.fileBrowser;
import pl.edu.agh.mwo.workbook.WorkbookReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileBrowser {

    public static List<String> browse(String path) {
        List<String> files = new ArrayList<>();

        File startFile = new File(path);
        if(!startFile.isDirectory()){
            files.add(path);
            return files;
        } else {
          return  browseTree(path, path, files);
        }

    }

    private static List<String> browseTree(String dirPath, String startPath,  List<String> collectedFiles ) {

        File startFile = new File(dirPath);

        File[] listOfFiles = startFile.listFiles();
        startPath += File.separator + startFile.getName();

        for (File file : listOfFiles) {
            if (!file.isDirectory()) {
                collectedFiles.add(startPath + File.separator + file.getName());
            } else {
                browseTree(dirPath + File.separator + file.getName(), startPath, collectedFiles);
            }
        }
        return collectedFiles;
    }

}
