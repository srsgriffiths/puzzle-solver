/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KakuroSolver;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author Sam
 */
public class KakuroSolverTest {
    
    
    public KakuroSolver testks;
    
    public KakuroSolverTest() {
        
    }

    @Before
    public void setupInstance() {
        int [][] hlsetup = {{0, 0}, {0, 0}, {0, 23}, {0, 14}, {0,6}, {0,7}, {0,4}, {0,0}, {0,31}, {0,7}, {0, 23},
                            {0,0}, {15, 16}, {5}, {18, 0}, {3}, 
                            {31,0}, {6}, {20, 4}, {3},
                            {14, 0}, {2}, {4, 40}, {2}, {14, 24}, {4},
                            {0,0}, {4, 6}, {2}, {0,0}, {15, 24}, {3}, {0,25}, {0,6},
                            {11,0}, {3}, {16, 0}, {2}, {17, 0}, {3},
                            {16, 0}, {3}, {17, 16}, {2}, {7, 0}, {3},
                            {0,0}, {0, 19}, {24, 21}, {3}, {0, 24}, {4, 23}, {2}, {0, 4},
                            {30, 0}, {4}, {16, 6}, {2}, {3, 16}, {2}, 
                            {21, 0}, {3}, {29, 0}, {6},
                            {9, 0}, {3}, {34, 0}, {5}, {0, 0}};
        
        Header[] hl = new Header[121];
        int index = 0;
        for (int[] i : hlsetup){
            if (i.length == 1){
               index += i[0]; 
            } else {
                hl[index] = new Header(i[0], i[1]);
                index++;
            }
            
        }
        testks = new KakuroSolver(hl, 11, 11, 9);
    }
    
    //@After
    //public void tearDownInstance(){}
    
    
    @Test
    public void testSetupPossibles() {
        System.out.println("setupPossibles");
        testks.setupRowColSize();
        testks.setHeaderPossibles();
        testks.setEntryPossibles();
        testks.sortEntryList();
        testks.sortHeaderRowList();
        testks.sortHeaderColList();
        //for (Entry e : testks.entrylist){
        //    System.out.println(e.size + " " + e.possibles.size());
        //}
        testks.printPuzzle();
    }


    @Test
    public void testGetPossibleSummands() {
        System.out.println("getPossibleSummands");
        byte[][] testlist = {{4, 2}, {1, 3},
                            {5, 2}, {1,2, 3, 4},
                            {9, 2}, {1, 2,3, 4,5, 6, 7, 8},
                            {16, 2}, {7, 9},
                            {15, 2}, {6,7,8,9}, 
                            {14, 2},{5,6,8,9},
                            {45, 9}, {1, 2,3, 4, 5, 6,7, 8, 9},
                            {40, 8}, {1,2,3,4,5,6,7,8,9},
                            {36, 8}, {1,2,3,4,5,6,7,8},
                            {4, 1}, {4},
                            {6, 3}, {1,2, 3},
                            {7, 3}, {1, 2, 4},
                            {8, 3}, {1, 2, 3, 4, 5},
                            {11, 3}, {1, 2,3, 4,5, 6, 7, 8},
                            {15, 3}, {1, 2, 3, 4,5, 6, 7, 8, 9},
                            {19, 3}, {2, 3, 4, 5, 6, 7, 8, 9},
                            {20, 3}, {3, 4, 5, 6, 7, 8, 9},
                            {22, 3}, {5, 6, 7, 8, 9},
                            {23, 3}, {6, 8, 9},
                            {24, 3}, {7, 8, 9}
        };
        for (int i = 0 ; i < testlist.length ; i += 2){
            byte[] e = testlist[i+1];
            //HashSet<Byte> e = new HashSet<>();
            //for (byte b :testlist[i+1]){
            //    e.add(b);
            //}
            //byte[] r = testks.getPossibleSummands((int) testlist[i][0], (int) testlist[i][1]);
            HashSet<Byte> r = testks.getPossibleSummands((int) testlist[i][0], (int) testlist[i][1]);
           
            int[] ra = r.parallelStream().mapToInt(k->k).toArray();
            
            byte[] b = new byte[ra.length];
            for (int j = 0 ; j < ra.length ; j++){
                b[j] = (byte) ra[j];
                //System.out.print( ra[i]);
            }
            Arrays.sort(b);
            
            //System.out.println(Arrays.toString(b));
            //System.out.println();
            assertArrayEquals(e, b);
        }
    }
/*
    @Test
    public void testSetupRowColSize() {
        System.out.println("setupRowColSize");
        KakuroSolver instance = null;
        instance.setupRowColSize();
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRowSize() {
        System.out.println("getRowSize");
        Header h = null;
        KakuroSolver instance = null;
        int expResult = 0;
        int result = instance.getRowSize(h);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetColSize() {
        System.out.println("getColSize");
        Header h = null;
        KakuroSolver instance = null;
        int expResult = 0;
        int result = instance.getColSize(h);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetHeaderPossibles() {
        System.out.println("setHeaderPossibles");
        KakuroSolver instance = null;
        instance.setHeaderPossibles();
        fail("The test case is a prototype.");
    }
    */
    
}
