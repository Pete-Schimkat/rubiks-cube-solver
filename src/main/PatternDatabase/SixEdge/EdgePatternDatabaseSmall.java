package main.PatternDatabase.SixEdge;

import main.Model.CubeModel;
import main.PatternDatabase.PatternDatabase;
import main.Algorithms.PermutationIndexer;

public class EdgePatternDatabaseSmall extends PatternDatabase {
    public PermutationIndexer permIndexer;

    /**
     * Initializes the Edge-PDB storage, needs to hold 12P6 * 2^6 possible scrambles.
     * (6 edges in any of 12 total positions with an orientation of either 0 or 1.
     */
    public EdgePatternDatabaseSmall() {
        super(42577920);
        this.permIndexer = new PermutationIndexer(12,6);
    }

    public int getDatabaseIndex(int[] edgePerm, int[] edgeOrientations) {
        int rank = this.permIndexer.rank(edgePerm);
        int orientationNumber = edgeOrientations[0] * 32 +
                edgeOrientations[1] * 16 +
                edgeOrientations[2] * 8 +
                edgeOrientations[3] * 4 +
                edgeOrientations[4] * 2 +
                edgeOrientations[5];

        return rank * 64 + orientationNumber;
    }

    public int getDatabaseIndex(CubeModel cm) {
        return 0;
    }

}
