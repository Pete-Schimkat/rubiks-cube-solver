package main.Model;

import main.Enums.IndexEnums;

/**
 * Simple but slow representation of the cube. Can be used to generate custom positions of the cube without knowing
 * how to achieve said position with regular moves. It also enables small modifications of existing CubeModels.
 * Mind that this could result in unsolvable positions.
 */
public class SimpleCubeModel {
    byte[][][] cube;

    public SimpleCubeModel(){
        this.cube = new byte[6][3][3];

        /*Sides:
        * 0 = top / red
        * 1 = left / blue
        * 2 = front / white
        * 3 = right / green
        * 4 = back / yellow
        * 5 = down / orange
        */
        for(int i = 0; i <6; i ++) {
            for(int row = 0; row < 3; row++) {
                for(int col = 0; col < 3; col++){
                    cube[i][row][col] = (byte) i;
                }
            }
        }
    }
    public SimpleCubeModel(CubeModel cm){
        this.cube = new byte[6][3][3];
        int col = 0;
        int row = 0;
        for(int i = 0; i < 6; i ++) {
            for (row = 0; row < 3; row++) {
                for (col = 0; col < 3; col++) {
                    this.cube[i][row][col] = (byte) cm.getColor(IndexEnums.FACE.values()[i], (byte) row, (byte) col).ordinal();
                }
            }
        }
    }
    public boolean compareCubes(SimpleCubeModel pm) {
        int col = 0;
        int row = 0;
        for(int i = 0; i < 6; i ++) {
            for (row = 0; row < 3; row++) {
                for (col = 0; col < 3; col++) {
                    if(this.cube[i][row][col] != (byte) pm.cube[i][row][col]) return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuffer r = new StringBuffer();
        String[] colors = new String[]{"\uD83D\uDFE5", "\uD83D\uDFE6", "⬜", "\uD83D\uDFE9", "\uD83D\uDFE8", "\uD83D\uDFE7"};

        int col = 0;
        int row = 0;
        for (; row < 3; row++) {
            r.append("           ");
            for (col = 0; col < 3; col++) {
                r.append(colors[this.cube[0][(byte) row] [(byte) col]]);
                r.append(" ");
            }
            r.append("\n");
        }
        r.append("\n");

        for (row = 0; row < 3; row++) {
            for (col = 0; col < 3; col++) {
                r.append(colors[this.cube[1][(byte) row][(byte) col]]);
                r.append(" ");
            }
            r.append(" ");
            for (col = 0; col < 3; col++) {
                r.append(colors[this.cube[2][(byte) row][(byte) col]]);
                r.append(" ");
            }
            r.append(" ");
            for (col = 0; col < 3; col++) {
                r.append(colors[this.cube[3][(byte) row][(byte) col]]);
                r.append(" ");
            }
            r.append(" ");
            for (col = 0; col < 3; col++) {
                r.append(colors[this.cube[4][(byte) row][(byte) col]]);
                r.append(" ");
            }
            r.append("\n");
        }
        r.append("\n");
        for (row = 0; row < 3; row++) {
            r.append("           ");
            for (col = 0; col < 3; col++) {
                r.append(colors[this.cube[5][(byte) row][(byte) col]]);
                r.append(" ");
            }
            r.append("\n");
        }

        return r.toString();
    }
}
