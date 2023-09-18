package main.Tests;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import main.Utils.NibbleArray;

import java.util.ArrayList;

public class NibbleArrayTest {

    @Test
    public void testArrayGet() {
        NibbleArray na = new NibbleArray(10,(byte)0xFF);
        assertEquals((byte) ((na.getData()[0]&0xFF)>>>4),na.get(0));
        assertEquals((byte) (na.getData()[0]&0x0F), na.get(1));
        assertEquals((byte) 15, na.get(0));
        assertEquals((byte) 15, na.get(1));
        na.set(0,(byte)13);
        na.set(1,(byte)7);
        assertEquals((byte) ((na.getData()[0]&0xFF)>>4),na.get(0));
        assertEquals((byte) (na.getData()[0]&0x0F), na.get(1));
        assertEquals((byte) 13, na.get(0));
        assertEquals((byte) 7, na.get(1));
    }
    @Test
    public void testArrayInit(){
        NibbleArray na = new NibbleArray(10,(byte)0xFF);
        for(int i = 0; i < na.getStorageSize(); i++) {
            assertEquals(na.get(i), 15);
        }
    }
    @Test
    public void testArraySet(){
        NibbleArray na = new NibbleArray(10,(byte)0xFF);
        na.set(3,(byte) 10);
        na.set(6,(byte) 7);
        na.set(7,(byte) 5);

        assertEquals(na.get(3), 10);
        assertEquals(na.get(6), 7);
        assertEquals(na.get(7), 5);
        assertEquals(na.get(2), 15);
        assertEquals(na.get(5), 15);
        assertEquals(na.get(8), 15);
    }
    @Test
    public void testInflate() {
        NibbleArray na = new NibbleArray(10,(byte)0xFF);
        ArrayList<Byte> nb = na.inflate();
        assertEquals(na.getStorageSize(), nb.size());
        NibbleArray nc = new NibbleArray(11,(byte)0xFF);
        nb = nc.inflate();
        assertEquals(nc.getStorageSize(), nb.size());
    }

    @Test
    public void testReset() {
        NibbleArray na = new NibbleArray(10,(byte)0xFF);
        na.set(3,(byte) 10);
        na.set(6,(byte) 7);
        na.set(8,(byte) 5);
        na.set(2,(byte) 2);
        na.set(4,(byte) 13);
        na.set(0,(byte) 6);
        na.reset((byte)0);
        for(int i = 0; i < na.getStorageSize(); i++) {
            assertEquals(0, na.get(i));
        }
        na.reset((byte) 0xFF);
        for(int i = 0; i < na.getStorageSize(); i++) {
            assertEquals(15, na.get(i));
        }





    }
    private static void checkArray(NibbleArray array){
        for(int i = 0; i < array.getStorageSize(); i++){
            System.out.println(ByteToString(array.get(i)));
        }
    }
    private static String ByteToString(byte b) {
        return (Byte.toUnsignedInt(b)+ " in binary is: " +String.format("%8s", Integer.toBinaryString(b& 0xFF)).replace(' ', '0'));
    }
}
