package com.example.moblab10;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileOperations {
    public static void writeToFile(String file, String str) {
        try {
            File f = new File(file);
            if (!f.exists()) {
                f.createNewFile();
            }

            FileWriter fw = new FileWriter(f.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(str + "\n");
            bw.close();
        } catch (Exception err) {
        }
    }

    public static void writeToFile(String file, String str, boolean append) {
        try {
            File f = new File(file);
            if (!f.exists()) {
                f.createNewFile();
            }

            FileWriter fw = new FileWriter(f.getAbsoluteFile(), append);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(str + "\n");
            bw.close();
        } catch (Exception err) {
        }
    }
}
