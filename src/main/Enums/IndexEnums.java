package main.Enums;

public class IndexEnums {
    public enum FACE {UP, LEFT, FRONT, RIGHT, BACK, DOWN };
    public enum COLOR {RED, BLUE, WHITE, GREEN, YELLOW, ORANGE};
    public enum EDGE {UB, UR, UF, UL, FR, FL, BL, BR, DF, DL, DB, DR};
    public enum CORNER {ULB, URB, URF, ULF, DLF, DLB, DRB, DRF};
    public enum MOVE {
        L, LPRIME, L2,
        R, RPRIME, R2,
        U, UPRIME, U2,
        D, DPRIME, D2,
        F, FPRIME, F2,
        B, BPRIME, B2,
        Y, YPRIME, Y2,
        X, XPRIME, X2,
        Z, ZPRIME, Z2,
        M, MPRIME, M2,
        E, EPRIME, E2,
        S, SPRIME, S2
    };
}
