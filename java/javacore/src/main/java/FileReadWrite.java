package main.java;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by zhoufeng on 16/5/15.
 */
public class FileReadWrite {
    public static void main(String[] args) throws java.io.IOException {
        String fileName = Paths.get(System.getProperty("user.dir"), "mytest.txt").toString();
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName.toString()));
        writer.write("line1\nline2\nline3\n");
        writer.close();
        BufferedReader reader = new BufferedReader(new FileReader(fileName.toString()));
        String line;
        while((line = reader.readLine()) != null) {
            System.out.println(line);

        }
    }
}
