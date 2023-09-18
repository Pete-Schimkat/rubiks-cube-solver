package main.Utils;

import main.Model.CubeModel;
import main.Enums.IndexEnums.MOVE;

import java.util.Arrays;
import java.util.List;

public class TwistStore {
    List<MOVE> moves;
    CubeModel cm;

    public TwistStore(CubeModel cm) {
        this.cm = cm;
        this.moves = Arrays.asList(MOVE.L, MOVE.LPRIME,MOVE.L2, MOVE.R,MOVE.RPRIME, MOVE.R2, MOVE.U, MOVE.UPRIME, MOVE.U2,
                MOVE.D, MOVE.DPRIME, MOVE.D2, MOVE.F, MOVE.FPRIME, MOVE.F2, MOVE.B, MOVE.BPRIME, MOVE.B2);

    }
    /**
     * Return the list of available moves.
     */
    public List<MOVE> getMoves() {
        return this.moves;
    }


    public MOVE getMove(int ind){
        return this.getMoves().get(ind);
    }

    public byte getNumMoves() {
        int size = this.getMoves().size();
        if (size > 127) {
            System.out.println("Check getNumMoves. this should never happen");
        }
        return ((byte) this.getMoves().size());
    }
    /**
     * Move using an index.
     */

    /**
     * Move using an index.
     */
    public void move(byte ind) {
        this.cm.move(this.getMove(ind));
    }
    /**
     * Undo a move.
     */
    public void invert(byte ind){
        this.cm.invert(MOVE.values()[ind]);
    }

}
