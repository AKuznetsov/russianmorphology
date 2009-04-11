package org.apache.lucene.russian.morphology.dictonary;

import java.util.Set;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;


public class IgnoredFormReader {
    private String fileName;
    private String fileEncoding = "windows-1251";

    public IgnoredFormReader(String fileName) {
        this.fileName = fileName;
    }

    public IgnoredFormReader(String fileName, String fileEncoding) {
        this.fileName = fileName;
        this.fileEncoding = fileEncoding;
    }

    public Set<String> getIngnoredFroms() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName), fileEncoding));
        String s = bufferedReader.readLine();
        HashSet<String> result = new HashSet<String>();
        while (s != null) {
            if (!s.startsWith("//")) {
                result.add(s.trim().split(" ")[0]);
            }
            s = bufferedReader.readLine();
        }
        return result;
    }
}
