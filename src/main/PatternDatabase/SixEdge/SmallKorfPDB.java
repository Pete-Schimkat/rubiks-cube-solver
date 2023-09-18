package main.PatternDatabase.SixEdge;

import main.Model.CubeModel;
import main.PatternDatabase.*;
import main.PatternDatabase.CornerPatternDatabase;
import main.PatternDatabase.EdgePermutationPatternDatabase;

import java.util.ArrayList;

public class SmallKorfPDB extends PatternDatabase {
    private boolean inflated = false;

    CornerPatternDatabase cornerPDB;
    EdgeLowerPDBSmall edge1PDB;
    EdgeHigherPDBSmall edge2PDB;
    EdgePermutationPatternDatabase edgePermPDB;

    ArrayList<Byte> inflatedCornerPDB;
    ArrayList<Byte> inflatedEdge1PDB;
    ArrayList<Byte> inflatedEdge2PDB;
    ArrayList<Byte> inflatedEdgePermPDB;

    public SmallKorfPDB(CornerPatternDatabase cornerPDB, EdgeLowerPDBSmall edge1PDB, EdgeHigherPDBSmall edge2PDB, EdgePermutationPatternDatabase edgePermPDB) {
        super(0);
        this.cornerPDB = cornerPDB;
        this.edge1PDB = edge1PDB;
        this.edge2PDB = edge2PDB;
        this.edgePermPDB = edgePermPDB;
    }

    public byte getNumMoves(CubeModel cm) {
        byte cornerMoves;
        byte edge1Moves;
        byte edge2Moves;
        byte edgePermMoves = 0;

        if (this.inflated) {
            cornerMoves = this.inflatedCornerPDB.get(this.cornerPDB.getDatabaseIndex(cm));
            edge1Moves = this.inflatedEdge1PDB.get(this.edge1PDB.getDatabaseIndex(cm));
            edge2Moves = this.inflatedEdge2PDB.get(this.edge2PDB.getDatabaseIndex(cm));
        //    edgePermMoves = this.inflatedEdgePermPDB.get(this.edgePermPDB.getDatabaseIndex(cm));
        } else {
            cornerMoves = this.cornerPDB.getNumMoves(cm);
            edge1Moves = this.edge1PDB.getNumMoves(cm);
            edge2Moves = this.edge2PDB.getNumMoves(cm);
            //edgePermMoves = this.edgePermPDB.getNumMoves(cm);

        }
        byte max = cornerMoves;
        if (edge1Moves > max) max = edge1Moves;
        if (edge2Moves > max) max = edge2Moves;
        //if (edgePermMoves > max) max = edgePermMoves;

        return max;
    }
    public boolean setNumMoves(CubeModel cm, byte numMoves) {
        boolean corner = this.cornerPDB.setNumMoves(cm, numMoves);
        boolean edge1 = this.edge1PDB.setNumMoves(cm, numMoves);
        boolean edge2 = this.edge2PDB.setNumMoves(cm, numMoves);
        boolean edgePerm = this.edgePermPDB.setNumMoves(cm, numMoves);

        return (corner || edge1 || edge2 || edgePerm);
    }

    /**
     * Returns true if all pattern-databases are full.
     */
    public boolean isFull() {
        return this.cornerPDB.isFull() && this.edge1PDB.isFull() && this.edge2PDB.isFull() && this.edgePermPDB.isFull();
    }

    public void reset() {
        this.inflated = false;

        this.cornerPDB.reset();
        this.edge1PDB.reset();
        this.edge2PDB.reset();
        this.edgePermPDB.reset();
    }

    @Override
    public boolean setNumMoves(int ind, byte numMoves) {
        System.out.println("1This is not implemented.");
        return false;
    }

    @Override
    public byte getNumMoves(int ind) {
        System.out.println("2This is not implemented.");
        return 0;
    }

    @Override
    public long getSize() {
        System.out.println("3This is not implemented.");
        return 0;
    }

    @Override
    public long getNumItems() {
        System.out.println("4This is not implemented.");
        return 0;
    }

    @Override
    public int getDatabaseIndex(CubeModel cm) {
        System.out.println("5This is not implemented.");
        return 0;
    }

    @Override
    public boolean readDatabaseFromFile(String filePath) {
        System.out.println("6This is not implemented.");
        return false;
    }

    @Override
    public void writeDatabaseToFile(String filePath) {
        System.out.println("7This is not implemented.");
    }
}

