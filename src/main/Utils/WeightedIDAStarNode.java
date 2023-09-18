package main.Utils;

import main.Enums.IndexEnums.MOVE;
import main.Model.CubeModel;

public class WeightedIDAStarNode {
    public WeightedIDAStarNode parent;
    public CubeModel cm;
    public MOVE move;
    public byte depth;
    public double estimatedSuccessMoves;

    public WeightedIDAStarNode(WeightedIDAStarNode parent, CubeModel cm, MOVE move, byte depth, double estimatedSuccessMoves){
        this.parent = parent;
        this.cm = cm;
        this.move = move;
        this.depth = depth;
        this.estimatedSuccessMoves = estimatedSuccessMoves;
    }

    public WeightedIDAStarNode(WeightedIDAStarNode a) {
        this.parent = a.parent;
        this.cm = new CubeModel(a.cm);
        this.move= a.move;
        this.depth = a.depth;
        this.estimatedSuccessMoves = a.estimatedSuccessMoves;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final WeightedIDAStarNode other = (WeightedIDAStarNode) obj;
        return this.cm.equals(other.cm);
    }
}
