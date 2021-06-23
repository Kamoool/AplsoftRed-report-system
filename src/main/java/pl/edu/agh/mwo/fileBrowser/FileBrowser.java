package pl.edu.agh.mwo.fileBrowser;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileBrowser {
    private String fileExtension;

    public FileBrowser(String fileExtension){
        this.fileExtension = fileExtension;
    }

    public List<String> browse(String path) {
        List<String> files = new ArrayList<>();

        File startFile = new File(path);
        if(!startFile.isDirectory()){
            if(correctFileExtension(startFile, fileExtension)) {
                files.add(path);
            }
        } else {
          return  browseTree(path, path, files);
        }
        return files;
    }

    private List<String> browseTree(String dirPath, String startPath,  List<String> collectedFiles ) {

        File startFile = new File(dirPath);

        File[] listOfFiles = startFile.listFiles();

        for (File file : listOfFiles) {
            if (!file.isDirectory()) {
               if(correctFileExtension(file, fileExtension)) {
                   collectedFiles.add(startPath + File.separator + file.getName());
               }
            } else {
                startPath += File.separator + file.getName();
                browseTree(dirPath + File.separator + file.getName(), startPath, collectedFiles);
            }
        }
        return collectedFiles;
    }

    private static boolean correctFileExtension(File file, String extension){
        return FilenameUtils.getExtension(file.getName()).equals(extension);
    }
}
