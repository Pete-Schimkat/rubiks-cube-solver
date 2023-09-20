package main.Algorithms;

import main.Enums.IndexEnums;
import main.Model.CubeModel;
import main.Model.DatabaseGoal;
import main.Utils.TwistStore;
import main.PatternDatabase.SevenEdge.CombinedKorfPatternDatabase;
import main.PatternDatabase.CornerPatternDatabase;
import main.PatternDatabase.SevenEdge.EdgeHigherPatternDatabase;
import main.PatternDatabase.SevenEdge.EdgeLowerPatternDatabase;
import main.PatternDatabase.EdgePermutationPatternDatabase;
import main.PatternDatabase.SixEdge.EdgeHigherPDBSmall;
import main.PatternDatabase.SixEdge.EdgeLowerPDBSmall;
import main.PatternDatabase.SixEdge.SmallKorfPDB;
import main.Utils.WeightedIDAStarNode;

import java.util.List;

public class KorfCubeSolver {
    CornerPatternDatabase cornerPDB;
    EdgeLowerPatternDatabase edge1PDB;
    EdgeHigherPatternDatabase edge2PDB;
    EdgePermutationPatternDatabase edgePermPDB;
    CombinedKorfPatternDatabase combinedPDB;

    SmallKorfPDB combinedSmallPDB;

    EdgeLowerPDBSmall sixEdge1PDB;

    EdgeHigherPDBSmall sixEdge2PDB;

    boolean cornerPDBIndexed = false;
    boolean edge1PDBIndexed = false;
    boolean edge2PDBIndexed = false;
    boolean edgePermPDBIndexed = false;
    boolean sixEdge1Indexed = false;
    boolean sixEdge2Indexed = false;

    double weight;

    public static boolean solving = false;
    public static final String databasePrefix = "";
    public KorfCubeSolver(double weight) {
        this.cornerPDB = new CornerPatternDatabase();
        this.edge1PDB = new EdgeLowerPatternDatabase();
        this.edge2PDB = new EdgeHigherPatternDatabase();
        this.edgePermPDB = new EdgePermutationPatternDatabase();
        this.sixEdge1PDB = new EdgeLowerPDBSmall();
        this.sixEdge2PDB = new EdgeHigherPDBSmall();
        this.combinedSmallPDB = new SmallKorfPDB(cornerPDB, sixEdge1PDB, sixEdge2PDB, edgePermPDB);
        this.combinedPDB = new CombinedKorfPatternDatabase(cornerPDB, edge1PDB, edge2PDB, edgePermPDB);
        this.weight = weight;

    }
    public void initialize(){
        System.out.println("Initializing pattern databases for the KorfCubeSolver, might take a while...");
        this.indexCornerDatabase();
        this.indexEdgeG1Database();
        this.indexEdgeG2Database();
        this.indexEdgePermDatabase();
        this.indexSixEdgeG1Database();
        this.indexSixEdgeG2Database();
    }



    public String solveCube(CubeModel cm,boolean print, double weight, boolean sixEdgeVersion) {

        String solutionString;
        long start = System.currentTimeMillis();
        long end;
        if(print) {
            System.out.println("Solving with Korf-Method...");
            System.out.println("Initial cube state: ");
            System.out.println(cm);
        }
        //Ensures correctly oriented cube
        if(!(cm.getColor(IndexEnums.FACE.UP, (byte) 1, (byte) 1) == IndexEnums.COLOR.RED &&
                cm.getColor(IndexEnums.FACE.FRONT, (byte) 1, (byte) 1) == IndexEnums.COLOR.WHITE)) return null;

        solutionString = solveWithWeightedIDAStar(cm, weight, sixEdgeVersion);
        end = System.currentTimeMillis();
        int minutes = Math.toIntExact((end-start)/60000);
        double seconds = (((double) ((end - (start))-(minutes*60000L))/1000));

        if(print) {
            System.out.println("It took " + minutes + " minutes and " + seconds + " seconds to solve this cube.");
            System.out.println("Found the following solution (" + (solutionString.split(" ").length) + " moves):");
            System.out.println(solutionString);
        }
        solving = false;
        return solutionString;
    }
    public String solveWithIDAStar(CubeModel cm) {
        return solveWithWeightedIDAStar(cm, 1, false );
    }

