/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteCoverSolver;

import java.util.HashMap;
import java.util.BitSet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * implements a complete cover problem (ccp) solver via Knuth's algorithm X
 * @author Sam
 */
public class CCSolver {
    
    
    //ccp to be solved
    protected NodeMatrix ccproblem;
    
    
    //list of BitSets storing solutions
    //null indicates end of solution
    protected ArrayList<boolean[]> solutions = new ArrayList<boolean[]>();
    
    
    //path to solutions, incorrect path ends in null
    //consecutive nodes ending in root (no colname&&size == -1) is solution
    //two consectutive unused keys -> end
    //placeholder indicates algorithm backtracking up a level
    protected HashMap<Integer, Node> path = new HashMap();
    //path counter
    protected int k = 0;
    //node used to indicate algorithm backtracking in path 
    protected Node placeholder = new Node();
    
    public CCSolver() {};
    
    public CCSolver(NodeMatrix ccproblem) {
        this.ccproblem = ccproblem;
    };
    
    /**
     * prints solutions to console each boolean separated by a space
     * 
     */
    public void printSolutions(){
        if (solutions == null){
            System.out.println("solutions list not initialized");
            return;
        }
        if (solutions.isEmpty()){
            System.out.println("solutions is empty");
        }
        for (boolean[] ba : solutions){

            if (ba == null){
                System.out.println();
                continue;
            }
            for (int i = 0 ; i < ccproblem.n ; i++){
                System.out.print(ba[i] + " ");
            }
            System.out.println();
        }

    }
    
    
    //@Deprecated
    public void printSolutions(ArrayList<boolean[]> solutions, int size){
        if (solutions == null){
            System.out.println("solutions list not initialized");
            return;
        }
        for (boolean[] ba : solutions){

            if (ba == null){
                System.out.println();
                continue;
            }
            for (int i = 0 ; i < size ; i++){
                System.out.print(ba[i] + " ");
            }
            System.out.println();
        }

    }
    
    /**
     * converts solutions held in path into literal solutions with rows of booleans 
     * representing solution rows
     * 
     * if multiple solutions, they're separated by a null row
     */
    
    protected void compileSolutions(){
        solutions.clear();
        ArrayList<boolean[]> temp = new ArrayList();
        
        for (int i = 0 ; !(path.get(i) == null && path.get(i+1) == null) ; i++){
            Node r = path.get(i);
            //System.out.println("compiling" + i);
            
            if(r == placeholder){
                continue;
            } else if(r == null){        //incorrect soltion
                for (int j = i ; path.get(j+1) == placeholder ; j++){
                    temp.remove(temp.size() - 1);
                }
                continue;
                
            } else if (r.columnhead == null && r.size == -1) { //end of correct solution
                temp.add(null);
                solutions.addAll(temp);
                temp.remove(temp.size() - 1);
                for (int j = i ; path.get(j+1) == placeholder ; j++){
                    temp.remove(temp.size() - 1);
                    //System.out.println("placeholder removed");
                }
                if (!temp.isEmpty()){
                    temp.remove(temp.size() - 1);
                }
                //printSolutions(temp);
                continue;
            }
            
            boolean[] row = new boolean[ccproblem.n];
            
            
            Node s = r;
            do {
                row[s.columnhead.index] = true;
                s = s.right;
            } while (s != r);
            
            temp.add(row);
            //printSolutions(temp);
        }
    }
    /**
     * solves ccproblem if initialized
     * compiles solutions and prints each to console
     */
    public void findSolutions(){
        if (ccproblem == null){
            //System.out.println("ccproblem not initialized");
        } else {
            solve();
            compileSolutions();
            printSolutions();
        }
        
    }
    
    /**
     * solves ccproblem if initialized using Knuth's algorithm X
     */
    protected void solve() {
        
        if (ccproblem.root.right == ccproblem.root){
            path.put(k, ccproblem.root);
            //System.out.println("FOUND A SOLUTION, k = " + k);
            k++; 
            return;
        }
        
        Node colhead = ccproblem.chooseColumn();
                
        
        if (colhead == null){
            //System.out.println("Dead end, k = " + k);
            k++;
            
            return;
        }
        
        Node c = colhead;
        ccproblem.coverColumn(colhead);
        while(c.down != colhead){
            c = c.down;
            path.put(k, c);
            //System.out.println("k = " + k);
            k++;
            
            Node j = c.right;
            while (j != c) {
                ccproblem.coverColumn(j.columnhead);
                j = j.right;
            } 
            
            solve();
            
            while (j != c.right) {
                j = j.left;
                ccproblem.uncoverColumn(j.columnhead);
            } 
        }
        ccproblem.uncoverColumn(colhead);
        path.put(k, placeholder);
        //System.out.println("up a level, k = "+ k);
        k++;
        
    }
}
