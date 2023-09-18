package main.Tests;
import main.Algorithms.PermutationIndexer;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermutationIndexerTest {

    @Test
    public void testSimplePermutationIndexing() {
        PermutationIndexer pi1 = new PermutationIndexer(3,3);
        int[][] perm1 = new int[][]{{0,1,2},{0,2,1},{1,0,2},{1,2,0},{2,0,1},{2,1,0}};
        for(int i = 0; i < 6; i++){
            assertEquals(pi1.rank(perm1[i]), i);
        }

    }
    @Test
    public void testPartialPermutationIndexing() {
        PermutationIndexer pi2 = new PermutationIndexer(4,2);
        int[][] perm2 = new int[][]{{0, 1}, {0, 2}, {0, 3}, {1, 0}, {1, 2}, {1, 3},
                {2, 0}, {2, 1}, {2, 3}, {3, 0}, {3, 1}, {3, 2}};
        for(int i = 0; i < 12; i++) {
            assertEquals(pi2.rank(perm2[i]), i);
        }
    }
}
