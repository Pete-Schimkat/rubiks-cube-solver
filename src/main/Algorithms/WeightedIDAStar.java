package main.Algorithms;

import main.Enums.IndexEnums;
import main.Model.CubeModel;
import main.Utils.TwistStore;
import main.PatternDatabase.PatternDatabase;
import main.Utils.WeightedIDAStarNode;

import java.util.*;

public class WeightedIDAStar extends CubeSearcher {
    PatternDatabase pdb;
    public static final TwistStore ms = new TwistStore(new CubeModel());
    private static final double INF = Double.MAX_VALUE;
    public double searchWeight;
    public WeightedIDAStar(PatternDatabase pdb, double searchWeight) {
        this.pdb = pdb;
        this.searchWeight = searchWeight;
    }
    public SearchResult solve(WeightedIDAStarNode root) {
        long start = System.currentTimeMillis();
        double bound = this.pdb.getNumMoves(root.cm)*searchWeight;
        int i = 0;
        while(true){
            SearchResult result =  this.search(root, (byte) 0, bound, new ArrayList<>());
            if(result.isGoalFound()) return result;
            if(result.getCost() == INF) {
                return result;
            }
            bound = result.getCost();
            i++;

            System.out.println("WIDA*: Finished search depth " + String.format("%-3s", i+".") + " Elapsed time: " + String.format("%-8s", (((double) (System.currentTimeMillis() - (start)) / 1000))) + " seconds.");
        }
        //return new ArrayList<>(path);
    }
    public SearchResult search(WeightedIDAStarNode node, int g, double bound, List<WeightedIDAStarNode> path) {
        double f = (double) g + searchWeight*this.pdb.getNumMoves(node.cm);
        if( f > (bound)) return new SearchResult(false, f, null);
        if(node.cm.isSolved()) {
            path.add(node);
            return new SearchResult(true, g, path);
        }

        double min = INF;
        PriorityQueue<WeightedIDAStarNode> successors = this.successors(node);
        //System.out.println(successors.size());
        for(WeightedIDAStarNode succ : successors) {
            if(!path.contains(succ)) {
                List<WeightedIDAStarNode> newPath = new ArrayList<>(path);
                newPath.add(node);
                SearchResult result = search(succ, g+1,bound,newPath);
                if(result.isGoalFound()) {
                    return result;
                }
                if(result.getCost() < min) {
                    min = result.getCost();
                }
            }
        }
        return new SearchResult(false,min, null);

    }

    private double weightedHeuristic(WeightedIDAStarNode node, double bound) {
        return searchWeight*this.pdb.getNumMoves(node.cm);
    }
    public PriorityQueue<WeightedIDAStarNode> successors(WeightedIDAStarNode node) {
        PriorityQueue<WeightedIDAStarNode> successors = new PriorityQueue<>(Comparator.comparingDouble(a->a.estimatedSuccessMoves));
        IndexEnums.MOVE move;
        CubeModel copyCube;
        double estimatedSuccessMoves;
        for (int i = 0; i < ms.getNumMoves(); i++) {
            move = ms.getMove(i);
            copyCube = new CubeModel(node.cm);
            //copyCube = node.cm.clone();
            copyCube.move(move);
            if (node.depth == 0 ||!this.pruner.prune(move, node.move)) {
                estimatedSuccessMoves = (double) (1 + node.depth + (this.pdb.getNumMoves(copyCube)*searchWeight));
                successors.add(new WeightedIDAStarNode(node, copyCube, move, (byte) (node.depth + 1), estimatedSuccessMoves));
                //System.out.println("node depth:"+node.depth);
            }
        }
        return successors;
    }

    private ArrayDeque<WeightedIDAStarNode> copy(ArrayDeque<WeightedIDAStarNode> a) {
        ArrayDeque<WeightedIDAStarNode> ret = new ArrayDeque<>();
        for(WeightedIDAStarNode node : a) {
            ret.add(new WeightedIDAStarNode(node));
        }
        return ret;
    }

    public static class SearchResult {
        private final boolean goalFound;
        private final double cost;
        private final List<WeightedIDAStarNode> path;

        public SearchResult(boolean goalFound, double cost, List<WeightedIDAStarNode> path) {
            this.goalFound = goalFound;
            this.cost =cost;
            this.path = path;
        }
        public boolean isGoalFound() {
            return goalFound;
        }

        public double getCost() {
            return cost;
        }

        public List<WeightedIDAStarNode> getPath() {
            return path;
        }
    }
 }

