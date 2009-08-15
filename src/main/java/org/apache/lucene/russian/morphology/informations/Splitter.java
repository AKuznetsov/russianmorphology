package org.apache.lucene.russian.morphology.informations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Splitter {
    int[][] separators;

    public Splitter(String fileName) throws IOException {
        readFromFile(fileName);
    }

    public Splitter(int[][] separators) {
        this.separators = separators;
    }

    public void writeToFile(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(separators.length + "\n");
        for (int[] i : separators) {
            writer.write(i.length + "\n");
            for (int j : i) {
                writer.write(j + "\n");
            }
        }
        writer.close();
    }

    public void readFromFile(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String s = bufferedReader.readLine();
        Integer amount = Integer.valueOf(s);
        separators = new int[amount][];
        for (int i = 0; i < amount; i++) {
            String s1 = bufferedReader.readLine();
            Integer wordLenght = Integer.valueOf(s1);
            separators[i] = new int[wordLenght];
            for (int j = 0; j < wordLenght; j++) {
                separators[i][j] = Integer.valueOf(bufferedReader.readLine());
            }
        }
        bufferedReader.close();
    }
}
