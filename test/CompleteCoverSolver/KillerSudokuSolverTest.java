/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteCoverSolver;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class KillerSudokuSolverTest {
    
    public KillerSudokuSolver testkss;
    public KillerSudokuSolverTest() {
        ArrayList<int[]> testcellspec = new ArrayList<>();
        /*
        int[][] testcells = {{16, 0, 9, 10},
                             {9, 18, 19},
                             {15, 1 , 2},
                             {7, 11, 20, 29},
                             {21, 3, 4, 12},
                             {4, 21, 30},
                             {7, 13, 14},
                             {6, 5, 6},
                             {14, 22, 31},
                             {24, 15, 24, 23, 32},
                             {15, 7, 16},
                             {7, 8, 17},
                             {8, 25, 26},
                             {16, 27, 36},
                             {7, 28, 37},
                             {11, 38, 47},
                             {5, 45, 54},
                             {17, 46, 55},
                             {18, 39, 40, 48, 49, 58},
                             {16, 41, 50},
                             {14, 33, 42},
                             {3, 34, 43},
                             {11, 35, 44},
                             {14, 52, 53},
                             {4, 51, 60},
                             {11, 63, 72},
                             {3, 64, 73},
                             {15, 65, 74, 75},
                             {15, 56, 57},
                             {16, 66, 67},
                             {8, 76, 77},
                             {9, 59, 68, 69},
                             {10, 61, 70},
                             {5, 62, 71},
                             {24, 78, 79, 80}};
        */
        
        int[][] testcells = {{7, 0, 1},
                             {9, 6, 7},
                             {5, 2, 8},
                             {7, 3, 4},
                             {7, 5, 11},
                             {10, 9, 15},
                             {3, 10, 16},
                             {4, 12, 18},
                             {11, 13, 14},
                             {6, 19, 20},
                             {7, 17, 23},
                             {8, 21, 22},
                             {7, 24, 30},
                             {4, 25, 26},
                             {10, 31, 32},
                             {3, 27, 33},
                             {11, 28, 34},
                             {7, 29, 35}};
        for (int[] cell : testcells){
            testcellspec.add(cell);
        }
        testkss = new KillerSudokuSolver(testcellspec, 3, 2, 2, 3);
    }
    
    @Test
    public void testToCCProblem() {
        KillerSudokuSolverTest ksst = new KillerSudokuSolverTest();
        testkss.toCCProblem();
        testkss.solve();
        testkss.compileSolutions();
        testkss.printSolutions();
        
    }
    
    
    
    @Test
    public void testGetPossibleSumands() {
        KillerSudokuSolverTest ksst = new KillerSudokuSolverTest();
        int[] test = {9,7};
        int[] result = testkss.getPossibleSumands(16, 2);
        //for (int i : result){
        //    System.out.print(i);
        //}
        //System.out.println(result.length);
        
        
    }
    /*
    @Test
    public void testUpdateRetMask() {
    }

    @Test
    public void testIncrementMaskNTrue() {
    }

    @Test
    public void testNTrue() {
    }

    @Test
    public void testIncrementMask() {
    }
    */
}
