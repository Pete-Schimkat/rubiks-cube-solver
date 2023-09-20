package main.Model;

import main.Enums.IndexEnums.MOVE;
import main.Enums.IndexEnums.COLOR;
import main.Enums.IndexEnums.CORNER;
import main.Enums.IndexEnums.EDGE;
import main.Enums.IndexEnums.FACE;

import java.util.stream.IntStream;

/**
 * The internal representation class for the model of the cube. Optimized for usage of pattern databases.
 * <p>
 * reference: https://stackoverflow.com/questions/500221/how-would-you-represent-a-rubiks-cube-in-code/55505784#55505784
 */
public class CubeModel {

    Cubie[] edges;
    public Cubie[] corners;
    COLOR[] centers;
    /**
     * Initializing the cube with red on top and white on the front
     */
    public CubeModel() {
        this.edges = new Cubie[12];
        this.corners = new Cubie[8];
        this.centers = new COLOR[6];
        for (byte i = 0; i < 12; i++) {
            this.edges[i] = new Cubie(i, (byte) 0);
        }
        for (byte j = 0; j < 8; j++) {
            this.corners[j] = new Cubie(j, (byte) 0);
        }
        this.centers[0] = COLOR.RED; // Up
        this.centers[1] = COLOR.BLUE;  // Left
        this.centers[2] = COLOR.WHITE; // Front
        this.centers[3] = COLOR.GREEN; // Right
        this.centers[4] = COLOR.YELLOW; // Back
        this.centers[5] = COLOR.ORANGE; // Down
    }

    public CubeModel(CubeModel cm) {
        this.edges = new Cubie[12];
        this.corners = new Cubie[8];
        this.centers = new COLOR[6];
        for (byte i = 0; i < 12; i++) {
            this.edges[i] = new Cubie(cm.edges[i]);
        }
        for (byte j = 0; j < 8; j++) {
            this.corners[j] = new Cubie(cm.corners[j]);

        }
        this.centers[0] = COLOR.RED; // Up
        this.centers[1] = COLOR.BLUE;  // Left
        this.centers[2] = COLOR.WHITE; // Front
        this.centers[3] = COLOR.GREEN; // Right
        this.centers[4] = COLOR.YELLOW; // Back
        this.centers[5] = COLOR.ORANGE; // Down
    }
    interface BooleanContains {
        boolean contains(int[] r, int x);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final CubeModel other = (CubeModel) obj;
        for (byte i = 0; i < 12; i++) {
            if(!this.edges[i].equals(other.edges[i])) return false;
        }
        for (byte j = 0; j < 8; j++) {
            if(!this.corners[j].equals(other.corners[j])) return false;
        }
        if(!(this.centers[0] == COLOR.RED &&
        this.centers[1] == COLOR.BLUE &&
        this.centers[2] == COLOR.WHITE&&
        this.centers[3] == COLOR.GREEN&&
        this.centers[4] == COLOR.YELLOW&&
        this.centers[5] == COLOR.ORANGE)) {
            return false;
        }
        return true;
    }

    public int extractCornerIndex(int[] r) {
        BooleanContains c = (d, x) -> (IntStream.of(d).anyMatch(y -> y == x));

        if (c.contains(r, COLOR.RED.ordinal())) { //RED
            if (c.contains(r, COLOR.BLUE.ordinal())) { // BLUE
                if (c.contains(r, COLOR.WHITE.ordinal())) { // WHITE
                    return CORNER.ULF.ordinal();
                } else {
                    return CORNER.ULB.ordinal();
                }
            } else { // GREEN
                if (c.contains(r, COLOR.WHITE.ordinal())) {
                    return CORNER.URF.ordinal();
                } else {
                    return CORNER.URB.ordinal();
                }
            }
        } else {
            if (c.contains(r, COLOR.BLUE.ordinal())) { // BLUE
                if (c.contains(r, COLOR.WHITE.ordinal())) { // WHITE
                    return CORNER.DLF.ordinal();
                } else {
                    return CORNER.DLB.ordinal();
                }
            } else { // GREEN
                if (c.contains(r, COLOR.WHITE.ordinal())) {
                    return CORNER.DRF.ordinal();
                } else {
                    return CORNER.DRB.ordinal();
                }
            }
        }
    }

    public int extractCornerOrientation(int[] r) {
        if ((r[0] == 0) || (r[0] == 5)) { // top or bottom ist rot oder orange
            //if ((r[1] == 1) || (r[1] == 3)) {
            //    if ((r[2] == 2) || r[2] == 4) {
                    return 0;
            //    }
            //}
        } else if ((r[1] == 0) || (r[1] == 5)) { // top or bottom ist blau oder grün
            //if ((r[1] == 2) || (r[1] == 4)) {
            //    if ((r[2] == 0) || r[2] == 5) {
                    return 1;
            //    }
            //}
        }
        return 2;
    }

    public int extractEdgeIndex(int[] r) {
        if((r[0] == 0 && r[1] == 1) || (r[0] == 1 && r[1] == 0)) {
            return EDGE.UL.ordinal();
        } else if ((r[0] == 0 && r[1] == 2) || (r[0] == 2 && r[1] == 0)) {
            return EDGE.UF.ordinal();
        } else if ((r[0] == 0 && r[1] == 3) || (r[0] == 3 && r[1] == 0)) {
            return EDGE.UR.ordinal();
        } else if ((r[0] == 0 && r[1] == 4) || (r[0] == 4 && r[1] == 0)) {
            return EDGE.UB.ordinal();
        } else if ((r[0] == 5 && r[1] == 1) || (r[0] == 1 && r[1] == 5)) {
            return EDGE.DL.ordinal();
        }  else if ((r[0] == 5 && r[1] == 2) || (r[0] == 2 && r[1] == 5)) {
            return EDGE.DF.ordinal();
        }  else if ((r[0] == 5 && r[1] == 3) || (r[0] == 3 && r[1] == 5)) {
            return EDGE.DR.ordinal();
        }  else if ((r[0] == 5 && r[1] == 4) || (r[0] == 4 && r[1] == 5)) {
            return EDGE.DB.ordinal();
        }  else if ((r[0] == 2 && r[1] == 1) || (r[0] == 1 && r[1] == 2)) {
            return EDGE.FL.ordinal();
        }  else if ((r[0] == 2 && r[1] == 3) || (r[0] == 3 && r[1] == 2)) {
            return EDGE.FR.ordinal();
        }  else if ((r[0] == 4 && r[1] == 1) || (r[0] == 1 && r[1] == 4)) {
            return EDGE.BL.ordinal();
        }  else if ((r[0] == 4 && r[1] == 3) || (r[0] == 3 && r[1] == 4)) {
            return EDGE.BR.ordinal();
        }
        return -1;
    }
    public int extractEdgeOrientation(int[] r){
        if((r[0] == 0 )|| (r[0] == 5)){
            return 0;
        }
        if((r[0] == 2) ||(r[0] == 4)){
            if((r[1] == 1) || (r[1] == 3)) {
                return 0;
            }
        }
        return 1;

    }

