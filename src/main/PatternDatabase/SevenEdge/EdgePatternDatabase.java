package main.PatternDatabase.SevenEdge;

import main.Model.CubeModel;
import main.Algorithms.PermutationIndexer;
import main.PatternDatabase.PatternDatabase;

public class EdgePatternDatabase extends PatternDatabase {
    public PermutationIndexer permIndexer;

    /**
     * Initializes the Edge-PDB storage, needs to hold 12P7 * 2^7 possible scrambles.
     * (7 edges in any of 12 total positions with an orientation of either 0 or 1.
     */
    public EdgePatternDatabase() {
        super(510935040);
        this.permIndexer = new PermutationIndexer(12, 7);
    }

    public int getDatabaseIndex(int[] edgePerm, int[] edgeOrientations) {
        int rank = this.permIndexer.rank(edgePerm);
        int orientationNumber = edgeOrientations[0] * 64 +
                edgeOrientations[1] * 32 +
                edgeOrientations[2] * 16 +
                edgeOrientations[3] * 8 +
                edgeOrientations[4] * 4 +
                edgeOrientations[5] * 2 +
                edgeOrientations[6];

        return rank * 128 + orientationNumber;
    }

    public int getDatabaseIndex(CubeModel cm) {
        return 0;
    }

}
