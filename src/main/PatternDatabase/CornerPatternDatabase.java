package main.PatternDatabase;

import main.Model.CubeModel;
import main.Enums.IndexEnums.CORNER;
import main.PatternDatabase.PatternDatabase;
import main.Algorithms.PermutationIndexer;

public class CornerPatternDatabase extends PatternDatabase {

    public PermutationIndexer permIndexer;

    public CornerPatternDatabase() {
        super(88179840);
        this.permIndexer = new PermutationIndexer(8, 8);
    }

    public int getDatabaseIndex(CubeModel cm) {
        int[] cornerPerm = new int[8];
        cornerPerm[0] = cm.getCornerIndex(CORNER.ULB);
        cornerPerm[1] = cm.getCornerIndex(CORNER.URB);
        cornerPerm[2] = cm.getCornerIndex(CORNER.URF);
        cornerPerm[3] = cm.getCornerIndex(CORNER.ULF);
        cornerPerm[4] = cm.getCornerIndex(CORNER.DLF);
        cornerPerm[5] = cm.getCornerIndex(CORNER.DLB);
        cornerPerm[6] = cm.getCornerIndex(CORNER.DRB);
        cornerPerm[7] = cm.getCornerIndex(CORNER.DRF);
        //Get
        int rank = this.permIndexer.rank(cornerPerm);

        int[] cornerOrientations = new int[7];
        cornerOrientations[0] = cm.getCornerOrientation(CORNER.ULB);
        cornerOrientations[1] = cm.getCornerOrientation(CORNER.URB);
        cornerOrientations[2] = cm.getCornerOrientation(CORNER.URF);
        cornerOrientations[3] = cm.getCornerOrientation(CORNER.ULF);
        cornerOrientations[4] = cm.getCornerOrientation(CORNER.DLF);
        cornerOrientations[5] = cm.getCornerOrientation(CORNER.DLB);
        cornerOrientations[6] = cm.getCornerOrientation(CORNER.DRB);

        //Assume that the orientations are base-3 numbers, convert them to base 10 and add them
        int orientationNumber = cornerOrientations[0] * 729 +
                cornerOrientations[1] * 243 +
                cornerOrientations[2] * 81 +
                cornerOrientations[3] * 27 +
                cornerOrientations[4] * 9 +
                cornerOrientations[5] * 3 +
                cornerOrientations[6];

        return ((rank * 2187) + orientationNumber);
    }
}
