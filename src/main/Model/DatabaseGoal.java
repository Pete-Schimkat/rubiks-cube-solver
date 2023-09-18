package main.Model;

import main.PatternDatabase.PatternDatabase;

public class DatabaseGoal {
    public PatternDatabase pdb;

    public DatabaseGoal(PatternDatabase pdb){
        this.pdb = pdb;
    }
    public boolean isSatisfied() {
        return this.pdb.isFull();
    }
    public String getDescription() { return "Indexing "+this.pdb.toString()+"...";
    }
    public boolean index(CubeModel cm, byte numMoves) {
        return this.pdb.setNumMoves(cm, numMoves);
    }
    public int getDatabaseIndex(CubeModel cm) {
        return this.pdb.getDatabaseIndex(cm);
    }
}
