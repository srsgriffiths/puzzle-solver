/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteCoverSolver;

/**
 *
 * @author Sam
 */
public class NodeMatrix {
    
    public Node root;
    // m*n marix
    public int m;
    public int n;


    public NodeMatrix(boolean[][] inmat){
        m = inmat.length;
        n = inmat[0].length;
        //System.out.println("m, n = " + m+ " " + n);
        
        root = new Node();
        
        //extra row for columnheaders
        Node[][] nodearray = new Node[m+1][n];
        
        
        //make column headers
        Node current = root;
        for (int i = 0 ; i < n ; i++){
            ColumnHeader ch = new ColumnHeader(i);
            nodearray[0][i] = ch;
            current.right = ch;
            ch.left = current;
            current = ch;
        }
        current.right = root;
        root.left = current;
        current = null;
        
        //initialize nodes and set left and right
        for (int i = 1 ; i < m+1; i++){
            boolean first = true;
            Node firstnode = null;
            for (int j = 0 ; j < n ; j++){
                if (inmat[i-1][j]){
                    Node c = new Node((ColumnHeader) nodearray[0][j]);
                    nodearray[i][j] = c;
                    
                    if (first){
                        current = c;
                        firstnode = c;
                        first = false;
                    } else {
                        current.right = c;
                        c.left = current;
                        current = c;
                    }
                }
            }
            assert(!first);
            current.right = firstnode;
            firstnode.left = current;
            current = null;
        }
        
        //set up and down
        
        for (int j = 0 ; j < n ; j++){
            boolean first = true;
            ColumnHeader colhead = (ColumnHeader) nodearray[0][j];
            for (int i = 1 ; i < m+1 ; i++){
                if(inmat[i-1][j]){
                    colhead.size++;
                    
                    Node c = nodearray[i][j];
                    if (first){
                        colhead.down = c;
                        c.up = colhead;
                        current = c;
                        first = false;
                        //System.out.println("firstline" + i + j);
                    } else {
                        current.down = c;
                        c.up = current;
                        current = c;
                        //System.out.println("node" + i + j);
                    }
                }
            }
            if (!first){
                current.down = colhead;
                colhead.up = current;
            }
        }
    }

    /**
     * returns column header with smallest size (the first it finds if multiple with same value
     * returns null if column in matrix with size zero
     * @return
     */
    Node chooseColumn() {
        int min = m;
        Node current = root;
        Node ret = null;
        while (current.right != root) {
            
            current = current.right;
            
            assert (current.size > -1);
            
            if (current.size < min) {
                min = current.size;
                if (min == 0) {
                    return null;
                }
                ret = current;
            }
        }
        assert (ret != null);
        //System.out.println("chooseColumn: min = " + min);
        //System.out.println("chooseColumn: index = "+ ((ColumnHeader) ret).index);
        return ret;
    }

    /**
     * Covers col c and all rows which have entry in c
     * @param c
     */
    void coverColumn(Node c) {
        assert (c.size > -1);
        c.right.left = c.left;
        c.left.right = c.right;
        //as in index i,j
        Node j = c;
        while (j.down != c) {
            j = j.down;
            Node i = j;
            while (i.right != j) {
                i = i.right;
                i.up.down = i.down;
                i.down.up = i.up;
                i.columnhead.size--;
            }
        }
    }

    void uncoverColumn(Node c) {
        assert (c.size > -1);
        c.right.left = c;
        c.left.right = c;
        //as in index i,j
        Node j = c;
        while (j.down != c) {
            j = j.down;
            Node i = j;
            while (i.right != j) {
                i = i.right;
                i.up.down = i;
                i.down.up = i;
                i.columnhead.size++;
            }
        }
    }
    
}
