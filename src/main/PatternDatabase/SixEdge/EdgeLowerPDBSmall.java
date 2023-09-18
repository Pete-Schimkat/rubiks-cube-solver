package main.PatternDatabase.SixEdge;

import main.Enums.IndexEnums;
import main.Model.CubeModel;

public class EdgeLowerPDBSmall extends EdgePatternDatabaseSmall {

    public EdgeLowerPDBSmall() {

    }

    public int getDatabaseIndex(CubeModel cm) {
        int[] edgePerm = new int[6];
        int[] edgeOrientations = new int[6];
        int numIndexed = 0;
        byte edgeInd = 0;

        for (int i = 0; i < 12 && numIndexed != 6; i++) {
            edgeInd = cm.getEdgeIndex(IndexEnums.EDGE.values()[i]);

            if (edgeInd < 6) {
                edgePerm[edgeInd] = i;
                edgeOrientations[edgeInd] = cm.getEdgeOrientation(IndexEnums.EDGE.values()[i]);
                numIndexed++;
            }
        }
        return super.getDatabaseIndex(edgePerm, edgeOrientations);
    }
}
