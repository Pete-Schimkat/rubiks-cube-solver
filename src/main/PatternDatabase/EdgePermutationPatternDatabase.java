package main.PatternDatabase;

import main.Enums.IndexEnums.EDGE;
import main.Model.CubeModel;
import main.Algorithms.PermutationIndexer;

public class EdgePermutationPatternDatabase extends PatternDatabase {
    private PermutationIndexer permIndexer;

    public EdgePermutationPatternDatabase() {
        super(479001600);
        this.permIndexer= new PermutationIndexer(12,12);
    }

    public int getDatabaseIndex(CubeModel cm) {
        int[] edgePerm = new int[12];
        for(int i= 0; i <12; i++){
            edgePerm[i] = cm.getEdgeIndex(EDGE.values()[i]);
        }
        return this.permIndexer.rank(edgePerm);
    }
}
