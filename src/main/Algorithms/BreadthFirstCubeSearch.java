package main.Algorithms;

import main.Model.CubeModel;
import main.Model.DatabaseGoal;
import main.Utils.TwistStore;
import main.Utils.BFSNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;

public class BreadthFirstCubeSearch extends CubeSearcher {

    public ArrayList<Byte> moveToNode(BFSNode node, TwistStore moveStore, ArrayList<Byte> moveIndices){
        LinkedList<Byte> moveStack = new LinkedList<>();
        byte moveIndex;

        while(node.parent != null) {
            moveStack.push(node.moveIndex);
            node = node.parent;
        }

        while(!moveStack.isEmpty()) {
            moveIndex = moveStack.pop();
            moveIndices.add(moveIndex);
            moveStore.move(moveIndex);
        }
        return moveIndices;
    }

    public void search(DatabaseGoal goal, CubeModel cm, TwistStore moveStore) {
        byte numMoves = moveStore.getNumMoves();
        int maxDepth = 0;
        int visited = 0;
        int maxQueueSize = 0;
        ArrayDeque<BFSNode> moveQueue = new ArrayDeque<>();
        BFSNode currentNode;
        ArrayList<Byte> moveIndices = new ArrayList<>();
        long start = System.currentTimeMillis();
        long end;

        if (goal.isSatisfied()) {
            return;
        }
        moveQueue.addLast(new BFSNode((byte) 0xFF, null));
        goal.index(cm, (byte) 0);

        while (!moveQueue.isEmpty()) {
            //Next node of the queue
            currentNode = moveQueue.removeFirst();

            //Visit the node
            moveIndices.clear();
            moveIndices = this.moveToNode(currentNode, moveStore, moveIndices);
            visited++;

            if (maxDepth < moveIndices.size()) {
                end = System.currentTimeMillis();
                System.out.println("BFS: Finished depth " + maxDepth + " (indexing depth " + (maxDepth) + "). Elapsed time " + String.format("%-8s", (((double) (end - (start)) / 1000))) + " seconds. Indexed " + String.format("%-8s", goal.pdb.getNumItems()) + " Items.");
                maxDepth++;
            }

            for (byte moveIndex = 0; moveIndex < numMoves; moveIndex++) {
                if (moveIndices.isEmpty() || !(this.pruner.prune(moveStore.getMove(moveIndex), moveStore.getMove(moveIndices.get(moveIndices.size() - 1))))) {
                    moveStore.move(moveIndex);

                    if (goal.index(cm, (byte) (moveIndices.size() + 1))) {
                        moveQueue.addLast(new BFSNode(moveIndex, currentNode));
                        if (goal.isSatisfied()) {
                            moveIndices.add(moveIndex); //push_back

                            System.out.println("BFS: Goal was satisfied in " + moveIndices.size() + " moves. Visited " + visited + " nodes. Max queue size: " + maxQueueSize);
                            return;
                        }
                    }
                    moveStore.invert(moveIndex);
                }
            }
            if (moveQueue.size() > maxQueueSize) maxQueueSize = moveQueue.size();
            //this.revertMoves(currentNode, moveStore);
            while (currentNode.parent != null) {
                moveStore.invert(currentNode.moveIndex);
                currentNode = currentNode.parent;
            }
        }
        System.out.println("BFS: Goal not fulfilled, but move queue is empty.");
        System.out.println("Number of items in PDB: " + goal.pdb.getNumItems() + "\nSize of Database: " + goal.pdb.getSize());
    }
}
