/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteCoverSolver;

import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class SudokuSolver extends CCSolver {
    protected byte[] spuzzle;
    protected ArrayList<byte[]> spuzzle_solutions = new ArrayList();
    // m,n are dims of whole puzzle, measuring block (ie normal = 3,3)
    //x,y are dims of blocks, ie normal puzzle = 3, 3
    protected int m, n, x, y, dims, size;
    
    
    public SudokuSolver() {};
    public SudokuSolver(byte[] spuzzle, int m, int n, int x, int y){
        this.spuzzle = spuzzle;
        this.m = m;
        this.n = n;
        this.x = x;
        this.y = y;
        size = x*y;
        dims = size*m*n;    
    }
    
    @Override
    public void findSolutions(){
        if (spuzzle == null){
            System.out.println("spuzzle not initialized");
        } else {
            toCCProblem();
            //System.out.println("toCCProblem");
            
            solve();
            //System.out.println("Solved");
            
            compileSolutions();
            //System.out.println("solutions compiled");
            
            //super.printSolutions();
            //System.out.println("super.printSolutions");
            
            printSolutions();
            //System.out.println("printsolution");
        }
        
    }
    
    @Override
    public void printSolutions(){
        if (spuzzle_solutions == null){
            System.out.println("spuzzle_solutions not initialized");
            return;
        }
        
        int k = 1;
        for (byte[] solution : spuzzle_solutions){
            System.out.println(k + ".");
            for (int i = 0 ; i < dims ; i++){
                if (i%(y*n) == 0 && i != 0){
                    System.out.println();
                }
                System.out.print(solution[i] + " ");
                
            }
            System.out.println();
            System.out.println();
            k++;
        }
        
        
    }
   
    
    @Override
    protected void compileSolutions(){
        super.compileSolutions();
        boolean end = false;
        byte[] spuzzle_solution = new byte[dims];
        for (boolean[] row : solutions){
            if (row == null){
                if (end) return;
                spuzzle_solutions.add(spuzzle_solution);
                spuzzle_solution = new byte[dims];
                end = true;
                continue;
            } else {
                end = false;
            }
            int index = -1;
            byte value = 0;
            /**
            for (boolean a : row){
                System.out.print(a+ " ");
            }
            */
            for (int i = 0 ; i < dims ; i++){
                if(row[i]){
                    index = i;
                    
                    break;
                }
            }
            for (int i = dims + x*y*getRowIndex(index) ; i < 2*dims ; i++){
                if (row[i]){
                    //System.out.println((i)%(x*y)+1 + " " + index);
                    value =(byte) ((i - dims)%(x*y)+1);
                    break;
                }
            }
            assert(value != 0);
            spuzzle_solution[index] = value;
            
        }
        //spuzzle_solutions.add(spuzzle_solution);
        //for (byte a : spuzzle_solution){
        //    System.out.print(a + " ");
        //}            
        
    }
    
    protected boolean[] makeCCProblemRow(int index, int n){
        //System.out.println("make row index = " + index);
        //System.out.println("make row n = " + n);
        boolean[] ret = new boolean[dims*4];
                
        int r = getRowIndex(index);
        int c = getColumnIndex(index);
        int b = getBlockNumber(index);
        
        ret[index] = true;
        //System.out.println(n);
        ret[dims + r*size + n] = true;
        //System.out.println(n);
        ret[2*dims + c*size + n] = true;
        //System.out.println(n);
        ret[3*dims + b*size + n] = true;
        return ret;
    }
    
    public void toCCProblem(){
        ArrayList<boolean[]> temparray = new ArrayList<>();
        
        //System.out.println("dims = " + dims);
        //boolean[] row = new boolean[(dims*4)];
        
        for (int i = 0 ; i < dims ; i++){
            //int r = getRowIndex(i);
            //int c = getColumnIndex(i);
            //int b = getBlockNumber(i);
            
            if (spuzzle[i] != 0){
                /**
                row[i] = true;
                //System.out.println("position = " + i);
                row[dims + r*size + spuzzle[i]-1] = true;
                //System.out.println("row = " + (dims + r*size + spuzzle[i]));
                row[2*dims + c*size + spuzzle[i]-1] = true;
                //System.out.println("column = " + (2*dims + c*size + spuzzle[i]));
                row[3*dims + b*size + spuzzle[i]-1] = true;
                //System.out.println("block = " + (3*dims + b*size + spuzzle[i]));
                */
                
                temparray.add(makeCCProblemRow(i, (spuzzle[i]-1)));
                //row = new boolean[dims*4];
            } else {
                for (int a = 0 ; a < size ; a++){
                    /**
                    row[i] = true;
                    row[dims + r*size + a] = true;
                    row[2*dims + c*size + a] = true;
                    row[3*dims + b*size + a] = true;
                    */
                    temparray.add(makeCCProblemRow(i, a));
                    /**
                    for (boolean d : row){
                        System.out.print(d + " ");
                    }
                    System.out.println();
                    System.out.println();
                    */
                    
                    
                    //row = new boolean[dims*4];
                }
            }
            /**
            for (boolean[] d : temparray){
                for (boolean e: d){
                    System.out.print(e + " ");
                }
                System.out.println();
            }          
            */
        }
        boolean[][] ret = new boolean[temparray.size()][dims*4];
        for (int r = 0 ; r < temparray.size() ; r++){
            ret[r] = temparray.get(r);
            //System.out.println(temparray.get(r).length);
            }
        //printSolutions(temparray, dims*4);
        
        //System.out.println(ret.length);
        //System.out.println(ret[0].length);
        
        ccproblem = new NodeMatrix(ret);
        
    }
    
    public int getRowIndex(int i){
        return (int) i/(size);
    }
    
    public int getColumnIndex(int i){
        return i%(size);
    }
    
    public int getBlockNumber(int k){
        return ((int) getColumnIndex(k)/n) + ((int) getRowIndex(k)/m)*n;
    }
    
    
    
    
    public byte[] getBlock(int i, int j){
        assert(i < m && j < n);
        
        byte[] ret = new byte[size];
        int start = (i*x*y*n) + (j*y);
        int index = 0;
        
        for (int a = 0 ; a < x ; a++){
            for (int b = 0 ; b < y ; b++){
                ret[index] = spuzzle[start + (a*y*n) + b];
                index++;
            }
        }
        return ret;
    }
    
    public byte[] getRow(int i){
        byte[] ret = new byte[y*n];
        assert(i < m*x);
        
        for (int a = 0 ; a < y*n ; a++){
            ret[a] = spuzzle[y*n*i + a];
        }
        return ret;
            
    }
    
    public byte[] getColumn(int j){
        byte[] ret = new byte[m*x];
        assert(j < n*y);
        
        for (int a = 0 ; a < m*x ; a++){
            ret[a] = spuzzle[j + a*y*n];
        }
        return ret;
    }
    
    
    
}
