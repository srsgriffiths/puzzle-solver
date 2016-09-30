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
public class NodeMatrixTest {
   
   
    public boolean[][] testarray = {{true, false, false, false, true},
                            {false, true, false, true, false}, 
                            {false, true, true, false, false},
                            {false, false, true, false, false},
                            {true, false, false, true, true}};
    int[] colsizes = {2, 2, 2, 2, 2};
    int[] rowsizes = {2, 2, 2, 1, 3};
    
    public NodeMatrix testmatrix = new NodeMatrix(testarray);
    
    @Test
    public void testMatrix() {
        assertEquals(5, testmatrix.m);
        assertEquals(5, testmatrix.n);
        Node currentcol = testmatrix.root.right;
        for (int i = 0 ; i < 5 ; i++){
            testColumn(currentcol, colsizes[i]);
            currentcol = currentcol.right;
        }
        testRow(testmatrix.root.right.down, 2);
        testRow(testmatrix.root.right.right.down, 2);
        testRow(testmatrix.root.right.right.down.down, 2);
        testRow(testmatrix.root.right.right.right.down.down, 1);
        testRow(testmatrix.root.right.down.down, 3);
        
    }
    
    private void testRow(Node start, int rowsize){
        int count = 0;
        Node current = start;
        do {
            count++;
            current = current.right;
        } while (current != start);
        assertEquals(rowsize, count);
    }
    
    private void testColumn(Node header, int size){
        int count = 0;
        Node current = header.down;
        do {
            count ++;
            current = current.down;
        } while (current != header);
        assertEquals(size, count);
    }
    
    @Test
    public void testChooseColumn() {
        assertEquals(testmatrix.root.right, testmatrix.chooseColumn());
    }
    
    
    @Test
    public void testCoverColumn(){
        Node c = testmatrix.root.right.right;
        Node d = testmatrix.root.left.left;
        testmatrix.coverColumn(c);
        testmatrix.coverColumn(d);
        int[] rows = {1, 1, 1};
        int[] colind = {0,2,4};
        Node current = testmatrix.root.right;
        int i = 0;
        do {
            assertEquals(colind[i], ((ColumnHeader) current).index);
            assertEquals(rows[i], current.size);
            current = current.right;
            i++;
        } while (current != testmatrix.root);
        testmatrix.uncoverColumn(d);
        testmatrix.uncoverColumn(c);
        testMatrix();
    }
    
}

