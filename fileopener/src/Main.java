import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//this app opens a random movie file from its folder including all subfolders

class FileOperations {

    public static final String[] DEFAULT_FILTERS = {"wmv", "avi", "mp4", "mpg", "mkv"};

    private File rootPath;
    private ArrayList<String> filteredFileTypes;
    private ArrayList<File> listOfFoundFiles;
    private ArrayList<File> listOfFoundDirs;

    public FileOperations(File rootPath) {
        this.rootPath = rootPath;
        this.filteredFileTypes = new ArrayList<>(Arrays.asList(DEFAULT_FILTERS));
        this.listOfFoundFiles = new ArrayList<>();
        this.listOfFoundDirs = new ArrayList<>();

        execute();
    }

    public void execute() {
        collectFilteredItemsFromFolder(rootPath); //init first load
        for (int i = 0; i < listOfFoundDirs.size(); i++)
            collectFilteredItemsFromFolder(listOfFoundDirs.get(i));

        if (listOfFoundFiles.isEmpty()) {
            System.out.println("Folder does not contain relevant files");
        } else {
            try {
                File randomFile = listOfFoundFiles.get(getRandomNumber(listOfFoundFiles.size()));
                openFile(randomFile);
                System.out.println("Opening: " + randomFile.getName());
            } catch (Exception e) {
                System.out.println("Opening file failed");
            }
        }
    }

    private void collectFilteredItemsFromFolder(File startPath) {
        File[] files = startPath.listFiles();

        if (files == null)
            return;

        for (File file : files) {
            if (file.isDirectory()) {
                listOfFoundDirs.add(file);
            } else if (isFilteredType(file)) {
                listOfFoundFiles.add(file);
            }
        }
    }

    private void openFile(File file) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
    }

    private int getRandomNumber(int max) {
        return new Random().nextInt(max);
    }

    private boolean isFilteredType(File file) {
        boolean filtered = false;
        for (String fileType : filteredFileTypes)
            if (file.getName().endsWith("." + fileType)) {
                filtered = true;
                break;
            }
        return filtered;
    }
}

public class Main {

    public static void main(String[] args) {

        FileOperations lookup = new FileOperations(new File(System.getProperty("user.dir")));

        System.exit(0);
    }
}
