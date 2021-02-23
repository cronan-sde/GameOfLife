package com.cronan.gameoflife.util;

import com.cronan.gameoflife.game.Universe;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Done: make multiple pattern files, with specific names.
 *  Find a way to allow random access to reading the pattern files
 *  so user gets random patterns everytime upon pressing P key
 */
public class PatternCSVReadWrite {

    private static String sunCsvFile = "resources/pattern.csv";
    private static String shipCsvFile = "resources/ship.csv";

    private static String[] patternFiles = {sunCsvFile, shipCsvFile};

    /*
     * Only for me to use to input cool patterns for use by user
     */
    public static void writePatters(String patternCoords) {
        try (PrintWriter write = new PrintWriter(new FileWriter(shipCsvFile, true))) {
            write.println(patternCoords);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * read the csv file locations and add them to a list of directions to be updated
     * in game, utilizes getRandomIdx to load random csv file to get random patterns on
     * pressing P key
     */
    public static List<Universe.Direction> readPatterns() {
        String line = "";
        String csvSplit = ",";
        int randIdx = getRandomIdx();

        List<Universe.Direction> patternDirections = new ArrayList<>();

        //removing resources/ from front of filename to be used with classLoader get resources
        String filePathForPattern = patternFiles[randIdx].split("/")[1];

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(PatternCSVReadWrite.class.getResourceAsStream("/" + filePathForPattern))))) {

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

    private static int getRandomIdx() {
        return (int)(Math.random() * patternFiles.length);
    }
}