    public String solveWithWeightedIDAStar(CubeModel cm, double weight, boolean sixEdgeDatabase) {
        List<WeightedIDAStarNode> solution;
        if(sixEdgeDatabase) {
            WeightedIDAStar ida3 = new WeightedIDAStar(this.combinedSmallPDB, weight);
            solution = ida3.solve(new WeightedIDAStarNode(null, cm, null, (byte) 0, this.combinedSmallPDB.getNumMoves(cm))).path();
        }
        else {
            WeightedIDAStar ida3 = new WeightedIDAStar(this.combinedPDB, weight);
            solution = ida3.solve(new WeightedIDAStarNode(null, cm, null, (byte) 0, this.combinedPDB.getNumMoves(cm))).path();
        }
            StringBuilder solutionString = new StringBuilder();
            for (WeightedIDAStarNode s : solution) {
                if (s.move != null) solutionString.append(CubeModel.getMove(s.move)).append(" ");
            }
            return solutionString.toString();

    }
    void indexCornerDatabase() {
        solving = true;
        if(!this.cornerPDB.readDatabaseFromFile(databasePrefix+"corner.pdb")){
            BreadthFirstCubeSearch bfs = new BreadthFirstCubeSearch();
            CubeModel cm = new CubeModel();
            DatabaseGoal cornerGoal = new DatabaseGoal(this.cornerPDB);
            TwistStore twistStore = new TwistStore(cm);

            System.out.println("Goal 1: " + cornerGoal.getDescription());

            bfs.search(cornerGoal, cm, twistStore);
            this.cornerPDB.writeDatabaseToFile(databasePrefix+"corner.pdb");
        }
        this.cornerPDBIndexed = true;
        this.onIndexComplete();

    }
    void indexEdgeG1Database(){
        solving = true;
        if(!this.edge1PDB.readDatabaseFromFile(databasePrefix+"edgeG1.pdb")){
            BreadthFirstCubeSearch indexer = new BreadthFirstCubeSearch();
            CubeModel cm = new CubeModel();
            DatabaseGoal edgeG1Goal = new DatabaseGoal(this.edge1PDB);
            TwistStore twistStore = new TwistStore(cm);

            System.out.println("Goal 2: " +edgeG1Goal.getDescription());

            indexer.search(edgeG1Goal, cm, twistStore);
            this.edge1PDB.writeDatabaseToFile(databasePrefix+"edgeG1.pdb");
        }

        this.edge1PDBIndexed = true;
        this.onIndexComplete();
    }
    void indexEdgeG2Database() {
        solving = true;
        if(!this.edge2PDB.readDatabaseFromFile(databasePrefix+"edgeG2.pdb")){
            BreadthFirstCubeSearch indexer = new BreadthFirstCubeSearch();
            CubeModel cm = new CubeModel();
            DatabaseGoal edgeG2Goal = new DatabaseGoal(this.edge2PDB);
            TwistStore twistStore = new TwistStore(cm);

            System.out.println("Goal 3: "+edgeG2Goal.getDescription());

            indexer.search(edgeG2Goal, cm, twistStore);
            this.edge2PDB.writeDatabaseToFile(databasePrefix+"edgeG2.pdb");
        }
        this.edge2PDBIndexed = true;
        this.onIndexComplete();
    }
    void indexEdgePermDatabase() {
        solving = true;
        if(!this.edgePermPDB.readDatabaseFromFile(databasePrefix+"edgePerm.pdb")) {
            BreadthFirstCubeSearch indexer = new BreadthFirstCubeSearch();
            CubeModel cm = new CubeModel();
            DatabaseGoal edgePermGoal = new DatabaseGoal(this.edgePermPDB);
            TwistStore twistStore = new TwistStore(cm);

            System.out.println("Goal 4: "+edgePermGoal.getDescription());

            indexer.search(edgePermGoal, cm, twistStore);
            this.edgePermPDB.writeDatabaseToFile(databasePrefix+"edgePerm.pdb");
        }

        this.edgePermPDBIndexed = true;
        this.onIndexComplete();
    }
    void onIndexComplete() {
        if(this.cornerPDBIndexed && this.edge1PDBIndexed && this.edge2PDBIndexed && this.edgePermPDBIndexed && this.sixEdge1Indexed && this.sixEdge2Indexed){
            solving = false;

            System.out.println("Korf indexing complete.");
        }
    }

    void indexSixEdgeG1Database(){
        solving = true;
        if(!this.sixEdge1PDB.readDatabaseFromFile(databasePrefix+"sixEdgeG1.pdb")){
            BreadthFirstCubeSearch indexer = new BreadthFirstCubeSearch();
            CubeModel cm = new CubeModel();
            DatabaseGoal edgeG1Goal = new DatabaseGoal(this.sixEdge1PDB);
            TwistStore twistStore = new TwistStore(cm);

            System.out.println("Goal 2: " +edgeG1Goal.getDescription());

            indexer.search(edgeG1Goal, cm, twistStore);
            this.sixEdge1PDB.writeDatabaseToFile(databasePrefix+"sixEdgeG1.pdb");
        }

        this.sixEdge1Indexed = true;
        this.onIndexComplete();
    }

    void indexSixEdgeG2Database(){
        solving = true;
        if(!this.sixEdge2PDB.readDatabaseFromFile(databasePrefix+"sixEdgeG2.pdb")){
            BreadthFirstCubeSearch indexer = new BreadthFirstCubeSearch();
            CubeModel cm = new CubeModel();
            DatabaseGoal edgeG1Goal = new DatabaseGoal(this.sixEdge2PDB);
            TwistStore twistStore = new TwistStore(cm);

            System.out.println("Goal 2: " +edgeG1Goal.getDescription());

            indexer.search(edgeG1Goal, cm, twistStore);
            this.sixEdge2PDB.writeDatabaseToFile(databasePrefix+"sixEdgeG2.pdb");
        }

        this.sixEdge2Indexed = true;
        this.onIndexComplete();
    }

}
