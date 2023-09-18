package main.Tests;

import main.Model.CubeModel;
import main.Algorithms.KorfCubeSolver;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class IterativeDeepeningAStarTest {

    @Test
    public void testIDA(){
        CubeModel cm = new CubeModel();
        String[] scrambles = new String[10];
        String[] solves = new String[10];
        KorfCubeSolver kcs = new KorfCubeSolver(1);
        kcs.initialize();
        String solution = "";

        int i = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("./src/main/Tests/scramblesWithSolves.txt"))) {
            String line = br.readLine();
            while(line != null){
                scrambles[i] = line;
                line = br.readLine();
                solves[i] = line;
                line = br.readLine();
                i++;
            }
        } catch (IOException io){
            io.printStackTrace();
        }
        for(int j = 0; j < 10; j++) {
            cm.applyScramble(scrambles[j]);
            assertFalse(cm.isSolved());
            solution = kcs.solveCube(cm,true,1,false);
            assertTrue(cm.isSolved());
            assertEquals(solves[j].length(), solution.split(" ").length);
        }
    }
}
