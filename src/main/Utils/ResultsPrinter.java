package main.Utils;

import main.Algorithms.KorfCubeSolver;
import main.Model.CubeModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ResultsPrinter {
    private static final String filePath = "main/resources/Scrambles/";
    private static final String resultPath = "";
    private static final String resultName = "ResultsWithWeight";

    public static void main(String[] args) {
        //calculateResults(1, 14, 16, 10);
        calculateResults(1, 3,9,10);
        //calculateResultsScramble(2.5, "F R2 U' F' D' B' D2 L R' F B R2 D2 R2 D2 F2 U2 F' B L R U2 L2 F2 L2 B' D F U R2 F' ");
        //calculateResults(1, 9,10,1);
        //calculateResults(2.5, 13,14,1);
    }

    public static void calculateResults(double weight, int solveRangeStart, int solveRangeEnd, int amount) {
        String[] scrambles;
        String fileName;
        String solution;
        long start;
        long end;
        if(solveRangeStart <3) {
            int temp = 3- solveRangeStart;
            solveRangeStart = 3;
            solveRangeEnd += temp;
        }
        CubeModel cm = new CubeModel();
        KorfCubeSolver kcs = new KorfCubeSolver(weight);
        kcs.initialize();
        for(int i = solveRangeStart; i < solveRangeEnd;i++) {
            fileName = filePath.concat("solvableIn" + i + "Moves");
            try (InputStream inputStream = ResultsPrinter.class.getClassLoader().getResourceAsStream(fileName)) {
                if (inputStream == null){System.out.println("Empty input-stream") ;return; }
                scrambles = readFileLines(inputStream);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (int j = 0; j < amount; j++) {
                cm.applyScramble(scrambles[j]);
                start = System.currentTimeMillis();
                solution = kcs.solveCube(cm, false, weight, false);
                end = System.currentTimeMillis();
                storeResult(end-start, weight);
                storeSolution(solution, weight);
                cm.applyScramble(solution);
                if (solution.split(" ").length != i) {
                    System.out.println("ALARM at line " + j + ": " + i + " was expected but  " + solution.split(" ").length + " was found.");
                }
            }
            newLineInResults(weight);
            newLineInSolutions(weight);
        }
    }
    public static void calculateResultsScramble(double weight, String scramble) {
        String[] scrambles;
        List<String> lines;
        String fileName;
        String solution;
        long start;
        long end;
        CubeModel cm = new CubeModel();
        KorfCubeSolver kcs = new KorfCubeSolver(weight);
        kcs.initialize();

            cm.applyScramble(scramble);
            start = System.currentTimeMillis();
            solution = kcs.solveCube(cm, false, weight, false);
            end = System.currentTimeMillis();
            storeResult(end-start, weight);
            //System.out.println(solution.split(" ").length);
            storeSolution(solution, weight);
            cm.applyScramble(solution);
            if (solution.split(" ").length != scramble.length()) {
                System.out.println("ALARM  " +"was expected but  " + solution.split(" ").length + " was found.");
            }
            newLineInResults(weight);
            newLineInSolutions(weight);
        }

    public static void storeResult(long timeNeeded, double weight) {
        String pathName = resultPath.concat(resultName+weight+".txt");
        try (FileWriter fw = new FileWriter(pathName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(timeNeeded+",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void storeSolution(String solution, double weight) {
        String pathName = resultPath.concat(resultName+weight+"Solutions.txt");
        try (FileWriter fw = new FileWriter(pathName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(solution+",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void newLineInSolutions(double weight) {
        String pathName = resultPath.concat(resultName+weight+"Solutions.txt");
        try (FileWriter fw = new FileWriter(pathName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void newLineInResults(double weight) {
        String pathName = resultPath.concat(resultName+weight+".txt");
        try (FileWriter fw = new FileWriter(pathName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] readFileLines(InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream)) {
            List<String> lines = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }

            return lines.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}
