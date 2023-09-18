package main.Utils;

import main.Model.CubeModel;
import main.Enums.IndexEnums.MOVE;

public class IDAStarNodeWithParent {
    public IDAStarNodeWithParent parent;
    public CubeModel cm;
    public MOVE move;
    public byte depth;
    public byte estimatedSuccessMoves;

    public IDAStarNodeWithParent(IDAStarNodeWithParent parent, CubeModel cm, MOVE move, byte depth, byte estimatedSuccessMoves){
        this.parent = parent;
        this.cm = cm;
        this.move = move;
        this.depth = depth;
        this.estimatedSuccessMoves = estimatedSuccessMoves;
    }
}
