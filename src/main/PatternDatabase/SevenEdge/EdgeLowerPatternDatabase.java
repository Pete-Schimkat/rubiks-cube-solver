package main.PatternDatabase.SevenEdge;

import main.Enums.IndexEnums.EDGE;
import main.Model.CubeModel;

public class EdgeLowerPatternDatabase extends EdgePatternDatabase {

    public EdgeLowerPatternDatabase() {
    }

    public int getDatabaseIndex(CubeModel cm) {
        int[] edgePerm = new int[7];
        int[] edgeOrientations = new int[7];
        int numIndexed = 0;
        byte edgeInd;

        for (int i = 0; i < 12 && numIndexed != 7; i++) {
            edgeInd = cm.getEdgeIndex(EDGE.values()[i]);

            if (edgeInd < 7) {
                edgePerm[edgeInd] = i;
                edgeOrientations[edgeInd] = cm.getEdgeOrientation(EDGE.values()[i]);
                numIndexed++;
            }
        }
        return super.getDatabaseIndex(edgePerm, edgeOrientations);
    }
}
