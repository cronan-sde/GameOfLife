package com.cronan.gameoflife.util;

import com.cronan.gameoflife.game.Universe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PatternCSVReadWrite {

    private static String csvFile = "src/pattern.csv";

    public static void writePatters(String patternCoords) {
        try (PrintWriter write = new PrintWriter(new FileWriter(csvFile, true))) {
            write.println(patternCoords);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Universe.Direction> readPatterns() {
        String line = "";
        String csvSplit = ",";

        List<Universe.Direction> patternDirections = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                String[] coords = line.split(csvSplit);
                int x = Integer.parseInt(coords[0].trim());
                int y = Integer.parseInt(coords[1].trim());
                patternDirections.add(new Universe.Direction(x, y));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return patternDirections;
    }
}