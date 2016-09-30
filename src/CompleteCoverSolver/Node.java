/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteCoverSolver;

/**
 * represents a node in a doubly linked array  
 * @author Sam
 */
public class Node {
    
    public Node right, left, up, down;
    public ColumnHeader columnhead;
    public int size = -1; //-1 for nodes and root, number of entries in col for columnhead
    
    public Node() {}
    public Node(ColumnHeader columnhead){
        this.columnhead = columnhead;
    }
   
}
