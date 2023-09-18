package main.Utils;

public class BFSNode {
    public byte moveIndex;
    public BFSNode parent;

    public BFSNode(byte moveIndex, BFSNode parent) {
        this.moveIndex = moveIndex;
        this.parent = parent;
    }
}
