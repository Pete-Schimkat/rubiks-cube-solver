package main.Tests;
import main.Model.CubeModel;
import main.Model.SimpleCubeModel;
import org.junit.Test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CubeModelTest {

    @Test
    public void testCubeModelSolves() {
        CubeModel cm = new CubeModel();
        String[] scrambles = new String[10];
        String[] solves = new String[10];
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
            cm.applyScramble(solves[j]);
            assertTrue(cm.isSolved());
        }
    }

    @Test
    public void testPrimitiveCubeModel(){
        int i = 0;
        String[] scrambles = new String[10];
        CubeModel cm =  new CubeModel();
        SimpleCubeModel pm;
        try(BufferedReader br = new BufferedReader(new FileReader("./src/main/Tests/scramblesWithSolves.txt"))) {
            String line = br.readLine();
            while(line != null){
                scrambles[i] = line;
                line = br.readLine();
                line = br.readLine();
                i++;
            }
        } catch (IOException io){
            io.printStackTrace();
        }
        for(int j = 0; j < 10; j ++) {
            cm.applyScramble(scrambles[j]);
            pm = new SimpleCubeModel(cm);
            assertEquals(cm.toString(), pm.toString());
        }
    }

    @Test
    public void testCubeModelConversion(){
        int i = 0;
        String[] scrambles = new String[10];
        CubeModel cm =  new CubeModel();
        CubeModel cm2;
        SimpleCubeModel pm;
        SimpleCubeModel pm2;
        try(BufferedReader br = new BufferedReader(new FileReader("./src/main/Tests/scramblesWithSolves.txt"))) {
            String line = br.readLine();
            while(line != null){
                scrambles[i] = line;
                line = br.readLine();
                line = br.readLine();
                i++;
            }
        } catch (IOException io){
            io.printStackTrace();
        }
        for(int j = 0; j < 10; j ++) {
            cm.applyScramble(scrambles[j]);
            pm = new SimpleCubeModel(cm);
            cm2 = new CubeModel(pm);
            pm2 = new SimpleCubeModel(cm2);
            assertTrue(pm.compareCubes(pm2));
            assertEquals(cm.toString(), cm2.toString()); // Check if information was lost during conversion from SimpleCubeModel
            assertEquals(cm.toString(), pm2.toString()); // Check if information was lost during conversion to SimpleCubeModel
        }
    }
}