    /**
     * get the edge colors at a specific  (cube)-index
     *
     * @return Array of 2 colors.
     */
    public COLOR[] getEdgeColors(EDGE index) {
        COLOR[] result = new COLOR[2];
        byte edgeIndex = (byte) index.ordinal();
        Cubie edge = this.edges[edgeIndex];

        switch (EDGE.values()[edge.index]) {

            case UB -> {
                result[0] = COLOR.RED;
                result[1] = COLOR.YELLOW;
            }
            case UR -> {
                result[0] = COLOR.RED;
                result[1] = COLOR.GREEN;
            }
            case UF -> {
                result[0] = COLOR.RED;
                result[1] = COLOR.WHITE;
            }
            case UL -> {
                result[0] = COLOR.RED;
                result[1] = COLOR.BLUE;
            }
            case FR -> {
                result[0] = COLOR.WHITE;
                result[1] = COLOR.GREEN;
            }
            case FL -> {
                result[0] = COLOR.WHITE;
                result[1] = COLOR.BLUE;
            }
            case BL -> {
                result[0] = COLOR.YELLOW;
                result[1] = COLOR.BLUE;
            }
            case BR -> {
                result[0] = COLOR.YELLOW;
                result[1] = COLOR.GREEN;
            }
            case DF -> {
                result[0] = COLOR.ORANGE;
                result[1] = COLOR.WHITE;
            }
            case DL -> {
                result[0] = COLOR.ORANGE;
                result[1] = COLOR.BLUE;
            }
            case DB -> {
                result[0] = COLOR.ORANGE;
                result[1] = COLOR.YELLOW;
            }
            case DR -> {
                result[0] = COLOR.ORANGE;
                result[1] = COLOR.GREEN;
            }
        }
        if (edge.orientation == 1) {
            COLOR temp = result[0];
            result[0] = result[1];
            result[1] = temp;
        }

        return result;
    }

    /**
     * Retrieves the colors on any corner.
     * @return - color-array with 3 entries with the colors on axis x,y,z respectively
     */
    public COLOR[] getCornerColors(CORNER index) {
        COLOR[] result = new COLOR[3];
        byte cornerIndex = (byte) index.ordinal();
        Cubie corner = this.corners[cornerIndex];

        byte redOrOrange = 0, blueOrGreen = 0, yellowOrWhite = 0, temp = 0;
        if (corner.orientation == 0) {
            //redOrOrange = 0;
            blueOrGreen = 1;
            yellowOrWhite = 2;

            if (((cornerIndex + corner.index) % 2) == 1) {
                temp = blueOrGreen;
                blueOrGreen = yellowOrWhite;
                yellowOrWhite = temp;
            }
        } else if (corner.orientation == 1) {
            if (((cornerIndex + corner.index) % 2) == 1) {
                if (cornerIndex % 2 == 1) {

                    redOrOrange = 2;
                    blueOrGreen = 1;
                    //yellowOrWhite = 0;

                } else {

                    redOrOrange = 1;
                    //blueOrGreen = 0;
                    yellowOrWhite = 2;

                }
            } else {
                if ((cornerIndex % 2) == 1) {

                    redOrOrange = 2;
                    //blueOrGreen = 0;
                    yellowOrWhite = 1;

                } else {

                    redOrOrange = 1;
                    blueOrGreen = 2;
                    //yellowOrWhite = 0;

                }
            }
        } else { //if (corner.orientation == 2)
            if ((cornerIndex + corner.index) % 2 == 1) {

                redOrOrange = 2;
                blueOrGreen = 1;
                //yellowOrWhite = 0;

                if ((cornerIndex % 2) == 1) {
                    redOrOrange = 1;
                    blueOrGreen = 0;
                    yellowOrWhite = 2;
                }
            } else {
                if (cornerIndex % 2 == 1) {

                    redOrOrange = 1;
                    blueOrGreen = 2;
                    //yellowOrWhite = 0;

                } else {
                    redOrOrange = 2;
                    //blueOrGreen = 0;
                    yellowOrWhite = 1;
                }
            }
        }
        switch (CORNER.values()[corner.index]) {
            case ULB -> {
                result[redOrOrange] = COLOR.RED;
                result[blueOrGreen] = COLOR.BLUE;
                result[yellowOrWhite] = COLOR.YELLOW;
            }
            case URB -> {
                result[redOrOrange] = COLOR.RED;
                result[blueOrGreen] = COLOR.GREEN;
                result[yellowOrWhite] = COLOR.YELLOW;
            }
            case URF -> {
                result[redOrOrange] = COLOR.RED;
                result[blueOrGreen] = COLOR.GREEN;
                result[yellowOrWhite] = COLOR.WHITE;
            }
            case ULF -> {
                result[redOrOrange] = COLOR.RED;
                result[blueOrGreen] = COLOR.BLUE;
                result[yellowOrWhite] = COLOR.WHITE;
            }
            case DLF -> {
                result[redOrOrange] = COLOR.ORANGE;
                result[blueOrGreen] = COLOR.BLUE;
                result[yellowOrWhite] = COLOR.WHITE;
            }
            case DLB -> {
                result[redOrOrange] = COLOR.ORANGE;
                result[blueOrGreen] = COLOR.BLUE;
                result[yellowOrWhite] = COLOR.YELLOW;
            }
            case DRB -> {
                result[redOrOrange] = COLOR.ORANGE;
                result[blueOrGreen] = COLOR.GREEN;
                result[yellowOrWhite] = COLOR.YELLOW;
            }
            case DRF -> {
                result[redOrOrange] = COLOR.ORANGE;
                result[blueOrGreen] = COLOR.GREEN;
                result[yellowOrWhite] = COLOR.WHITE;
            }
        }
        return result;
    }

