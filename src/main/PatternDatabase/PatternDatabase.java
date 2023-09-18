package main.PatternDatabase;
import main.Model.CubeModel;
import main.Utils.NibbleArray;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PatternDatabase {
    public NibbleArray database;
    private int numItems;
    private final long size;


    public PatternDatabase(long size){
        this.size = size;
        this.database = new NibbleArray(Math.toIntExact(size), (byte) 0xFF);
    }

    /**
     * Using the given index, set the number of moves required to get to the state located at that index.
     * If there already is a set state, the method checks if the given amount of moves is less than the saved value
     * and if so, replaces it with the newer value.
     * @param ind - given index for the database.
     * @param numMoves - Number of moves to get to the indexed state.
     * @return true if successful replacement, otherwise false.
     */
    public boolean setNumMoves(int ind, byte numMoves) {
        byte oldNumMoves = this.getNumMoves(ind);

        if(oldNumMoves == (byte) 0xF ){
            //System.out.println("does this ever happen?");
            this.numItems++;
        }
        if(oldNumMoves == (byte) 0xF || oldNumMoves > numMoves){
            this.database.set(ind, numMoves);
            return true;
        }
        return false;
    }

    public boolean setNumMoves(CubeModel cm, byte numMoves){
        return this.setNumMoves(this.getDatabaseIndex(cm), numMoves);
    }
    /**
     * Get the number of moves it takes from a solved state to get to a scrambled state.
     *
     * @param ind
     * @return
     */
    public byte getNumMoves(int ind) {
        return this.database.get(ind);
    }

    public byte getNumMoves(CubeModel cm) {
        return this.getNumMoves(this.getDatabaseIndex(cm));
    }

    public long getSize() {
        return this.size;
    }

    public long getNumItems() {
        return this.numItems;
    }

    /**
     * Returns true if the database is filled
     * @return
     */
    public boolean isFull() {
        return (this.numItems == this.size);
    }

    public int getDatabaseIndex(CubeModel cm) {return 0;
    }

    public void writeDatabaseToFile(String filePath) {
        try(FileOutputStream fos = new FileOutputStream(filePath)){
            fos.write(this.database.data);
        } catch(IOException e){
            e.printStackTrace();
        }
        //TODO
    }
    public boolean readDatabaseFromFile(String filePath) {
        //System.out.println(filePath);
        //InputStream iu;
        try (InputStream inputStream = PatternDatabase.class.getClassLoader().getResourceAsStream("main/resources/"+filePath)) {
            if (inputStream == null) return false;
            byte[] tempData = inputStream.readAllBytes();
            this.database = new NibbleArray(Math.toIntExact(tempData.length), (byte) 0xFF, true);
            this.database.data = tempData;
            this.numItems = Math.toIntExact(this.size);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return switch (Math.toIntExact(this.size)) {
            case 479001600 -> "Edge-Permutation Database";
            case 510935040 -> "7-Edge Database";
            case 42577920 -> "6-Edge Database";
            case 88179840 -> "Corner Database";
            default -> "Unknown Database";
        };
    }
    public void reset(){
        if(this.numItems != 0) {
            this.database.reset((byte) 0xFF);
            this.numItems = 0;
        }
    }
}
