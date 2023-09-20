package main.Tests;

import main.Enums.IndexEnums.MOVE;
import main.Model.CubeModel;
import main.Utils.MovePruner;
import main.Algorithms.KorfCubeSolver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * Main use will be to generate random scrambles for the solver to solve.
 */
public class ScrambleGenerator {
    private static final String filePath = "./src/Data/Scrambles/";
    private static final Random random = new Random();

    private static final MOVE[] moveValues = MOVE.values();
    private static final MovePruner pruner = new MovePruner();
    public static void main(String[] args) {
         ScrambleGenerator sg = new ScrambleGenerator();
         for(int i = 16; i < 20;i++) {
             sg.generateScrambles(i, 10);

         }
         //sg.validateMoveset();
    }


    public void generateScrambles(int length, int amountOfScrambles){
        StringBuilder scramblePlaceholder = new StringBuilder();
        String movePlaceholder;
        for(int j = 0; j < amountOfScrambles; j++) {
            String lastMovePlaceholder = CubeModel.getMove(moveValues[random.nextInt(18)]);
            scramblePlaceholder.setLength(0);
            scramblePlaceholder.append(lastMovePlaceholder).append(" ");

            for (int i = 0; i < length - 1; ) {
                movePlaceholder = CubeModel.getMove(moveValues[random.nextInt(18)]);
                if (!pruner.prune(movePlaceholder, lastMovePlaceholder)) {
                    scramblePlaceholder.append(movePlaceholder).append(" ");
                    lastMovePlaceholder = movePlaceholder;
                    i++;
                }
            }
            String pathName = filePath.concat("solvableIn" + length + "Moves");
            try (FileWriter fw = new FileWriter(pathName, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(scramblePlaceholder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //System.out.println(scramblePlaceholder);
    }
    public void validateMoveset() {
    String[] scrambles;
    List<String> lines;
    String fileName;
    String solution;
    CubeModel cm = new CubeModel();
        KorfCubeSolver kcs = new KorfCubeSolver(1);
        kcs.initialize();
        for(int i = 3; i < 12;i++) {
            fileName = filePath.concat("solvableIn"+i+"Moves");
            try {
                lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
                scrambles = lines.toArray(lines.toArray(new String[0]));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for(int j = 0; j < scrambles.length; j++) {
                cm.applyScramble(scrambles[j]);
                solution = kcs.solveCube(cm,false, 1,false);
                //System.out.println(solution.split(" ").length);
                cm.applyScramble(solution);
                if(solution.split(" ").length != i) {
                    System.out.println("ALARM at line "+j+": "+ i +" was expected but  "+solution.split(" ").length+   " was found.");
                }
            }

        }
    }
}
