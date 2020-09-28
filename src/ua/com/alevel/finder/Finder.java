package ua.com.alevel.finder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class Finder implements Callable<List<File>> {
    private String fileName, path;
    private File file;

    private String inputData(String message) {
        Scanner sc = new Scanner(System.in);
        System.out.println(message);
        String str = sc.nextLine();
        return str;
    }

    private void setInfoAboutFound() {
        String fileName = inputData("Enter the name of file or directory which you want to find");
        int a = Integer.parseInt(inputData("Find in /Users/vitalij/?(1 - YES, 0 - NO)"));
        File file = null;
        String path = "/Users/vitalij/";
        if (a == 1) {
            file = new File(path);
        }
        if (a == 0) {
            path = inputData("Enter the path");
            file = new File(path);
            if (!file.isDirectory()) {
                System.out.println("Incorrect path, chose default path");
                file = new File("/Users/vitalij/");
            }
        }
        this.fileName = fileName;
        this.path = path;
        this.file = file;
    }

    @Override
    public List<File> call() throws Exception {
        setInfoAboutFound();
        List<File> list = Arrays.asList(file.listFiles());
        List<File> result = findFiles(fileName, list, path);
        return result;
    }

    private List<File> findFiles(String fileName, List<File> list, String path) {
        List<File> tmp = new ArrayList<>();
        List<File> resultFromDerivedMethods = new ArrayList<>();
        String[] arrName = fileName.split("\\.");
        tmp = findFile(fileName, list, resultFromDerivedMethods);
        if (tmp.size() == 0) {
            if (arrName.length == 0) {
                tmp = findDir(fileName, list, resultFromDerivedMethods);
            } else {
                String format = "." + arrName[arrName.length - 1];
                tmp = findByFormat(format, list, resultFromDerivedMethods);
                if (tmp.size() == 0) {
                    tmp = findDir(fileName, list, resultFromDerivedMethods);
                }
            }
        }
        return tmp;
    }

    private List<File> findByFormat(String format, List<File> list, List<File> tmp) {
        for (File currentFile :
                list) {
            if (currentFile.isFile() && currentFile.getName().endsWith(format)) {
                tmp.add(currentFile);
            }
        }
        return tmp;
    }

    private List<File> findFile(String fileName, List<File> list, List<File> tmp) {
        for (File currentFile :
                list) {
            if (currentFile.isDirectory()) {
                findFile(fileName, Arrays.asList(currentFile.listFiles()), tmp);
            }
            if (currentFile.isFile() && currentFile.getName().equals(fileName)) {
                tmp.add(currentFile);
            }
        }
        return tmp;
    }

    private List<File> findDir(String fileName, List<File> list, List<File> tmp) {
        for (File currentFile :
                list) {
            if (currentFile.isDirectory() && !(currentFile.getName().equals(fileName))) {
                findDir(fileName, Arrays.asList(currentFile.listFiles()), tmp);
            }
            if (currentFile.isDirectory() && currentFile.getName().equals(fileName)) {
                tmp.add(currentFile);
            }
        }
        return tmp;
    }
}
