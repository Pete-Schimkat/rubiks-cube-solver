package main.Algorithms;

import java.util.BitSet;

/**
 * Generates the Indices for possible permutations of the Rubiks Cube, namely for the Corners and both halves of the edges.
 */
public class PermutationIndexer {

    int N; //<- permutation size
    int K; //<- pick size
    int[] binaryOnesLookup;
    int[] precomputedFactorials;


    public PermutationIndexer() {
        this(12, 7);
    }
    public PermutationIndexer(int N, int K) {
        //populate binary Ones Lookup:
        this.N = N;
        this.K = K;
        this.binaryOnesLookup = new int[((1 << N))];
        this.precomputedFactorials = new int[K];
        for(int i=0; i < ((1 << N)-1); i++){
            this.binaryOnesLookup[i] = Integer.bitCount(i);
        }
        //populate precomputed Factorials:
        for(int j = 0; j< K; j++) {
            this.precomputedFactorials[j] = pick(N-1-j, K-1-j);
        }
    }

    /**
     * Computes the lexicographic rank of a permutation (which is used to index the pattern database).
     *
     * @param perm array needs to be of length K (for now)
     * @return - lexicographic rank of the passed permutation
     */
    public int rank(int[] perm) {
        //Holds lehmer-code in a factorial number system
        int[] lehmer = new int[this.K];

        //BitSet of seen digits in the permutation (a set bit signifies that the digit has been seen)
        BitSet seen = new BitSet(this.N);

        //First digit of the permutation is always the first digit of the lehmer code
        lehmer[0] = perm[0];

        //Mark that digit as seen (For java Bitsets, the LSB or the Bit of index 0 are the rightmost bit
        seen.set(N-1-perm[0]);

        long value;
        for(int i = 1; i<K; i++){
            //Mark the currently viewed digit of the perm as seen
            seen.set(N-1-perm[i]);

            //To get the number of seen digits to the left of the current digit, we convert the bitset to a long
            //and right-shift it to count the ones afterwards (look them up)
            value = toLong(seen);
            int amountOnes = this.binaryOnesLookup[(Math.toIntExact(value) >> (N - perm[i]))];  //Math-method to ensure that we do not lose information

            lehmer[i] = perm[i] - amountOnes;
        }
        //Convert Lehmer-Code to base 10 by multiplying with factorials
        int index = 0;
        for(int k = 0; k < K; k++){
            index = index + (lehmer[k] * this.precomputedFactorials[k]);
        }
        return index;

    }
    public static long toLong(BitSet bs){
        long[] longValue = bs.toLongArray();
        long value;
        if(longValue.length == 0){
            value = 0;
        } else {
            value = longValue[0];
        }
        return value;
    }
    //Unsigned Integers: Use Integer.compareUnsigned etc. when comparing, Dividing, right shifting, casting
    //Source: https://stackoverflow.com/questions/9854166/declaring-an-unsigned-int-in-java
    public static int factorial(int n) {
        if (n != 0) {
            return n * (factorial(n - 1));
        }
        return 1;
    }
    public static int pick(int n, int k) {
        return Integer.divideUnsigned(factorial(n),factorial(n-k));
    }

}
