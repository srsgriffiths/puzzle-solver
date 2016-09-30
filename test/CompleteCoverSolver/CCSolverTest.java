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
public class CCSolverTest {
    
    public boolean[][] testarray = {{true, false, false, false, true},
                                    {false, true, false, true, false}, 
                                    {false, true, true, false, false},
                                    {false, false, true, false, false},
                                    {true, false, false, true, true}};
    int[] colsizes = {2, 2, 2, 2, 2};
    int[] rowsizes = {2, 2, 2, 1, 3};
    
    public NodeMatrix testmatrix = new NodeMatrix(testarray);
    public CCSolver testsolver = new CCSolver(testmatrix);
    
    public void testprintSolutions(){
        
    }
    
    
    public void testCompileSolutions(){
        
    }
    
    @Test
    public void testFindSolutions(){
        testsolver.findSolutions();
    }
    
    
    
}
