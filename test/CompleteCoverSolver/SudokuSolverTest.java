/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteCoverSolver;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sam
 */
public class SudokuSolverTest {
    
    public SudokuSolverTest() {
    }

    /**
    @Test
    public void testGetBlock() {
        System.out.println("blocks");
        byte[] testpuzzle = new byte[36];
        for (byte i = 0 ; i < 36 ; i++){
            testpuzzle[i] = i;
        }
        SudokuSolver testsolver = new SudokuSolver(testpuzzle, 3, 2, 2, 3);
        for (int i = 0; i < 3 ; i++){
            for (int j = 0 ; j < 2 ; j++){
                byte[] block = testsolver.getBlock(i, j);
                for (byte e : block){
                    System.out.print(e + " ");
                }
                System.out.println();
            }
        }
        System.out.println();
    }
    
    @Test
    public void testGetRow(){
        System.out.println("rows");
        byte[] testpuzzle = new byte[36];
        for (byte i = 0 ; i < 36 ; i++){
            testpuzzle[i] = i;
        }
        SudokuSolver testsolver = new SudokuSolver(testpuzzle, 3, 2, 2, 3);
        for (int i = 0 ; i <6 ; i++){
            byte[] block = testsolver.getRow(i);
            for (byte e : block){
                System.out.print(e + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    @Test
    public void testGetcColumn(){
        System.out.println("columns");
        byte[] testpuzzle = new byte[36];
        for (byte i = 0 ; i < 36 ; i++){
            testpuzzle[i] = i;
        }
        SudokuSolver testsolver = new SudokuSolver(testpuzzle, 3, 2, 2, 3);
        for (int i = 0 ; i <6 ; i++){
            byte[] block = testsolver.getColumn(i);
            for (byte e : block){
                System.out.print(e + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    */
    /**
    @Test
    public void testFindSolutions(){
        byte[] testpuzzle = {0,0,5,0,6,0,
                             0,0,0,0,3,4,
                             1,0,0,0,0,5,
                             6,0,0,0,0,3,
                             0,0,0,0,5,6,
                             0,0,3,0,2,0};
        
        
        SudokuSolver testsolver = new SudokuSolver(testpuzzle, 3, 2, 2, 3);
        
        testsolver.findSolutions();
    }
    */
    
    @Test
    public void tesToCCProblem(){
        byte[] testpuzzle ={0,0,0,8,0,3,0,0,0,
                            0,0,7,0,6,0,2,0,0,
                            4,0,0,0,0,0,0,0,3,
                            0,1,0,0,8,0,0,7,0,
                            5,4,3,0,0,0,6,8,2,
                            0,7,0,0,2,0,0,4,0,
                            9,0,0,0,0,0,0,0,1,
                            0,0,8,0,9,0,4,0,0,
                            0,0,0,5,0,2,0,0,0};
        
        
        SudokuSolver testsolver = new SudokuSolver(testpuzzle, 3, 3, 3, 3);
        //testsolver.toCCProblem();        
        testsolver.findSolutions();
    }
    
    
}