    /**
     * Returns the color of a specified side of a cubie. The Model.Cubie is identified by giving a row and column between 0-2
     * and the color is specified by the face.
     *
     * @param face - Face of the cube from which the color is returned
     * @param row  - row of the cubie (0-2)
     * @param col  - column of the cubie (0-2)
     * @return the COLOR
     */
    public COLOR getColor(FACE face, byte row, byte col) {
        if (row == 1 && col == 1) {
            //System.out.println(face.ordinal());
            //System.out.println((this.centers[face.ordinal()]));
            return this.centers[face.ordinal()];
        }
        if (face == FACE.UP) {
            if (row == 0) {
                if (col == 0) {
                    return this.getCornerColors(CORNER.ULB)[0];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.UB)[0];
                } else { //if(col==2)
                    return this.getCornerColors(CORNER.URB)[0];
                }
            } else if (row == 1) {
                if (col == 0) {
                    return this.getEdgeColors(EDGE.UL)[0];
                } else { //if col == 2, col1 is covered above
                    return this.getEdgeColors(EDGE.UR)[0];
                }
            } else { //if row==2
                if (col == 0) {
                    return this.getCornerColors(CORNER.ULF)[0];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.UF)[0];
                } else { //if col == 2
                    return this.getCornerColors(CORNER.URF)[0];
                }
            }
        } else if (face == FACE.LEFT) {
            if (row == 0) {
                if (col == 0) {
                    //System.out.println("Color in getColor war :"+this.getCornerColors(CORNER.ULB)[1]+" (Should be yellow)");
                    return this.getCornerColors(CORNER.ULB)[1];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.UL)[1];
                } else { //if(col==2)
                    return this.getCornerColors(CORNER.ULF)[1];
                }
            } else if (row == 1) {
                if (col == 0) {
                    return this.getEdgeColors(EDGE.BL)[1];
                } else { //if col == 2, col1 is covered above
                    return this.getEdgeColors(EDGE.FL)[1];
                }
            } else { //if row==2
                if (col == 0) {
                    return this.getCornerColors(CORNER.DLB)[1];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.DL)[1];
                } else { //if col == 2
                    return this.getCornerColors(CORNER.DLF)[1];
                }
            }
        } else if (face == FACE.FRONT) {
            if (row == 0) {
                if (col == 0) {
                    return this.getCornerColors(CORNER.ULF)[2];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.UF)[1];
                } else { //if(col==2)
                    return this.getCornerColors(CORNER.URF)[2];
                }
            } else if (row == 1) {
                if (col == 0) {
                    return this.getEdgeColors(EDGE.FL)[0];
                } else { //if col == 2, col1 is covered above
                    return this.getEdgeColors(EDGE.FR)[0];
                }
            } else { //if row==2
                if (col == 0) {
                    return this.getCornerColors(CORNER.DLF)[2];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.DF)[1];
                } else { //if col == 2
                    return this.getCornerColors(CORNER.DRF)[2];
                }
            }
        } else if (face == FACE.RIGHT) {
            if (row == 0) {
                if (col == 0) {
                    return this.getCornerColors(CORNER.URF)[1];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.UR)[1];
                } else { //if(col==2)
                    return this.getCornerColors(CORNER.URB)[1];
                }
            } else if (row == 1) {
                if (col == 0) {
                    return this.getEdgeColors(EDGE.FR)[1];
                } else { //if col == 2, col1 is covered above
                    return this.getEdgeColors(EDGE.BR)[1];
                }
            } else { //if row==2
                if (col == 0) {
                    return this.getCornerColors(CORNER.DRF)[1];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.DR)[1];
                } else { //if col == 2
                    return this.getCornerColors(CORNER.DRB)[1];
                }
            }
        } else if (face == FACE.BACK) {
            if (row == 0) {
                if (col == 0) {
                    return this.getCornerColors(CORNER.URB)[2];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.UB)[1];
                } else { //if(col==2)
                    return this.getCornerColors(CORNER.ULB)[2];
                }
            } else if (row == 1) {
                if (col == 0) {
                    return this.getEdgeColors(EDGE.BR)[0];
                } else { //if col == 2, col1 is covered above
                    return this.getEdgeColors(EDGE.BL)[0];
                }
            } else { //if row==2
                if (col == 0) {
                    return this.getCornerColors(CORNER.DRB)[2];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.DB)[1];
                } else { //if col == 2
                    return this.getCornerColors(CORNER.DLB)[2];
                }
            }
        } else { //if(face == FACE.DOWN) {
            if (row == 0) {
                if (col == 0) {
                    return this.getCornerColors(CORNER.DLF)[0];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.DF)[0];
                } else { //if(col==2)
                    return this.getCornerColors(CORNER.DRF)[0];
                }
            } else if (row == 1) {
                if (col == 0) {
                    return this.getEdgeColors(EDGE.DL)[0];
                } else { //if col == 2, col1 is covered above
                    return this.getEdgeColors(EDGE.DR)[0];
                }
            } else { //if row==2
                if (col == 0) {
                    return this.getCornerColors(CORNER.DLB)[0];
                } else if (col == 1) {
                    return this.getEdgeColors(EDGE.DB)[0];
                } else { //if col == 2
                    return this.getCornerColors(CORNER.DRB)[0];
                }
            }
        }
    }

    /**
     * Returns the cube-relative index of an edge-cubie. Passing a positional edge, this method finds the cubie at that
     * position and returns the original index of the identified piece that is relative to the solved state of the cube.
     *
     */
    public byte getEdgeIndex(EDGE edge) {
        return this.edges[edge.ordinal()].index;
    }

    /**
     * Returns the orientation of an edge piece, relative to its position in the current state of the cube.
     *
     */
    public byte getEdgeOrientation(EDGE edge) {
        return this.edges[edge.ordinal()].orientation;
    }

    /**
     * Returns the cube-relative index of a corner-cubie.
     *
     */
    public byte getCornerIndex(CORNER corner) {
        return this.corners[corner.ordinal()].index;
    }

    /**
     * Returns the orientation of a corner-piece relative to its position in the current state of the cube.
     *
     */
    public byte getCornerOrientation(CORNER corner) {
        return this.corners[corner.ordinal()].orientation;
    }

    /**
     * Checks if the cube is in a solved state by iterating over all indices and checking if every cubie is in its
     * original place.
     *
     * @return false if any cubie is not in its original position, otherwise true
     */
    public boolean isSolved() {
        //Iterating over all corners
        for (byte i = 0; i < 8; i++) {
            if (this.corners[i].index != i || this.corners[i].orientation != 0) { //wrong position or wrong orientation
                //System.out.println("Faulty corner at: "+i);
                return false;
            }
        }
        //Iterating over all edges
        for (byte j = 0; j < 12; j++) {
            if (this.edges[j].index != j || this.edges[j].orientation != 0) {
                //System.out.println("Faulty edge at: "+j+"\nWas: "+EDGE.values()[(this.edges[j].index)]+"Should be: "+EDGE.values()[j]);
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the corner orientation of a corner on 90-degree clockwise twist.
     *
     * @param cornerIndex corner that will be updated
     * @param updateValue how many times the corner orientation is twisted, either 1 or 2
     */
    public void updateCornerOrientation(CORNER cornerIndex, byte updateValue) {
        int index = cornerIndex.ordinal();
        Cubie corner = this.corners[index];
        corner.orientation = (byte) (corner.orientation + updateValue);
        if (corner.orientation == 3) {
            corner.orientation = 0;
        } else if (corner.orientation == 4) {
            corner.orientation = 1;
        }
        this.corners[index] = corner;
    }

    /**
     * Updates the orientation of an edge when applying F or B-moves. Edge orientations are not changed when applying
     * R,L,D or U moves, but the orientation is flipped by F or B moves.
     *
     * @param edge edge that will be flipped
     */
    public void updateEdgeOrientation(EDGE edge) {
        this.edges[edge.ordinal()].orientation ^= 1;  //last bit inversion switches between 1 and 0
    }

    /* --------------------------------------MOVES--------------------------------------- */

    public void u() {
        //Corner position
        Cubie temp = this.corners[CORNER.ULF.ordinal()];
        this.corners[CORNER.ULF.ordinal()] = this.corners[CORNER.URF.ordinal()];
        this.corners[CORNER.URF.ordinal()] = this.corners[CORNER.URB.ordinal()];
        this.corners[CORNER.URB.ordinal()] = this.corners[CORNER.ULB.ordinal()];
        this.corners[CORNER.ULB.ordinal()] = temp;

        //Edge position
        temp = this.edges[EDGE.UL.ordinal()];
        this.edges[EDGE.UL.ordinal()] = this.edges[EDGE.UF.ordinal()];
        this.edges[EDGE.UF.ordinal()] = this.edges[EDGE.UR.ordinal()];
        this.edges[EDGE.UR.ordinal()] = this.edges[EDGE.UB.ordinal()];
        this.edges[EDGE.UB.ordinal()] = temp;
    }

    public void uPrime() {
        Cubie temp = this.corners[CORNER.ULB.ordinal()];
        this.corners[CORNER.ULB.ordinal()] = this.corners[CORNER.URB.ordinal()];
        this.corners[CORNER.URB.ordinal()] = this.corners[CORNER.URF.ordinal()];
        this.corners[CORNER.URF.ordinal()] = this.corners[CORNER.ULF.ordinal()];
        this.corners[CORNER.ULF.ordinal()] = temp;

        temp = this.edges[EDGE.UB.ordinal()];
        this.edges[EDGE.UB.ordinal()] = this.edges[EDGE.UR.ordinal()];
        this.edges[EDGE.UR.ordinal()] = this.edges[EDGE.UF.ordinal()];
        this.edges[EDGE.UF.ordinal()] = this.edges[EDGE.UL.ordinal()];
        this.edges[EDGE.UL.ordinal()] = temp;
    }

    public void u2() {
        Cubie temp = this.corners[CORNER.ULB.ordinal()];
        this.corners[CORNER.ULB.ordinal()] = this.corners[CORNER.URF.ordinal()];
        this.corners[CORNER.URF.ordinal()] = temp;

        temp = this.corners[CORNER.URB.ordinal()];
        this.corners[CORNER.URB.ordinal()] = this.corners[CORNER.ULF.ordinal()];
        this.corners[CORNER.ULF.ordinal()] = temp;

        temp = this.edges[EDGE.UB.ordinal()];
        this.edges[EDGE.UB.ordinal()] = this.edges[EDGE.UF.ordinal()];
        this.edges[EDGE.UF.ordinal()] = temp;

        temp = this.edges[EDGE.UR.ordinal()];
        this.edges[EDGE.UR.ordinal()] = this.edges[EDGE.UL.ordinal()];
        this.edges[EDGE.UL.ordinal()] = temp;
    }

    public void l() {
        Cubie temp = this.corners[CORNER.DLB.ordinal()];
        this.corners[CORNER.DLB.ordinal()] = this.corners[CORNER.DLF.ordinal()];
        this.corners[CORNER.DLF.ordinal()] = this.corners[CORNER.ULF.ordinal()];
        this.corners[CORNER.ULF.ordinal()] = this.corners[CORNER.ULB.ordinal()];
        this.corners[CORNER.ULB.ordinal()] = temp;

        temp = this.edges[EDGE.BL.ordinal()];
        this.edges[EDGE.BL.ordinal()] = this.edges[EDGE.DL.ordinal()];
        this.edges[EDGE.DL.ordinal()] = this.edges[EDGE.FL.ordinal()];
        this.edges[EDGE.FL.ordinal()] = this.edges[EDGE.UL.ordinal()];
        this.edges[EDGE.UL.ordinal()] = temp;

        this.updateCornerOrientation(CORNER.DLB, (byte) 1);
        this.updateCornerOrientation(CORNER.DLF, (byte) 2);
        this.updateCornerOrientation(CORNER.ULF, (byte) 1);
        this.updateCornerOrientation(CORNER.ULB, (byte) 2);
    }

    public void lPrime() {
        Cubie temp = this.corners[CORNER.DLB.ordinal()];
        this.corners[CORNER.DLB.ordinal()] = this.corners[CORNER.ULB.ordinal()];
        this.corners[CORNER.ULB.ordinal()] = this.corners[CORNER.ULF.ordinal()];
        this.corners[CORNER.ULF.ordinal()] = this.corners[CORNER.DLF.ordinal()];
        this.corners[CORNER.DLF.ordinal()] = temp;

        temp = this.edges[EDGE.BL.ordinal()];
        this.edges[EDGE.BL.ordinal()] = this.edges[EDGE.UL.ordinal()];
        this.edges[EDGE.UL.ordinal()] = this.edges[EDGE.FL.ordinal()];
        this.edges[EDGE.FL.ordinal()] = this.edges[EDGE.DL.ordinal()];
        this.edges[EDGE.DL.ordinal()] = temp;

        this.updateCornerOrientation(CORNER.DLB, (byte) 1);
        this.updateCornerOrientation(CORNER.DLF, (byte) 2);
        this.updateCornerOrientation(CORNER.ULF, (byte) 1);
        this.updateCornerOrientation(CORNER.ULB, (byte) 2);
    }

    public void l2() {
        Cubie temp = this.corners[CORNER.DLB.ordinal()];
        this.corners[CORNER.DLB.ordinal()] = this.corners[CORNER.ULF.ordinal()];
        this.corners[CORNER.ULF.ordinal()] = temp;

        temp = this.corners[CORNER.ULB.ordinal()];
        this.corners[CORNER.ULB.ordinal()] = this.corners[CORNER.DLF.ordinal()];
        this.corners[CORNER.DLF.ordinal()] = temp;

        temp = this.edges[EDGE.BL.ordinal()];
        this.edges[EDGE.BL.ordinal()] = this.edges[EDGE.FL.ordinal()];
        this.edges[EDGE.FL.ordinal()] = temp;

        temp = this.edges[EDGE.UL.ordinal()];
        this.edges[EDGE.UL.ordinal()] = this.edges[EDGE.DL.ordinal()];
        this.edges[EDGE.DL.ordinal()] = temp;
    }

    public void f() {
        Cubie temp = this.corners[CORNER.ULF.ordinal()];
        this.corners[CORNER.ULF.ordinal()] = this.corners[CORNER.DLF.ordinal()];
        this.corners[CORNER.DLF.ordinal()] = this.corners[CORNER.DRF.ordinal()];
        this.corners[CORNER.DRF.ordinal()] = this.corners[CORNER.URF.ordinal()];
        this.corners[CORNER.URF.ordinal()] = temp;

        temp = this.edges[EDGE.UF.ordinal()];
        this.edges[EDGE.UF.ordinal()] = this.edges[EDGE.FL.ordinal()];
        this.edges[EDGE.FL.ordinal()] = this.edges[EDGE.DF.ordinal()];
        this.edges[EDGE.DF.ordinal()] = this.edges[EDGE.FR.ordinal()];
        this.edges[EDGE.FR.ordinal()] = temp;

        this.updateCornerOrientation(CORNER.ULF, (byte) 2);
        this.updateCornerOrientation(CORNER.URF, (byte) 1);
        this.updateCornerOrientation(CORNER.DRF, (byte) 2);
        this.updateCornerOrientation(CORNER.DLF, (byte) 1);

        this.updateEdgeOrientation(EDGE.UF);
        this.updateEdgeOrientation(EDGE.FL);
        this.updateEdgeOrientation(EDGE.DF);
        this.updateEdgeOrientation(EDGE.FR);
    }

    public void fPrime() {
        Cubie temp = this.corners[CORNER.ULF.ordinal()];
        this.corners[CORNER.ULF.ordinal()] = this.corners[CORNER.URF.ordinal()];
        this.corners[CORNER.URF.ordinal()] = this.corners[CORNER.DRF.ordinal()];
        this.corners[CORNER.DRF.ordinal()] = this.corners[CORNER.DLF.ordinal()];
        this.corners[CORNER.DLF.ordinal()] = temp;

        temp = this.edges[EDGE.UF.ordinal()];
        this.edges[EDGE.UF.ordinal()] = this.edges[EDGE.FR.ordinal()];
        this.edges[EDGE.FR.ordinal()] = this.edges[EDGE.DF.ordinal()];
        this.edges[EDGE.DF.ordinal()] = this.edges[EDGE.FL.ordinal()];
        this.edges[EDGE.FL.ordinal()] = temp;

        this.updateCornerOrientation(CORNER.ULF, (byte) 2);
        this.updateCornerOrientation(CORNER.URF, (byte) 1);
        this.updateCornerOrientation(CORNER.DRF, (byte) 2);
        this.updateCornerOrientation(CORNER.DLF, (byte) 1);

        this.updateEdgeOrientation(EDGE.UF);
        this.updateEdgeOrientation(EDGE.FL);
        this.updateEdgeOrientation(EDGE.DF);
        this.updateEdgeOrientation(EDGE.FR);
    }

    public void f2() {
        Cubie temp = this.corners[CORNER.ULF.ordinal()];
        this.corners[CORNER.ULF.ordinal()] = this.corners[CORNER.DRF.ordinal()];
        this.corners[CORNER.DRF.ordinal()] = temp;

        temp = this.corners[CORNER.URF.ordinal()];
        this.corners[CORNER.URF.ordinal()] = this.corners[CORNER.DLF.ordinal()];
        this.corners[CORNER.DLF.ordinal()] = temp;

        temp = this.edges[EDGE.UF.ordinal()];
        this.edges[EDGE.UF.ordinal()] = this.edges[EDGE.DF.ordinal()];
        this.edges[EDGE.DF.ordinal()] = temp;

        temp = this.edges[EDGE.FL.ordinal()];
        this.edges[EDGE.FL.ordinal()] = this.edges[EDGE.FR.ordinal()];
        this.edges[EDGE.FR.ordinal()] = temp;
    }

    public void r() {
        Cubie temp = this.corners[CORNER.DRB.ordinal()];
        this.corners[CORNER.DRB.ordinal()] = this.corners[CORNER.URB.ordinal()];
        this.corners[CORNER.URB.ordinal()] = this.corners[CORNER.URF.ordinal()];
        this.corners[CORNER.URF.ordinal()] = this.corners[CORNER.DRF.ordinal()];
        this.corners[CORNER.DRF.ordinal()] = temp;

        temp = this.edges[EDGE.BR.ordinal()];
        this.edges[EDGE.BR.ordinal()] = this.edges[EDGE.UR.ordinal()];
        this.edges[EDGE.UR.ordinal()] = this.edges[EDGE.FR.ordinal()];
        this.edges[EDGE.FR.ordinal()] = this.edges[EDGE.DR.ordinal()];
        this.edges[EDGE.DR.ordinal()] = temp;

        this.updateCornerOrientation(CORNER.DRB, (byte) 2);
        this.updateCornerOrientation(CORNER.DRF, (byte) 1);
        this.updateCornerOrientation(CORNER.URF, (byte) 2);
        this.updateCornerOrientation(CORNER.URB, (byte) 1);
    }

    public void rPrime() {
        Cubie temp = this.corners[CORNER.DRB.ordinal()];
        this.corners[CORNER.DRB.ordinal()] = this.corners[CORNER.DRF.ordinal()];
        this.corners[CORNER.DRF.ordinal()] = this.corners[CORNER.URF.ordinal()];
        this.corners[CORNER.URF.ordinal()] = this.corners[CORNER.URB.ordinal()];
        this.corners[CORNER.URB.ordinal()] = temp;

        temp = this.edges[EDGE.BR.ordinal()];
        this.edges[EDGE.BR.ordinal()] = this.edges[EDGE.DR.ordinal()];
        this.edges[EDGE.DR.ordinal()] = this.edges[EDGE.FR.ordinal()];
        this.edges[EDGE.FR.ordinal()] = this.edges[EDGE.UR.ordinal()];
        this.edges[EDGE.UR.ordinal()] = temp;

        this.updateCornerOrientation(CORNER.DRB, (byte) 2);
        this.updateCornerOrientation(CORNER.DRF, (byte) 1);
        this.updateCornerOrientation(CORNER.URF, (byte) 2);
        this.updateCornerOrientation(CORNER.URB, (byte) 1);
    }

    public void r2() {
        Cubie temp = this.corners[CORNER.DRB.ordinal()];
        this.corners[CORNER.DRB.ordinal()] = this.corners[CORNER.URF.ordinal()];
        this.corners[CORNER.URF.ordinal()] = temp;

        temp = this.corners[CORNER.URB.ordinal()];
        this.corners[CORNER.URB.ordinal()] = this.corners[CORNER.DRF.ordinal()];
        this.corners[CORNER.DRF.ordinal()] = temp;

        temp = this.edges[EDGE.BR.ordinal()];
        this.edges[EDGE.BR.ordinal()] = this.edges[EDGE.FR.ordinal()];
        this.edges[EDGE.FR.ordinal()] = temp;

        temp = this.edges[EDGE.UR.ordinal()];
        this.edges[EDGE.UR.ordinal()] = this.edges[EDGE.DR.ordinal()];
        this.edges[EDGE.DR.ordinal()] = temp;
    }

    public void b() {
        Cubie temp = this.corners[CORNER.ULB.ordinal()];
        this.corners[CORNER.ULB.ordinal()] = this.corners[CORNER.URB.ordinal()];
        this.corners[CORNER.URB.ordinal()] = this.corners[CORNER.DRB.ordinal()];
        this.corners[CORNER.DRB.ordinal()] = this.corners[CORNER.DLB.ordinal()];
        this.corners[CORNER.DLB.ordinal()] = temp;

        temp = this.edges[EDGE.UB.ordinal()];
        this.edges[EDGE.UB.ordinal()] = this.edges[EDGE.BR.ordinal()];
        this.edges[EDGE.BR.ordinal()] = this.edges[EDGE.DB.ordinal()];
        this.edges[EDGE.DB.ordinal()] = this.edges[EDGE.BL.ordinal()];
        this.edges[EDGE.BL.ordinal()] = temp;

        this.updateCornerOrientation(CORNER.ULB, (byte) 1);
        this.updateCornerOrientation(CORNER.URB, (byte) 2);
        this.updateCornerOrientation(CORNER.DRB, (byte) 1);
        this.updateCornerOrientation(CORNER.DLB, (byte) 2);

        this.updateEdgeOrientation(EDGE.UB);
        this.updateEdgeOrientation(EDGE.BL);
        this.updateEdgeOrientation(EDGE.DB);
        this.updateEdgeOrientation(EDGE.BR);
    }

    public void bPrime() {
        Cubie temp = this.corners[CORNER.ULB.ordinal()];
        this.corners[CORNER.ULB.ordinal()] = this.corners[CORNER.DLB.ordinal()];
        this.corners[CORNER.DLB.ordinal()] = this.corners[CORNER.DRB.ordinal()];
        this.corners[CORNER.DRB.ordinal()] = this.corners[CORNER.URB.ordinal()];
        this.corners[CORNER.URB.ordinal()] = temp;

        temp = this.edges[EDGE.UB.ordinal()];
        this.edges[EDGE.UB.ordinal()] = this.edges[EDGE.BL.ordinal()];
        this.edges[EDGE.BL.ordinal()] = this.edges[EDGE.DB.ordinal()];
        this.edges[EDGE.DB.ordinal()] = this.edges[EDGE.BR.ordinal()];
        this.edges[EDGE.BR.ordinal()] = temp;

        this.updateCornerOrientation(CORNER.ULB, (byte) 1);
        this.updateCornerOrientation(CORNER.URB, (byte) 2);
        this.updateCornerOrientation(CORNER.DRB, (byte) 1);
        this.updateCornerOrientation(CORNER.DLB, (byte) 2);

        this.updateEdgeOrientation(EDGE.UB);
        this.updateEdgeOrientation(EDGE.BL);
        this.updateEdgeOrientation(EDGE.DB);
        this.updateEdgeOrientation(EDGE.BR);
    }

    public void b2() {
        Cubie temp = this.corners[CORNER.ULB.ordinal()];
        this.corners[CORNER.ULB.ordinal()] = this.corners[CORNER.DRB.ordinal()];
        this.corners[CORNER.DRB.ordinal()] = temp;

        temp = this.corners[CORNER.URB.ordinal()];
        this.corners[CORNER.URB.ordinal()] = this.corners[CORNER.DLB.ordinal()];
        this.corners[CORNER.DLB.ordinal()] = temp;

        temp = this.edges[EDGE.UB.ordinal()];
        this.edges[EDGE.UB.ordinal()] = this.edges[EDGE.DB.ordinal()];
        this.edges[EDGE.DB.ordinal()] = temp;

        temp = this.edges[EDGE.BL.ordinal()];
        this.edges[EDGE.BL.ordinal()] = this.edges[EDGE.BR.ordinal()];
        this.edges[EDGE.BR.ordinal()] = temp;
    }

    public void d() {
        Cubie temp = this.corners[CORNER.DLB.ordinal()];
        this.corners[CORNER.DLB.ordinal()] = this.corners[CORNER.DRB.ordinal()];
        this.corners[CORNER.DRB.ordinal()] = this.corners[CORNER.DRF.ordinal()];
        this.corners[CORNER.DRF.ordinal()] = this.corners[CORNER.DLF.ordinal()];
        this.corners[CORNER.DLF.ordinal()] = temp;

        temp = this.edges[EDGE.DB.ordinal()];
        this.edges[EDGE.DB.ordinal()] = this.edges[EDGE.DR.ordinal()];
        this.edges[EDGE.DR.ordinal()] = this.edges[EDGE.DF.ordinal()];
        this.edges[EDGE.DF.ordinal()] = this.edges[EDGE.DL.ordinal()];
        this.edges[EDGE.DL.ordinal()] = temp;
    }

    public void dPrime() {
        Cubie temp = this.corners[CORNER.DLF.ordinal()];
        this.corners[CORNER.DLF.ordinal()] = this.corners[CORNER.DRF.ordinal()];
        this.corners[CORNER.DRF.ordinal()] = this.corners[CORNER.DRB.ordinal()];
        this.corners[CORNER.DRB.ordinal()] = this.corners[CORNER.DLB.ordinal()];
        this.corners[CORNER.DLB.ordinal()] = temp;

        temp = this.edges[EDGE.DL.ordinal()];
        this.edges[EDGE.DL.ordinal()] = this.edges[EDGE.DF.ordinal()];
        this.edges[EDGE.DF.ordinal()] = this.edges[EDGE.DR.ordinal()];
        this.edges[EDGE.DR.ordinal()] = this.edges[EDGE.DB.ordinal()];
        this.edges[EDGE.DB.ordinal()] = temp;
    }

    public void d2() {
        Cubie temp = this.corners[CORNER.DLB.ordinal()];
        this.corners[CORNER.DLB.ordinal()] = this.corners[CORNER.DRF.ordinal()];
        this.corners[CORNER.DRF.ordinal()] = temp;

        temp = this.corners[CORNER.DRB.ordinal()];
        this.corners[CORNER.DRB.ordinal()] = this.corners[CORNER.DLF.ordinal()];
        this.corners[CORNER.DLF.ordinal()] = temp;

        temp = this.edges[EDGE.DB.ordinal()];
        this.edges[EDGE.DB.ordinal()] = this.edges[EDGE.DF.ordinal()];
        this.edges[EDGE.DF.ordinal()] = temp;

        temp = this.edges[EDGE.DR.ordinal()];
        this.edges[EDGE.DR.ordinal()] = this.edges[EDGE.DL.ordinal()];
        this.edges[EDGE.DL.ordinal()] = temp;
    }

    public void m() {
        System.out.println("Not implemented");
    }

    public void mPrime() {
        System.out.println("Not implemented");
    }

    public void m2() {
        System.out.println("Not implemented");
    }

    public void e() {
        System.out.println("Not implemented");
    }

    public void ePrime() {
        System.out.println("Not implemented");
    }

    public void e2() {
        System.out.println("Not implemented");
    }

    public void s() {
        System.out.println("Not implemented");
    }

    public void sPrime() {
        System.out.println("Not implemented");
    }

    public void s2() {
        System.out.println("Not implemented");
    }

    public void y() {
        System.out.println("Not implemented");
    }

    public void yPrime() {
        System.out.println("Not implemented");
    }

    public void y2() {
        System.out.println("Not implemented");
    }

    public void x() {
        System.out.println("Not implemented");
    }

    public void xPrime() {
        System.out.println("Not implemented");
    }

    public void x2() {
        System.out.println("Not implemented");
    }

    public void z() {
        System.out.println("Not implemented");
    }

    public void zPrime() {
        System.out.println("Not implemented");
    }

    public void z2() {
        System.out.println("Not implemented");
    }

    public void move(MOVE ind) {
        switch (ind) {
            case L:
                this.l();
                break;
            case LPRIME:
                this.lPrime();
                break;
            case L2:
                this.l2();
                break;
            case R:
                this.r();
                break;
            case RPRIME:
                this.rPrime();
                break;
            case R2:
                this.r2();
                break;
            case U:
                this.u();
                break;
            case UPRIME:
                this.uPrime();
                break;
            case U2:
                this.u2();
                break;
            case D:
                this.d();
                break;
            case DPRIME:
                this.dPrime();
                break;
            case D2:
                this.d2();
                break;
            case F:
                this.f();
                break;
            case FPRIME:
                this.fPrime();
                break;
            case F2:
                this.f2();
                break;
            case B:
                this.b();
                break;
            case BPRIME:
                this.bPrime();
                break;
            case B2:
                this.b2();
                break;
            case Y:
                this.y();
                break;
            case YPRIME:
                this.yPrime();
                break;
            case Y2:
                this.y2();
                break;
            case X:
                this.x();
                break;
            case XPRIME:
                this.xPrime();
                break;
            case X2:
                this.x2();
                break;
            case Z:
                this.z();
                break;
            case ZPRIME:
                this.zPrime();
                break;
            case Z2:
                this.z2();
                break;
            case M:
                this.m();
                break;
            case MPRIME:
                this.mPrime();
                break;
            case M2:
                this.m2();
                break;
            case E:
                this.e();
                break;
            case EPRIME:
                this.ePrime();
                break;
            case E2:
                this.e2();
                break;
            case S:
                this.s();
                break;
            case SPRIME:
                this.sPrime();
                break;
            case S2:
                this.s2();
                break;
        }
    }

    public void invert(MOVE ind) {
        switch (ind) {
            case L:
                this.lPrime();
                break;
            case LPRIME:
                this.l();
                break;
            case L2:
                this.l2();
                break;
            case R:
                this.rPrime();
                break;
            case RPRIME:
                this.r();
                break;
            case R2:
                this.r2();
                break;
            case U:
                this.uPrime();
                break;
            case UPRIME:
                this.u();
                break;
            case U2:
                this.u2();
                break;
            case D:
                this.dPrime();
                break;
            case DPRIME:
                this.d();
                break;
            case D2:
                this.d2();
                break;
            case F:
                this.fPrime();
                break;
            case FPRIME:
                this.f();
                break;
            case F2:
                this.f2();
                break;
            case B:
                this.bPrime();
                break;
            case BPRIME:
                this.b();
                break;
            case B2:
                this.b2();
                break;
            case Y:
                this.yPrime();
                break;
            case YPRIME:
                this.y();
                break;
            case Y2:
                this.y2();
                break;
            case X:
                this.xPrime();
                break;
            case XPRIME:
                this.x();
                break;
            case X2:
                this.x2();
                break;
            case Z:
                this.zPrime();
                break;
            case ZPRIME:
                this.z();
                break;
            case Z2:
                this.z2();
                break;
            case M:
                this.mPrime();
                break;
            case MPRIME:
                this.m();
                break;
            case M2:
                this.m2();
                break;
            case E:
                this.ePrime();
                break;
            case EPRIME:
                this.e();
                break;
            case E2:
                this.e2();
                break;
            case S:
                this.sPrime();
                break;
            case SPRIME:
                this.s();
                break;
            case S2:
                this.s2();
                break;
        }
    }

    public static String getMove(MOVE ind) {
        return switch (ind) {
            case L -> "L";
            case LPRIME -> "L'";
            case L2 -> "L2";
            case R -> "R";
            case RPRIME -> "R'";
            case R2 -> "R2";
            case U -> "U";
            case UPRIME -> "U'";
            case U2 -> "U2";
            case D -> "D";
            case DPRIME -> "D'";
            case D2 -> "D2";
            case F -> "F";
            case FPRIME -> "F'";
            case F2 -> "F2";
            case B -> "B";
            case BPRIME -> "B'";
            case B2 -> "B2";
            case Y -> "Y";
            case YPRIME -> "Y'";
            case Y2 -> "Y2";
            case X -> "X";
            case XPRIME -> "X'";
            case X2 -> "X2";
            case Z -> "Z";
            case ZPRIME -> "Z'";
            case Z2 -> "Z2";
            case M -> "M";
            case MPRIME -> "M'";
            case M2 -> "M2";
            case E -> "E";
            case EPRIME -> "E'";
            case E2 -> "E2";
            case S -> "S";
            case SPRIME -> "S'";
            case S2 -> "S2";
        };
    }


    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        //char[] colors = new char[]{'W','G', 'R', 'B', 'O', 'Y'};
        //char[] colors = new char[]{'F','R', 'T', 'L', 'D', 'B'};
        String[] colors = new String[]{"\uD83D\uDFE5", "\uD83D\uDFE6", "⬜", "\uD83D\uDFE9", "\uD83D\uDFE8", "\uD83D\uDFE7"};

        int col = 0;
        int row = 0;
        for (; row < 3; row++) {
            r.append("           ");
            for (; col < 3; col++) {
                r.append(colors[this.getColor(FACE.UP, (byte) row, (byte) col).ordinal()]);
                r.append(" ");
            }
            r.append("\n");
        }
        r.append("\n");

        for (row = 0; row < 3; row++) {
            for (col = 0; col < 3; col++) {
                r.append(colors[this.getColor(FACE.LEFT, (byte) row, (byte) col).ordinal()]);
                r.append(" ");
            }
            r.append(" ");
            for (col = 0; col < 3; col++) {
                r.append(colors[this.getColor(FACE.FRONT, (byte) row, (byte) col).ordinal()]);
                r.append(" ");
            }
            r.append(" ");
            for (col = 0; col < 3; col++) {
                r.append(colors[this.getColor(FACE.RIGHT, (byte) row, (byte) col).ordinal()]);
                r.append(" ");
            }
            r.append(" ");
            for (col = 0; col < 3; col++) {
                r.append(colors[this.getColor(FACE.BACK, (byte) row, (byte) col).ordinal()]);
                r.append(" ");
            }
            r.append("\n");
        }
        r.append("\n");
        for (row = 0; row < 3; row++) {
            r.append("           ");
            for (col = 0; col < 3; col++) {
                r.append(colors[this.getColor(FACE.DOWN, (byte) row, (byte) col).ordinal()]);
                r.append(" ");
            }
            r.append("\n");
        }
        return r.toString();
    }

    public void applyScramble(String scramble) {
        this.applyScramble(scramble, " ");
    }

    public void applyScramble(String scramble, String separator) {
        String[] moveArray = scramble.split(separator);

        for (String s : moveArray) {
            switch (s) {
                case "U" -> this.u();
                case "U'" -> this.uPrime();
                case "U2" -> this.u2();
                case "L" -> this.l();
                case "L'" -> this.lPrime();
                case "L2" -> this.l2();
                case "F" -> this.f();
                case "F'" -> this.fPrime();
                case "F2" -> this.f2();
                case "R" -> this.r();
                case "R'" -> this.rPrime();
                case "R2" -> this.r2();
                case "B" -> this.b();
                case "B'" -> this.bPrime();
                case "B2" -> this.b2();
                case "D" -> this.d();
                case "D'" -> this.dPrime();
                case "D2" -> this.d2();
                case "Y" -> this.y();
                case "Y'" -> this.yPrime();
                case "Y2" -> this.y2();
                case "X" -> this.x();
                case "X'" -> this.xPrime();
                case "X2" -> this.x2();
                case "Z" -> this.z();
                case "Z'" -> this.zPrime();
                case "Z2" -> this.z2();
                case "M" -> this.m();
                case "M'" -> this.mPrime();
                case "M2" -> this.m2();
                case "E" -> this.e();
                case "E'" -> this.ePrime();
                case "E2" -> this.e2();
                case "S" -> this.s();
                case "S'" -> this.sPrime();
                case "S2" -> this.s2();
                default -> System.out.println("invalid move detected: " + s);
            }
        }
    }

    /**
     * Translates a primitive cube model into the model that will be used for computations.
     *
     * @param pm - the cube that will be translated
     */
    public CubeModel(SimpleCubeModel pm) {
        this.edges = new Cubie[12];
        this.corners = new Cubie[8];
        this.centers = new COLOR[6];
        this.centers[0] = COLOR.RED; // Up
        this.centers[1] = COLOR.BLUE;  // Left
        this.centers[2] = COLOR.WHITE; // Front
        this.centers[3] = COLOR.GREEN; // Right
        this.centers[4] = COLOR.YELLOW; // Back
        this.centers[5] = COLOR.ORANGE; // Down

        int[] r = new int[3];

        //ULB
        r[0] = pm.cube[0][0][0];
        r[1] = pm.cube[1][0][0];
        r[2] =  pm.cube[4][0][2];
        this.corners[0] = new Cubie((byte) extractCornerIndex(r), (byte)extractCornerOrientation(r));
        //URB
        r[0] = pm.cube[0][0][2];
        r[1] = pm.cube[4][0][0];
        r[2] =  pm.cube[3][0][2];
        this.corners[1] = new Cubie((byte) extractCornerIndex(r), (byte)extractCornerOrientation(r));


        //URF
        r[0] = pm.cube[0][2][2];
        r[1] = pm.cube[3][0][0];
        r[2] = pm.cube[2][0][2];
        this.corners[2] = new Cubie((byte) extractCornerIndex(r), (byte)extractCornerOrientation(r));


        //ULF
        r[0] = pm.cube[0][2][0];
        r[1] = pm.cube[2][0][0];
        r[2] = pm.cube[1][0][2];
        this.corners[3] = new Cubie((byte) extractCornerIndex(r), (byte)extractCornerOrientation(r));


        //DLF
        r[0] = pm.cube[5][0][0];
        r[1] = pm.cube[1][2][2];
        r[2] = pm.cube[2][2][0];
        this.corners[4] = new Cubie((byte) extractCornerIndex(r), (byte)extractCornerOrientation(r));
        //DLB
        r[0] = pm.cube[5][2][0];
        r[1] = pm.cube[4][2][2];
        r[2] = pm.cube[1][2][0];
        this.corners[5] = new Cubie((byte) extractCornerIndex(r), (byte)extractCornerOrientation(r));


        //DRB
        r[0] = pm.cube[5][2][2];
        r[1] = pm.cube[3][2][2];
        r[2] = pm.cube[4][2][0];
        this.corners[6] = new Cubie((byte) extractCornerIndex(r), (byte)extractCornerOrientation(r));


        //DRF
        r[0] = pm.cube[5][0][2];
        r[1] = pm.cube[2][2][2];
        r[2] = pm.cube[3][2][0];
        this.corners[7] = new Cubie((byte) extractCornerIndex(r), (byte)extractCornerOrientation(r));

        //----------------EDGES-----------------//
        //UB
        r[0] = pm.cube[0][0][1];
        r[1] = pm.cube[4][0][1];
        this.edges[0] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
        //UR
        r[0] = pm.cube[0][1][2];
        r[1] = pm.cube[3][0][1];
        this.edges[1] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
        //UF
        r[0] = pm.cube[0][2][1];
        r[1] = pm.cube[2][0][1];
        this.edges[2] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
        //UL
        r[0] = pm.cube[0][1][0];
        r[1] = pm.cube[1][0][1];
        this.edges[3] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
        //FR
        r[0] = pm.cube[2][1][2];
        r[1] = pm.cube[3][1][0];
        this.edges[4] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
        //FL
        r[0] = pm.cube[2][1][0];
        r[1] = pm.cube[1][1][2];
        this.edges[5] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
        //BL
        r[0] = pm.cube[4][1][2];
        r[1] = pm.cube[1][1][0];
        this.edges[6] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
        //BR
        r[0] = pm.cube[4][1][0];
        r[1] = pm.cube[3][1][2];
        this.edges[7] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
        //DF
        r[0] = pm.cube[5][0][1];
        r[1] = pm.cube[2][2][1];
        this.edges[8] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
        //DL
        r[0] = pm.cube[5][1][0];
        r[1] = pm.cube[1][2][1];
        this.edges[9] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
        //DB
        r[0] = pm.cube[5][2][1];
        r[1] = pm.cube[4][2][1];
        this.edges[10] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
        //DR
        r[0] = pm.cube[5][1][2];
        r[1] = pm.cube[3][2][1];
        this.edges[11] = new Cubie((byte) extractEdgeIndex(r), (byte) extractEdgeOrientation(r));
    }

}
