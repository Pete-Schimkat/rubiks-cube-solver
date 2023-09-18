package main.Utils;

import main.Model.CubeModel;
import main.PatternDatabase.PatternDatabase;
import main.Algorithms.PermutationIndexer;
import main.Enums.IndexEnums.EDGE;

/**
 * Database used for testing.
 */
public class TestPatternDatabase extends PatternDatabase {
    PermutationIndexer permIndexer;

    public TestPatternDatabase() {
        this (40320);
    }
    public TestPatternDatabase(long size) {
        super(size);
        this.permIndexer = new PermutationIndexer(8,8);
    }

    public static void main(String[] args) {
        TestPatternDatabase pdb = new TestPatternDatabase();
        CubeModel cm = new CubeModel();

        /*if(!pdb.readDatabaseFromFile("./src/Data/corner.pdb")){
            DatabaseGoal testGoal = new DatabaseGoal(pdb);
            TwistStore twistStore = new TwistStore(cm);

            System.out.println("Goal 1: "+testGoal.getDescription());
        }*/
    }

    public int getDatabaseIndex(CubeModel cm) {
        PermutationIndexer permIndexer = new PermutationIndexer(8,8);
        byte[] edgeMap = new byte[12];

        edgeMap[EDGE.UB.ordinal()] = 0;
        edgeMap[EDGE.UR.ordinal()] = 1;
        edgeMap[EDGE.UF.ordinal()] = 2;
        edgeMap[EDGE.UL.ordinal()] = 3;
        edgeMap[EDGE.DF.ordinal()] = 4;
        edgeMap[EDGE.DL.ordinal()] = 5;
        edgeMap[EDGE.DB.ordinal()] = 6;
        edgeMap[EDGE.DR.ordinal()] = 7;

        int[] edgeIndexes = new int[8];
        edgeIndexes[0] = edgeMap[cm.getEdgeIndex(EDGE.UB)];
        edgeIndexes[1] = edgeMap[cm.getEdgeIndex(EDGE.UR)];
        edgeIndexes[2] = edgeMap[cm.getEdgeIndex(EDGE.UF)];
        edgeIndexes[3] = edgeMap[cm.getEdgeIndex(EDGE.UL)];
        edgeIndexes[4] = edgeMap[cm.getEdgeIndex(EDGE.DF)];
        edgeIndexes[5] = edgeMap[cm.getEdgeIndex(EDGE.DL)];
        edgeIndexes[6] = edgeMap[cm.getEdgeIndex(EDGE.DB)];
        edgeIndexes[7] = edgeMap[cm.getEdgeIndex(EDGE.DR)];

        int rank = permIndexer.rank(edgeIndexes);

        return rank;
    }
}
