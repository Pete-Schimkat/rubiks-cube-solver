package main.Utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is mostly used as an array for 4-bit values. Since there are no inherent 4-bit
 * data-structures in java, the underlying structure is a byte-array that is accessed
 * based on bit-shifted indices.
 */
public class NibbleArray {

    private int length;
    private int size;
    public byte[] data;

    public NibbleArray(int size, byte fill) {
        this.size = size;
        //if(size%2 == 1) { //Not sure if this is correct or necessary, but leaving it in for now
        data = new byte[(size / 2) + 1];
        //} else {
        //    data = new byte[size/2];
        //}
        length = data.length;
        Arrays.fill(data, fill);
    }

    public NibbleArray(int size, byte fill, boolean read) {
        if (!read) System.out.println("ERROR when trying to read nibble-array from file.");
        this.size = size;
        data = new byte[size];
        length = data.length;
        Arrays.fill(data, fill);
    }

    public byte[] getData() {
        return this.data;
    }

    public int getStorageSize() {
        return this.size;
    }

    public int getLength() {
        return this.length;
    }

    /**
     * Gets the element at the specified index.
     *
     * @param index - database-index of the wanted element
     * @return the element at position pos
     */
    public byte get(int index) {
        int i = index / 2;
        byte value = this.data[i];
        if (index % 2 == 1) {
            return (byte) (value & 0x0F);
        } else {
            return (byte) ((value & 0xFF) >>> 4);
        }
    }


    //https://stackoverflow.com/questions/36454932/weird-behaviour-of-bit-shifting-with-byte-in-java


    /**
     * Sets the element at the specified index.
     *
     * @param index - position of the element
     * @param value - value that will be set
     */
    public void set(int index, byte value) {
        int i = index / 2;
        byte currentValue = this.data[i];
        if (index % 2 == 1) { //last 4 bits
            this.data[i] = (byte) ((currentValue & 0xF0) | (value & 0x0F));
        } else { //first 4 bits
            this.data[i] = (byte) ((value << 4) | (currentValue & 0x0F));
        }
    }

    /**
     * Moves all of the data into an ArrayList. This takes up double the space
     * but might be useful for faster computations as no bit-wise operations are required
     * to access specific entries in the database.
     *
     * @return - ArrayList filled with all entries from the database
     */
    public ArrayList<Byte> inflate() {
        ArrayList<Byte> destination = new ArrayList<>();
        for (int i = 0; i < this.getStorageSize(); i++) {
            destination.add(this.get(i));
        }
        return destination;
    }

    /**
     * "Resets" the database by filling the underlying array with a pre-specified value.
     *
     * @param fill - value that will be used to fill the array
     */
    public void reset(byte fill) {
        Arrays.fill(this.data, fill);
    }

}
