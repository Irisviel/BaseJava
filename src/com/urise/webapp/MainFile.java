package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainFile {

    static final String FILE_PATH = ".\\.gitignore";
    static final String PROJECT_PATH = "./src/com/urise/webapp";

    public static void main(String[] args) {

        File file = new File(FILE_PATH);
        try {
            System.out.println(file.getCanonicalPath() + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Unable to locate file " + FILE_PATH, e);
        }

        File dir = new File(PROJECT_PATH);
        System.out.println("\"" + PROJECT_PATH + "\"" + " is a directory: " + dir.isDirectory() + "\n");
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }
        System.out.println();

        try (FileInputStream fis = new FileInputStream(FILE_PATH)) {
            System.out.println("First byte of " + FILE_PATH + ": " + fis.read() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        printDirectoryTree(dir);
    }

    public static void printDirectoryTree(File dir){
        ArrayList<String> indentPool = new ArrayList<>();
        printDirectoryRecursive(dir, 0, indentPool);
    }

    public static void printDirectoryRecursive(File dir, int level, ArrayList<String> indentPool) {
        File[] files = dir.listFiles();

        if (indentPool.size() - 1 < level) {
            indentPool.add(String.join("", Collections.nCopies(level, "  ")));
        }

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(indentPool.get(level) + "File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(indentPool.get(level) + "Directory: " + file.getName());
                    printDirectoryRecursive(file, level + 1, indentPool);
                }
            }
        }
    }
}
