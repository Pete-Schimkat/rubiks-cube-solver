package main.Utils;

import main.Enums.IndexEnums.MOVE;


import java.util.LinkedList;

public class MovePruner {
    /**
     * Check if the next move should be skipped.
     *
     * @param move  The next move, not yet applied.
     * @param moves A vector of moves leading up to move.
     */
    public boolean prune(String move, LinkedList<String> moves) {
        if (moves.size() == 0) {
            return false;
        }
        String lastMove = moves.getLast();
        return this.prune(move, lastMove);
    }

    /**
     * Check if the next move should be skipped.
     *
     * @param move     The next move, not yet applied.
     * @param lastMove The previous move.
     */
    public boolean prune(String move, String lastMove) {
        if (move.charAt(0) == lastMove.charAt(0)) return true;
        if (move.charAt(0) == 'U' && lastMove.charAt(0) == 'D') return true;
        if (move.charAt(0) == 'L' && lastMove.charAt(0) == 'R') return true;
        if (move.charAt(0) == 'F' && lastMove.charAt(0) == 'B') return true;
        return false;
    }

    /**
     * Check if the next move should be skipped.
     *
     * @param move     The next move, not yet applied.
     * @param lastMove The previous move.
     */
    public boolean prune(MOVE move, MOVE lastMove) {

        // Commutative moves.
        if ((move == MOVE.F || move == MOVE.FPRIME || move == MOVE.F2) &&
                (lastMove == MOVE.B || lastMove == MOVE.BPRIME || lastMove == MOVE.B2)) {
            return true;
        }

        if ((move == MOVE.L || move == MOVE.LPRIME || move == MOVE.L2) &&
                (lastMove == MOVE.R || lastMove == MOVE.RPRIME || lastMove == MOVE.R2)) {
            return true;
        }

        if ((move == MOVE.U || move == MOVE.UPRIME || move == MOVE.U2) &&
                (lastMove == MOVE.D || lastMove == MOVE.DPRIME || lastMove == MOVE.D2)) {
            return true;
        }

        //Additive moves (twisting the same face twice)
        if ((move == MOVE.L || move == MOVE.LPRIME || move == MOVE.L2) &&
                (lastMove == MOVE.L || lastMove == MOVE.LPRIME || lastMove == MOVE.L2)) {
            return true;
        }

        if ((move == MOVE.R || move == MOVE.RPRIME || move == MOVE.R2) &&
                (lastMove == MOVE.R || lastMove == MOVE.RPRIME || lastMove == MOVE.R2)) {
            return true;
        }

        if ((move == MOVE.U || move == MOVE.UPRIME || move == MOVE.U2) &&
                (lastMove == MOVE.U || lastMove == MOVE.UPRIME || lastMove == MOVE.U2)) {
            return true;
        }

        if ((move == MOVE.D || move == MOVE.DPRIME || move == MOVE.D2) &&
                (lastMove == MOVE.D || lastMove == MOVE.DPRIME || lastMove == MOVE.D2)) {
            return true;
        }

        if ((move == MOVE.F || move == MOVE.FPRIME || move == MOVE.F2) &&
                (lastMove == MOVE.F || lastMove == MOVE.FPRIME || lastMove == MOVE.F2)) {
            return true;
        }

        if ((move == MOVE.B || move == MOVE.BPRIME || move == MOVE.B2) &&
                (lastMove == MOVE.B || lastMove == MOVE.BPRIME || lastMove == MOVE.B2)) {
            return true;
        }

        return false;
    }
}
